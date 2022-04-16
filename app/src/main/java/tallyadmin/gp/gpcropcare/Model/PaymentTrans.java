package tallyadmin.gp.gpcropcare.Model;

public class PaymentTrans {
    String PaidBy,PaidTo,TransactionType,InstrumentNo,InstrumentDate,AccountNo,IFSCCode,BankName;

    public PaymentTrans() {
    }

    public String getPaidBy() {
        return PaidBy;
    }

    public void setPaidBy(String paidBy) {
        PaidBy = paidBy;
    }

    public String getPaidTo() {
        return PaidTo;
    }

    public void setPaidTo(String paidTo) {
        PaidTo = paidTo;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getInstrumentNo() {
        return InstrumentNo;
    }

    public void setInstrumentNo(String instrumentNo) {
        InstrumentNo = instrumentNo;
    }

    public String getInstrumentDate() {
        return InstrumentDate;
    }

    public void setInstrumentDate(String instrumentDate) {
        InstrumentDate = instrumentDate;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }
}
