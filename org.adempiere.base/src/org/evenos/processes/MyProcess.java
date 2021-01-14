package org.evenos.processes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.util.Callback;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

public class MyProcess extends SvrProcess{

	private String aString = "String Kosong";
	private boolean acheckbox = true;
	private Timestamp adatetime = new Timestamp(System.currentTimeMillis());
	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		log.warning("test aja dulu()-method");
		
		ProcessInfoParameter[] param = getParameter();
		for(ProcessInfoParameter para : param) {
			
			String paraName = para.getParameterName();
			
			if(paraName.equalsIgnoreCase("astring")) {
				aString = para.getParameterAsString();
			}else if(paraName.equalsIgnoreCase("acheckbox")) {
				acheckbox = para.getParameterAsBoolean();
			}else if(paraName.equalsIgnoreCase("adatetime")) {
				adatetime = para.getParameterAsTimestamp();
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + paraName);
			
		}	
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		log.warning("test aja dulu ya.....()-method");
		
		addLog(aString);
		addLog(1000000, adatetime, BigDecimal.ONE, "Tanggal Kita Adalah" + adatetime.toString());
		addLog(1000001, adatetime, BigDecimal.ONE, "CheckBox Kita Adalah" + acheckbox, 100, 100);
		
		StringBuilder yesno = new StringBuilder();
		
		processUI.ask("Ya atau Tidak ?", new Callback<Boolean>() {
			
			@Override
			public void onCallback(Boolean result) {
				// TODO Auto-generated method stub
				yesno.append(result);
			}
		});
		
		while(yesno.length() == 0)
			;
		
		final StringBuilder string = new StringBuilder();
		final StringBuilder stringProvided = new StringBuilder();
		
		processUI.askForInput("Tolong Masukan String", new Callback<String>() {

			@Override
			public void onCallback(String result) {
				// TODO Auto-generated method stub
				stringProvided.append(true);
				string.append(result);
			}
			
			
		});
		
		while(stringProvided.length() == 0)
			;
		
		addLog(yesno.toString());
		addLog(string.toString());
		
		return null;
	}

}
