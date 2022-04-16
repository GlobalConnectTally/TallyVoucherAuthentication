package tallyadmin.gp.gpcropcare.Model;

public class SalesOrder {
    String CmpGUID,VoucherTypeName,PartyName,ReffNo,BuyerName,BuyerAddress,Narration,TotalAmt,Date,TallyUserName,VoucherNumber,TallyUsermobileno,LedgerMasterId,AllowApprove,AllowReject;
    int MasterID;

    public SalesOrder(String cmpGUID, String voucherTypeName, String partyName, String reffNo, String buyerName, String buyerAddress, String narration, String totalAmt, String date, String tallyUserName, String voucherNumber, String tallyUsermobileno, String ledgerMasterId, int masterID, String approve, String reject) {
        CmpGUID = cmpGUID;
        VoucherTypeName = voucherTypeName;
        PartyName = partyName;
        ReffNo = reffNo;
        BuyerName = buyerName;
        BuyerAddress = buyerAddress;
        Narration = narration;
        TotalAmt = totalAmt;
        Date = date;
        TallyUserName = tallyUserName;
        VoucherNumber = voucherNumber;
        TallyUsermobileno = tallyUsermobileno;
        LedgerMasterId = ledgerMasterId;
        MasterID = masterID;
        AllowApprove  = approve;
        AllowReject = reject ;
    }

    public SalesOrder() {

    }

    public String getAllowApprove() {
        return AllowApprove;
    }

    public void setAllowApprove(String allowApprove) {
        AllowApprove = allowApprove;
    }

    public String getAllowReject() {
        return AllowReject;
    }

    public void setAllowReject(String allowReject) {
        AllowReject = allowReject;
    }

    public String getCmpGUID() {
        return CmpGUID;
    }

    public void setCmpGUID(String cmpGUID) {
        CmpGUID = cmpGUID;
    }

    public String getVoucherTypeName() {
        return VoucherTypeName;
    }

    public void setVoucherTypeName(String voucherTypeName) {
        VoucherTypeName = voucherTypeName;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getReffNo() {
        return ReffNo;
    }

    public void setReffNo(String reffNo) {
        ReffNo = reffNo;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getBuyerAddress() {
        return BuyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        BuyerAddress = buyerAddress;
    }

    public String getNarration() {
        return Narration;
    }

    public void setNarration(String narration) {
        Narration = narration;
    }

    public String getTotalAmt() {
        return TotalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        TotalAmt = totalAmt;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTallyUserName() {
        return TallyUserName;
    }

    public void setTallyUserName(String tallyUserName) {
        TallyUserName = tallyUserName;
    }

    public String getVoucherNumber() {
        return VoucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        VoucherNumber = voucherNumber;
    }

    public String getTallyUsermobileno() {
        return TallyUsermobileno;
    }

    public void setTallyUsermobileno(String tallyUsermobileno) {
        TallyUsermobileno = tallyUsermobileno;
    }

    public String getLedgerMasterId() {
        return LedgerMasterId;
    }

    public void setLedgerMasterId(String ledgerMasterId) {
        LedgerMasterId = ledgerMasterId;
    }

    public int getMasterID() {
        return MasterID;
    }

    public void setMasterID(int masterID) {
        MasterID = masterID;
    }
}
