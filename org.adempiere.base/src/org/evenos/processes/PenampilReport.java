package org.evenos.processes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

public class PenampilReport extends SvrProcess{
	
	private String Nama = "String Kosong";
	private Timestamp DariTanggal = new Timestamp(System.currentTimeMillis());
	private Timestamp SampaiTanggal = new Timestamp(System.currentTimeMillis());

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
		log.warning("----lagi prepare----() - method");
		
		ProcessInfoParameter[] param = getParameter();
		for(ProcessInfoParameter para : param) {
			
			String paraName = para.getParameterName();
			
			if(paraName.equalsIgnoreCase("ad_client_id")) {
				Nama = para.getParameterAsString();
			}else if(paraName.equalsIgnoreCase("DariTanggal")) {
				DariTanggal = para.getParameter_ToAsTimestamp();
			}else if(paraName.equalsIgnoreCase("SampaiTanggal")) {
				SampaiTanggal = para.getParameter_ToAsTimestamp();
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + paraName);
			
			
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		log.warning("----lagi mengerjakan----() - method");
		return null;
	}

}
