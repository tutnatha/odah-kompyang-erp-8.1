package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.process.DocAction;
import org.compiere.util.Env;

//author : I Ketut Sunartha
//made for : Generate AR Invoice (Sales Order)--> based on Payment Vendor (AP Invoice)  

public class MInvoiceOdah extends X_C_Invoice implements DocAction{

	public MInvoiceOdah(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
		// TODO Auto-generated constructor stub
		if (C_Invoice_ID == 0)
		{
			setDocStatus (DOCSTATUS_Drafted);		//	Draft
			setDocAction (DOCACTION_Complete);
			//
			setPaymentRule(PAYMENTRULE_OnCredit);	//	Payment Terms

			setDateInvoiced (new Timestamp (System.currentTimeMillis ()));
			setDateAcct (new Timestamp (System.currentTimeMillis ()));
			//
			setChargeAmt (Env.ZERO);
			setTotalLines (Env.ZERO);
			setGrandTotal (Env.ZERO);
			//
			setIsSOTrx (true);
			setIsTaxIncluded (false);
			setIsApproved (false);
			setIsDiscountPrinted (false);
			setIsPaid (false);
			setSendEMail (false);
			setIsPrinted (false);
			setIsTransferred (false);
			setIsSelfService(false);
			setIsPayScheduleValid(false);
			setIsInDispute(false);
			setPosted(false);
			super.setProcessed (false);
			setProcessing(false);
			
			//add oleh: natha
			super.setAD_Org_ID(1000003);
			super.setC_DocType_ID(0);					//New Document
			super.setC_DocTypeTarget_ID(1000055);		//AR Receipt
			super.setC_BPartner_ID(1000050);			//PUBLIC
			super.setC_BPartner_Location_ID(1000037);	//lokasi
			super.setC_Currency_ID(303);				//IDR
			super.setC_PaymentTerm_ID(1000001);         //Immediate
//			super.setPaymentRule("P");					//P, S, B
			super.setM_PriceList_ID(1000006);			//SALES PRICE LIST DEC 2020
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean processIt(String action) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
