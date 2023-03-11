package tallyadmin.gp.gpcropcare.Model;

public class MonthlyReportDetails {
     String CmpShortName;
     String LedgerName;
     String month;
     Opening opening;
     Debt debt;
     Credit credit;
     Closing closing;

     public MonthlyReportDetails() {
     }

     public MonthlyReportDetails(String cmpShortName, String ledgerName, String month, Opening opening, Debt debt, Credit credit, Closing closing) {
          CmpShortName = cmpShortName;
          LedgerName = ledgerName;
          this.month = month;
          this.opening = opening;
          this.debt = debt;
          this.credit = credit;
          this.closing = closing;
     }

     public String getCmpShortName() {
          return CmpShortName;
     }

     public void setCmpShortName(String cmpShortName) {
          CmpShortName = cmpShortName;
     }

     public String getLedgerName() {
          return LedgerName;
     }

     public void setLedgerName(String ledgerName) {
          LedgerName = ledgerName;
     }

     public String getMonth() {
          return month;
     }

     public void setMonth(String month) {
          this.month = month;
     }

     public Opening getOpening() {
          return opening;
     }

     public void setOpening(Opening opening) {
          this.opening = opening;
     }

     public Debt getDebt() {
          return debt;
     }

     public void setDebt(Debt debt) {
          this.debt = debt;
     }

     public Credit getCredit() {
          return credit;
     }

     public void setCredit(Credit credit) {
          this.credit = credit;
     }

     public Closing getClosing() {
          return closing;
     }

     public void setClosing(Closing closing) {
          this.closing = closing;
     }

     @Override
     public String toString() {
          return "{" +
                  "CmpShortName='" + CmpShortName + '\'' +
                  ", LedgerName='" + LedgerName + '\'' +
                  ", month='" + month + '\'' +
                  ", opening=" + opening +
                  ", debt=" + debt +
                  ", credit=" + credit +
                  ", closing=" + closing +
                  '}';
     }
}

