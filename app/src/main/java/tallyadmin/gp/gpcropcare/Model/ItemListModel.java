package tallyadmin.gp.gpcropcare.Model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class ItemListModel {
    private String CmpShortName;
    private String ItemName;
    private String ItemParent;
    private String ItemOpening;
    private String ItemInwards;
    private String ItemOutwards;
    private String ItemClosing;
    private String AppLoginUserID;

    public ItemListModel() {

    }

    public ItemListModel( String cmpShortName, String itemName, String itemParent, String itemOpening, String itemInwards, String itemOutwards, String itemClosing, String appLoginUserID) {
        CmpShortName = cmpShortName;
        ItemName = itemName;
        ItemParent = itemParent;
        ItemOpening = itemOpening;
        ItemInwards = itemInwards;
        ItemOutwards = itemOutwards;
        ItemClosing = itemClosing;
        AppLoginUserID = appLoginUserID;
    }

    public String getCmpShortName()
    {
        return CmpShortName;
    }

    public void setCmpShortName(String cmpShortName)
    {
        CmpShortName = cmpShortName;
    }

    public String getItemName()
    {
        return ItemName;
    }

    public void setItemName(String itemName)
    {
        ItemName = itemName;
    }

    public String getItemParent()
    {
        return ItemParent;
    }

    public void setItemParent(String itemParent)
    {
        ItemParent = itemParent;
    }

    public String getItemOpening()
    {
        return ItemOpening;
    }

    public void setItemOpening(String itemOpening)
    {
        ItemOpening = itemOpening;
    }

    public String getItemInwards()
    {
        return ItemInwards;
    }

    public void setItemInwards(String itemInwards)
    {
        ItemInwards = itemInwards;
    }

    public String getItemOutwards()
    {
        return ItemOutwards;
    }

    public void setItemOutwards(String itemOutwards)
    {
        ItemOutwards = itemOutwards;
    }

    public String getItemClosing()
    {
        return ItemClosing;
    }

    public void setItemClosing(String itemClosing)
    {
        ItemClosing = itemClosing;
    }

    public String getAppLoginUserID() {
        return AppLoginUserID;
    }

    public void setAppLoginUserID(String appLoginUserID) {
        AppLoginUserID = appLoginUserID;
    }

    @Override
    public String toString() {
        return "{" +
                "CmpShortName='" + CmpShortName + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemParent='" + ItemParent + '\'' +
                ", ItemOpening='" + ItemOpening + '\'' +
                ", ItemInwards='" + ItemInwards + '\'' +
                ", ItemOutwards='" + ItemOutwards + '\'' +
                ", ItemClosing='" + ItemClosing + '\'' +
                ", AppLoginUserID='" + AppLoginUserID + '\'' +
                '}';
    }
}
