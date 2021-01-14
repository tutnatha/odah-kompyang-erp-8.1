package org.evenos.processes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceOdah;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

//OLEH : I KETUT SUNARTHA

public class InvoiceCopyForm extends SvrProcess{

	private Timestamp	m_dateInvoiced_From = null;
	private Timestamp	m_dateInvoiced_To = null;
	private int			m_C_BPartner_ID = 0;
	private int			m_C_Invoice_ID = 0;
	private String		m_DocumentNo_From = null;
	private String		m_DocumentNo_To = null;

	//contoh 
//	private int		m_C_Invoice_ID = 0;
	
//	@Override : NANTI BUKA LAGI
	protected void prepareXYZ() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null && para[i].getParameter_To() == null)
				;
			else if (name.equals("DateInvoiced"))
			{
				m_dateInvoiced_From = ((Timestamp)para[i].getParameter());
				m_dateInvoiced_To = ((Timestamp)para[i].getParameter_To());
			}			
			else if (name.equals("C_BPartner_ID"))
				m_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Invoice_ID"))
				m_C_Invoice_ID = para[i].getParameterAsInt();
			else if (name.equals("DocumentNo"))
			{
				m_DocumentNo_From = (String)para[i].getParameter();
				m_DocumentNo_To = (String)para[i].getParameter_To();
			}
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
		if (m_DocumentNo_From != null && m_DocumentNo_From.length() == 0)
			m_DocumentNo_From = null;
		if (m_DocumentNo_To != null && m_DocumentNo_To.length() == 0)
			m_DocumentNo_To = null;				
	}

//	@Override // NANTI DIBUKA LAGI
	protected String doItXYZ() throws Exception {
		
//		Too broad selection
		if (m_C_BPartner_ID == 0 && m_C_Invoice_ID == 0 && m_dateInvoiced_From == null && m_dateInvoiced_To == null
			&& m_DocumentNo_From == null && m_DocumentNo_To == null)
			throw new AdempiereUserError ("@RestrictSelection@");

		MClient client = MClient.get(getCtx());
		
//		Get Info
		StringBuilder sql = new StringBuilder ("SELECT i.C_Invoice_ID,bp.AD_Language,c.IsMultiLingualDocument,")		//	1..3
			//	Prio: 1. BPartner 2. DocType, 3. PrintFormat (Org)	//	see ReportCtl+MInvoice
			.append(" COALESCE(bp.Invoice_PrintFormat_ID, dt.AD_PrintFormat_ID, pf.Invoice_PrintFormat_ID),")	//	4 
			.append(" dt.DocumentCopies+bp.DocumentCopies,")								//	5
			.append(" bpc.AD_User_ID, i.DocumentNo,")										//	6..7
			.append(" bp.C_BPartner_ID ")													//	8
			.append("FROM C_Invoice i")
			.append(" INNER JOIN C_BPartner bp ON (i.C_BPartner_ID=bp.C_BPartner_ID)")
			.append(" LEFT OUTER JOIN AD_User bpc ON (i.AD_User_ID=bpc.AD_User_ID)")
			.append(" INNER JOIN AD_Client c ON (i.AD_Client_ID=c.AD_Client_ID)")
			.append(" INNER JOIN AD_PrintForm pf ON (i.AD_Client_ID=pf.AD_Client_ID)")
			.append(" INNER JOIN C_DocType dt ON (i.C_DocType_ID=dt.C_DocType_ID)")
		    .append(" WHERE i.AD_Client_ID=? AND i.AD_Org_ID=? AND i.isSOTrx='Y' AND ")
		    .append("       pf.AD_Org_ID IN (0,i.AD_Org_ID) AND " );	//	more them 1 PF
		
		return null;
	}
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
//	protected void prepareContoh()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_Invoice_ID"))
				m_C_Invoice_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("Dari C_Invoice_ID :"))
				m_C_Invoice_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}	//	prepare
	
	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
//	protected String doItContoh() throws Exception
	{
		//select Record yang baru :
		//karena dalam idempiere form akan dibuat terlebih dahulu 
		//dan membuat record baru
		
//		int To_C_Invoice_ID = getRecord_ID();
		int To_C_Invoice_ID = 1000038;			//hard code dulu
												//invoice tujuan
		
		if (log.isLoggable(Level.INFO)) log.info("From C_Invoice_ID=" + m_C_Invoice_ID + " to " + To_C_Invoice_ID);
		if (To_C_Invoice_ID == 0)
			throw new IllegalArgumentException("Target C_Invoice_ID == 0");
		if (m_C_Invoice_ID == 0)
			throw new IllegalArgumentException("Source C_Invoice_ID == 0");
		
		//select from invoice where invoice_id=m_C_Invoice_ID
		MInvoice from = new MInvoice (getCtx(), m_C_Invoice_ID, get_TrxName());
		
		//select to invoice where invoice_i=To_C_Invoice_ID
//		MInvoice to = new MInvoice (getCtx(), To_C_Invoice_ID, get_TrxName());	//remark dulu
		
		//invoice baru C_Invoice_ID = 0
//		MInvoice to2 = new MInvoice(getCtx(), 0, get_TrxName());
//		to2.saveEx();
//		to2.save();
//		to2.save(get_TrxName());
//		to2.saveEx(get_TrxName());
//		to2.saveReplica(isFromReplication);
		
		//
//		int no = to.copyLinesFrom (from, false, false, false);	//remark dulu		
		//
		
//		int no2 = to2.copyLinesFrom (from, false, false, false);
		
		//
//		return "@Copied@=" + no;
//		return "@Copied@=" + no2;
		
		MInvoiceOdah to2 = new MInvoiceOdah(getCtx(), 0, get_TrxName());
		to2.saveEx();
		
		return "@Copied@=" + 0; //no2; //0;
	}	//	doIt
}
