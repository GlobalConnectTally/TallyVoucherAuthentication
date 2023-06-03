package tallyadmin.gp.gpcropcare.Model;

public class MonthlyReportDetails {
     String CmpShortName;
     String LedgerName;
     String month;
     int monthNo;
     int year;
     int yearmonthcombo;
     Opening opening;
     Debt debt;
     Credit credit;
     Closing closing;

     public MonthlyReportDetails() {
     }

     public MonthlyReportDetails(String cmpShortName, String ledgerName, String month, int monthNo, int year, int yearmonthcombo, Opening opening, Debt debt, Credit credit, Closing closing) {
          CmpShortName = cmpShortName;
          LedgerName = ledgerName;
          this.month = month;
          this.monthNo = monthNo;
          this.year = year;
          this.yearmonthcombo = yearmonthcombo;
          this.opening = opening;
          this.debt = debt;
          this.credit = credit;
          this.closing = closing;
     }

     public int getMonthNo() {
          return monthNo;
     }

     public void setMonthNo(int monthNo) {
          this.monthNo = monthNo;
     }

     public int getYear() {
          return year;
     }

     public void setYear(int year) {
          this.year = year;
     }

     public int getYearmonthcombo() {
          return yearmonthcombo;
     }

     public void setYearmonthcombo(int yearmonthcombo) {
          this.yearmonthcombo = yearmonthcombo;
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
          return "MonthlyReportDetails{" +
                  "CmpShortName='" + CmpShortName + '\'' +
                  ", LedgerName='" + LedgerName + '\'' +
                  ", month='" + month + '\'' +
                  ", monthNo=" + monthNo +
                  ", year=" + year +
                  ", yearmonthcombo=" + yearmonthcombo +
                  ", opening=" + opening +
                  ", debt=" + debt +
                  ", credit=" + credit +
                  ", closing=" + closing +
                  '}';
     }
}

