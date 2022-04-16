package tallyadmin.gp.gpcropcare.Model;

import java.util.ArrayList;
import java.util.List;

public class Report {
    String CmpGUID,PartyName,BuyerName,BuyerAddress,TotalAmt,Date,CategoryName,AllowedSubGrp,CreditDays,CreditAmt,SalesQuantity,SalesAmount,The120Days,The180Days,PartyMasterId;
    int MasterID,lengsize;

    private ArrayList<Reportchild> ChildItemList;

    public Report() {
    }

    public Report(String cmpGUID, String partyName, String buyerName, String buyerAddress, String totalAmt, String date, String categoryName, String allowedSubGrp, String creditDays, String creditAmt, String salesQuantity, String salesAmount, String the120Days, String the180Days, String partyMasterId, int masterID, int lengsize, ArrayList<Reportchild> childItemList) {
        CmpGUID = cmpGUID;
        PartyName = partyName;
        BuyerName = buyerName;
        BuyerAddress = buyerAddress;
        TotalAmt = totalAmt;
        Date = date;
        CategoryName = categoryName;
        AllowedSubGrp = allowedSubGrp;
        CreditDays = creditDays;
        CreditAmt = creditAmt;
        SalesQuantity = salesQuantity;
        SalesAmount = salesAmount;
        The120Days = the120Days;
        The180Days = the180Days;
        PartyMasterId = partyMasterId;
        MasterID = masterID;
        this.lengsize = lengsize;
        ChildItemList = childItemList;
    }

    public String getCmpGUID() {
        return CmpGUID;
    }

    public void setCmpGUID(String cmpGUID) {
        CmpGUID = cmpGUID;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getBuyerAddress() {
        return BuyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        BuyerAddress = buyerAddress;
    }

    public String getTotalAmt() {
        return TotalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        TotalAmt = totalAmt;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getAllowedSubGrp() {
        return AllowedSubGrp;
    }

    public void setAllowedSubGrp(String allowedSubGrp) {
        AllowedSubGrp = allowedSubGrp;
    }

    public String getCreditDays() {
        return CreditDays;
    }

    public void setCreditDays(String creditDays) {
        CreditDays = creditDays;
    }

    public String getCreditAmt() {
        return CreditAmt;
    }

    public void setCreditAmt(String creditAmt) {
        CreditAmt = creditAmt;
    }

    public String getSalesQuantity() {
        return SalesQuantity;
    }

    public void setSalesQuantity(String salesQuantity) {
        SalesQuantity = salesQuantity;
    }

    public String getSalesAmount() {
        return SalesAmount;
    }

    public void setSalesAmount(String salesAmount) {
        SalesAmount = salesAmount;
    }

    public String getThe120Days() {
        return The120Days;
    }

    public void setThe120Days(String the120Days) {
        The120Days = the120Days;
    }

    public String getThe180Days() {
        return The180Days;
    }

    public void setThe180Days(String the180Days) {
        The180Days = the180Days;
    }

    public String getPartyMasterId() {
        return PartyMasterId;
    }

    public void setPartyMasterId(String partyMasterId) {
        PartyMasterId = partyMasterId;
    }

    public int getMasterID() {
        return MasterID;
    }

    public void setMasterID(int masterID) {
        MasterID = masterID;
    }

    public int getLengsize() {
        return lengsize;
    }

    public void setLengsize(int lengsize) {
        this.lengsize = lengsize;
    }

    public ArrayList<Reportchild> getChildItemList() {
        return ChildItemList;
    }

    public void setChildItemList(ArrayList<Reportchild> childItemList) {
        ChildItemList = childItemList;
    }
}
