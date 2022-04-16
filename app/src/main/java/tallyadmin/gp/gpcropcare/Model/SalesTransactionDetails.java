package tallyadmin.gp.gpcropcare.Model;



public class SalesTransactionDetails {
    String ItemName,SalesLedgerName,InvQty,InvBatchName,InvGodownName,InvMfgDate,InvExpDate;
    String VoucherNumber,InvRate;

    String InvAmount,InvDiscPerc;

    public String getInvAmount() {
        return InvAmount;
    }

    public void setInvAmount(String invAmount) {
        InvAmount = invAmount;
    }

    public SalesTransactionDetails() {

    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getSalesLedgerName() {
        return SalesLedgerName;
    }

    public void setSalesLedgerName(String salesLedgerName) {
        SalesLedgerName = salesLedgerName;
    }

    public String getInvQty() {
        return InvQty;
    }

    public void setInvQty(String invQty) {
        InvQty = invQty;
    }

    public String getInvBatchName() {
        return InvBatchName;
    }

    public void setInvBatchName(String invBatchName) {
        InvBatchName = invBatchName;
    }

    public String getInvGodownName() {
        return InvGodownName;
    }

    public void setInvGodownName(String invGodownName) {
        InvGodownName = invGodownName;
    }

    public String getInvMfgDate() {
        return InvMfgDate;
    }

    public void setInvMfgDate(String invMfgDate) {
        InvMfgDate = invMfgDate;
    }

    public String getInvExpDate() {
        return InvExpDate;
    }

    public void setInvExpDate(String invExpDate) {
        InvExpDate = invExpDate;
    }

    public String getInvDiscPerc() {
        return InvDiscPerc;
    }

    public void setInvDiscPerc(String invDiscPerc) {
        InvDiscPerc = invDiscPerc;
    }

    public String getVoucherNumber() {
        return VoucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        VoucherNumber = voucherNumber;
    }

    public String getInvRate() {
        return InvRate;
    }

    public void setInvRate(String invRate) {
        InvRate = invRate;
    }
}
