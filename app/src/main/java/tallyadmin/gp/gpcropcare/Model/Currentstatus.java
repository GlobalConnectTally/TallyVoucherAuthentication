package tallyadmin.gp.gpcropcare.Model;

public class Currentstatus {
    String Opening;
    String Debit;
    String Credit;
    String Closing;


    public Currentstatus(String opening, String debit, String credit, String closing) {
        Opening = opening;
        Debit = debit;
        Credit = credit;
        Closing = closing;
    }

    public Currentstatus() {
    }

    public String getOpening() {
        return Opening;
    }

    public void setOpening(String opening) {
        Opening = opening;
    }

    public String getDebit() {
        return Debit;
    }

    public void setDebit(String debit) {
        Debit = debit;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }

    public String getClosing() {
        return Closing;
    }

    public void setClosing(String closing) {
        Closing = closing;
    }

}
