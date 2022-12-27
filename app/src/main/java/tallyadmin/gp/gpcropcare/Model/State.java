package tallyadmin.gp.gpcropcare.Model;

public class State
{
    String CmpShortName , ItemParent , TotalClosing;

    public String getCmpShortName() {
        return CmpShortName;
    }

    public void setCmpShortName(String cmpShortName) {
        CmpShortName = cmpShortName;
    }

    public String getItemParent() {
        return ItemParent;
    }

    public void setItemParent(String itemParent) {
        ItemParent = itemParent;
    }

    public String getTotalClosing() {
        return TotalClosing;
    }

    public void setTotalClosing(String totalClosing) {
        TotalClosing = totalClosing;
    }

    @Override
    public String toString() {
        return "{" +
                "CmpShortName='" + CmpShortName + '\'' +
                ", ItemParent='" + ItemParent + '\'' +
                ", TotalClosing='" + TotalClosing + '\'' +
                '}';
    }
}
