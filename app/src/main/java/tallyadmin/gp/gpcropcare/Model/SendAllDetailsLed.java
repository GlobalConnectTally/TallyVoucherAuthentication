package tallyadmin.gp.gpcropcare.Model;

public class SendAllDetailsLed {
    String VoucherNumber,LedgerPerc;
    String LedgerAmount,LedgerName;


    public SendAllDetailsLed() {
    }

    public String getVoucherNumber() {
        return VoucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        VoucherNumber = voucherNumber;
    }

    public String getLedgerPerc() {
        return LedgerPerc;
    }

    public void setLedgerPerc(String ledgerPerc) {
        LedgerPerc = ledgerPerc;
    }

    public String getLedgerAmount() {
        return LedgerAmount;
    }

    public void setLedgerAmount(String ledgerAmount) {
        LedgerAmount = ledgerAmount;
    }

    public String getLedgerName() {
        return LedgerName;
    }

    public void setLedgerName(String ledgerName) {
        LedgerName = ledgerName;
    }
}
