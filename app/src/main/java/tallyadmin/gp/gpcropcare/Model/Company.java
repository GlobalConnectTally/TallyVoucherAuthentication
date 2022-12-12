package tallyadmin.gp.gpcropcare.Model;

public class Company {

    String CmpGUID, CompanyName;
    private boolean isSelected;
    private Integer pendingSales;
    private String allowedApprove, allowReject;
    private String FirstLevel, SecondLevel,CmpShortName;

    public Company() {

    }

    public Company(String cmpGUID, String companyName, boolean isSelected, Integer pendingSales,
                   String allowedApprove, String allowReject, String firstLevel, String secondLevel,String CmpShortName)
    {
        CmpGUID = cmpGUID;
        CompanyName = companyName;
        this.isSelected = isSelected;
        this.pendingSales = pendingSales;
        this.allowedApprove = allowedApprove;
        this.allowReject = allowReject;
        this.CmpShortName = CmpShortName;
        FirstLevel = firstLevel;
        SecondLevel = secondLevel;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Company(String cmpGUID, String companyName, boolean isSelected)
    {
        CmpGUID = cmpGUID;
        CompanyName = companyName;
        this.isSelected = isSelected;
    }

    public String getCmpGUID() {
        return CmpGUID;
    }

    public void setCmpGUID(String cmpGUID)
    {
        CmpGUID = cmpGUID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public Integer getPendingSales() {
        return pendingSales;
    }

    public void setPendingSales(Integer pendingSales) {
        this.pendingSales = pendingSales;
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

    public String getFirstLevel() {
        return FirstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        FirstLevel = firstLevel;
    }

    public String getSecondLevel() {
        return SecondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        SecondLevel = secondLevel;
    }

    public String getCmpShortName() {
        return CmpShortName;
    }

    public void setCmpShortName(String cmpShortName) {
        CmpShortName = cmpShortName;
    }
}
