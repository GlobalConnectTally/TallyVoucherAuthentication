package tallyadmin.gp.gpcropcare.Model;

public class LastBusiness {

    String pgr;
    String pestisides;
    String royal;
    String premium;
    String classic;
    String dust;
    String total;

    public LastBusiness(String pgr, String pestisides, String royal, String premium, String classic, String dust, String total) {
        this.pgr = pgr;
        this.pestisides = pestisides;
        this.royal = royal;
        this.premium = premium;
        this.classic = classic;
        this.dust = dust;
        this.total = total;
    }

    public LastBusiness() {
    }

    public String getPestisides() {
        return pestisides;
    }

    public void setPestisides(String pestisides) {
        this.pestisides = pestisides;
    }

    public String getPgr() {
        return pgr;
    }

    public void setPgr(String pgr) {
        this.pgr = pgr;
    }

    public String getRoyal() {
        return royal;
    }

    public void setRoyal(String royal) {
        this.royal = royal;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getClassic() {
        return classic;
    }

    public void setClassic(String classic) {
        this.classic = classic;
    }

    public String getDust() {
        return dust;
    }

    public void setDust(String dust) {
        this.dust = dust;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


}
