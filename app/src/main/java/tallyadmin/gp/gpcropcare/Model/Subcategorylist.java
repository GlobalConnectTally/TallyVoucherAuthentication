package tallyadmin.gp.gpcropcare.Model;

public class Subcategorylist {


    static String subcategoryname;
    static String creditamount;
    static String percentage;
    static  String salesquantity;
    static  String creditdays;
    static String salesamount;

    public Subcategorylist(String subcategoryname, String creditamount, String percentage, String salesquantity,
                           String creditdays,String salesamount) {
        this.subcategoryname = subcategoryname;
        this.creditamount = creditamount;
        this.percentage = percentage;
        this.salesquantity = salesquantity;
        this.creditdays = creditdays;
        this.salesamount = salesamount;
    }

    public static String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }


    public static String getCreditamount() {
        return creditamount;
    }

    public void setCreditamount(String creditamount) {
        this.creditamount = creditamount;
    }


    public static String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }


    public static String getSalesquantity() {
        return salesquantity;
    }

    public void setSalesquantity(String salesquantity) {
        this.salesquantity = salesquantity;
    }


    public static String getCreditdays() {
        return creditdays;
    }

    public void setCreditdays(String creditdays) {
        this.creditdays = creditdays;
    }


    public static String getSalesamount() {
        return salesamount;
    }

    public void setSalesamount(String salesamount) {
        this.salesamount = salesamount;
    }

}
