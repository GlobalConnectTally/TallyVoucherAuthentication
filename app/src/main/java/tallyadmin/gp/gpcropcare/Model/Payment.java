package tallyadmin.gp.gpcropcare.Model;

public class Payment {
   String Narration,Amount,TallyUserName,VoucherDate,VoucherTypeName,CmpGUID,VoucherNumber;
   int MasterID;



    public Payment() {
    }

    public String getVoucherNumber() {
        return VoucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        VoucherNumber = voucherNumber;
    }

    public String getNarration() {

        return Narration;
    }

    public void setNarration(String narration) {
        Narration = narration;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTallyUserName() {
        return TallyUserName;
    }

    public void setTallyUserName(String tallyUserName) {
        TallyUserName = tallyUserName;
    }

    public String getVoucherDate() {
        return VoucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        VoucherDate = voucherDate;
    }

    public String getVoucherTypeName() {
        return VoucherTypeName;
    }

    public void setVoucherTypeName(String voucherTypeName) {
        VoucherTypeName = voucherTypeName;
    }

    public String getCmpGUID() {
        return CmpGUID;
    }

    public void setCmpGUID(String cmpGUID) {
        CmpGUID = cmpGUID;
    }

    public int getMasterID() {
        return MasterID;
    }

    public void setMasterID(int masterID) {
        MasterID = masterID;
    }
}
