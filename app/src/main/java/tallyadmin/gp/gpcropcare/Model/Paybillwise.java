package tallyadmin.gp.gpcropcare.Model;

public class Paybillwise {
    String PartyName,BillReff,BillDate,Amount;

    public Paybillwise() {
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getBillReff() {
        return BillReff;
    }

    public void setBillReff(String billReff) {
        BillReff = billReff;
    }

    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
