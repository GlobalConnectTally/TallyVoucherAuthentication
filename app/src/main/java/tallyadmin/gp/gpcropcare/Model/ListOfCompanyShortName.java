package tallyadmin.gp.gpcropcare.Model;

 public class ListOfCompanyShortName {


    String CmpShortName;

    public String getCmpShortName() {
        return CmpShortName;
    }

    public void setCmpShortName(String cmpShortName) {
        CmpShortName = cmpShortName;
    }

    @Override
    public String toString() {
        return "{" +
                ", CmpShortName='" + CmpShortName + '\'' +
                '}';
    }
}
