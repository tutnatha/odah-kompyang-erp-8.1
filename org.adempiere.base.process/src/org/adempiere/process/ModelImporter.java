package org.adempiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.MDocType;
//import org.compiere.model.X_C_BPartner_EDI;
//import org.compiere.model.X_C_EDIFormat;
//import org.compiere.model.X_C_EDIProcessor;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class ModelImporter extends SvrProcess{

	/** Client Parameter			*/
	protected int	p_AD_Client_ID = 0;
	
	/** Business Partner Parameter */
	protected int p_C_BPartner_ID = 0;
	
	/** Document Type Parameter */
	protected int p_C_DocType_ID = 0;
	
	/** Record ID */
	protected int p_Record_ID = 0;
	
	/** Table ID */
	int AD_Table_ID = 0;
	
	/**
	 * 	Get Parameters
	 */
	@Override
	protected void prepare() {
		
		p_Record_ID = getRecord_ID();
		if (p_AD_Client_ID == 0)
			p_AD_Client_ID = Env.getAD_Client_ID(getCtx());
		AD_Table_ID = getTable_ID();
		
		StringBuffer sb = new StringBuffer ("AD_Table_ID=").append(AD_Table_ID);
		sb.append("; Record_ID=").append(getRecord_ID());
		//	Parameter
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		log.info(sb.toString());
		
	}

	/**
	 * 	Process 
	 *	@return info
	 */
	@Override
	protected String doIt() throws Exception {
StringBuffer result = new StringBuffer("");
		
		MClient client = MClient.get (getCtx(), p_AD_Client_ID);
		log.info(client.toString());
		// get proper EDI Format from Document Type
		MDocType docType = MDocType.get(getCtx(), p_C_DocType_ID);
		
		int C_EDIFormat_ID = 0;
		int C_EDIProcessor_ID = 0;
		String sql1 = "SELECT C_EDIFormat_ID, C_EDIProcessor_ID "
//			   + "FROM " + X_C_BPartner_EDI.Table_Name + " "
			   + "WHERE AD_Client_ID = ? "
			   + " AND C_BPartner_ID = ? "
			   + " AND C_DocType_ID = ? "
			   + " AND Inbound = 'N' " 
		;
		ResultSet rs1 = null;
		PreparedStatement pstmt1 = null;
		try
		{
			pstmt1 = DB.prepareStatement(sql1, get_TrxName());
			pstmt1.setInt(1, p_AD_Client_ID);
			pstmt1.setInt(2, p_C_BPartner_ID);
			pstmt1.setInt(3, p_C_DocType_ID);
			rs1 = pstmt1.executeQuery();
			if (rs1.next())
			{
				// Found specific C_EDIFormat for given BPartner and C_DocType_ID
//				C_EDIFormat_ID = rs1.getInt(X_C_EDIFormat.COLUMNNAME_C_EDIFormat_ID);
//				C_EDIProcessor_ID = rs1.getInt(X_C_EDIProcessor.COLUMNNAME_C_EDIProcessor_ID);
			} else {
				// Get C_EDIFormat_ID from Document Type
//				C_EDIFormat_ID = docType.getC_EDIFormat_ID();
			}
			
		} finally {
			try {
				if (rs1 != null) rs1.close();
				if (pstmt1 != null) pstmt1.close();
			} catch (SQLException ex) {/*ignored*/}
			rs1 = null;
			pstmt1 = null;
		}
		if (C_EDIFormat_ID == 0) {
			throw new Exception("EDI Format is not set for C_DocType_ID = [" + p_C_DocType_ID + "]");
		}
		if (C_EDIProcessor_ID == 0) {
			throw new Exception("EDI Processor is not set for C_DocType_ID = [" + p_C_DocType_ID + "]");
		}
		
		addLog(0, null, null, Msg.getMsg (getCtx(), "EDISubmitProcessResult") + "\n" + result.toString());
		return result.toString();
	}

}
