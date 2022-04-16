package tallyadmin.gp.gpcropcare.Model;

public class Demoresponse {
    String status,msg;

    public Demoresponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Demoresponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
