package tallyadmin.gp.gpcropcare.Model;

public class State
{
    String CmpShortName , ItemParent , ItemName, ItemClosing;

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

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

    public String getItemClosing() {
        return ItemClosing;
    }

    public void setItemClosing(String totalClosing) {
        ItemClosing = totalClosing;
    }

    @Override
    public String toString() {
        return "{" +
                " CmpShortName='" + CmpShortName + '\'' +
                ", ItemParent='" + ItemParent + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemClosing='" + ItemClosing + '\'' +
                '}';
    }
}
