package tallyadmin.gp.gpcropcare.Model;

public class Closing {
    double ncr;
    double rpl;

    public Closing(double ncr, double rpl) {
        this.ncr = ncr;
        this.rpl = rpl;
    }

    public double getNcr() {
        return ncr;
    }

    public void setNcr(double ncr) {
        this.ncr = ncr;
    }

    public double getRpl() {
        return rpl;
    }

    public void setRpl(double rpl) {
        this.rpl = rpl;
    }

    @Override
    public String toString() {
        return "{" +
                "ncr=" + ncr +
                ", rpl=" + rpl +
                '}';
    }
}
