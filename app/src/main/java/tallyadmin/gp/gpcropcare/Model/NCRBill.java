package tallyadmin.gp.gpcropcare.Model;

public class NCRBill {

    String Billno;
    String Billdate;
    String Amount;
    String Duedate;

    public NCRBill(String billno, String billdate, String amount, String duedate) {
        Billno = billno;
        Billdate = billdate;
        Amount = amount;
        Duedate = duedate;
    }

    public NCRBill() {
    }

    public String getBillno() {
        return Billno;
    }

    public void setBillno(String billno) {
        Billno = billno;
    }

    public String getBilldate() {
        return Billdate;
    }

    public void setBilldate(String billdate) {
        Billdate = billdate;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDuedate() {
        return Duedate;
    }

    public void setDuedate(String duedate) {
        Duedate = duedate;
    }


}
