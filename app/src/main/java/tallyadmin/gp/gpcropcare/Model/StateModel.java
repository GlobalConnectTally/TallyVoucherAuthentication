package tallyadmin.gp.gpcropcare.Model;

import java.util.List;

public class StateModel {

    private String CmpShortName;
    private List<State> ItemData;
    private String TotalClosing;

    public String getCmpShortName() {
        return CmpShortName;
    }

    public void setCmpShortName(String cmpShortName) {
        CmpShortName = cmpShortName;
    }

    public List<State> getItemData() {
        return ItemData;
    }

    public void setItemData(List<State> itemData) {
        ItemData = itemData;
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
                ", ItemData=" + ItemData +
                ", TotalClosing='" + TotalClosing + '\'' +
                '}';
    }
}
