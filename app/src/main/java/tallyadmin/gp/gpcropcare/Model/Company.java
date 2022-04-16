package tallyadmin.gp.gpcropcare.Model;

public class Company {

    String CmpGUID,CompanyName;
    private boolean isSelected;
    private String allowedApprove,allowReject;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Company(String cmpGUID, String companyName, boolean isSelected) {
        CmpGUID = cmpGUID;
        CompanyName = companyName;
        this.isSelected = isSelected;
    }

    public Company(String cmpGUID, String companyName, boolean isSelected, String allowedApprove, String allowReject) {
        CmpGUID = cmpGUID;
        CompanyName = companyName;
        this.isSelected = isSelected;
        this.allowedApprove = allowedApprove;
        this.allowReject = allowReject;
    }

    public Company() {

    }

    public String getAllowedApprove() {
        return allowedApprove;
    }

    public void setAllowedApprove(String allowedApprove) {
        this.allowedApprove = allowedApprove;
    }

    public String getAllowReject() {
        return allowReject;
    }

    public void setAllowReject(String allowReject) {
        this.allowReject = allowReject;
    }

    public String getCmpGUID() {
        return CmpGUID;
    }

    public void setCmpGUID(String cmpGUID) {
        CmpGUID = cmpGUID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
