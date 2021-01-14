package org.evenos.factories;

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;
import org.evenos.processes.MyProcess;

public class MyProcessFactory implements IProcessFactory{

	@Override
	public ProcessCall newProcessInstance(String className) {
		// TODO Auto-generated method stub
		
		
		if(className.equals("org.evenos.processes.MyProcess"))
			return new MyProcess();
		
		return null;
	}

}
