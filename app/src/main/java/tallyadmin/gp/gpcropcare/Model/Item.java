package tallyadmin.gp.gpcropcare.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "itemList")
public class Item
{
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "CmpShortName")
    private String CmpShortName;

    @ColumnInfo(name = "ItemName")
    private String ItemName;

    @ColumnInfo(name = "ItemParent")
    private String ItemParent;

    @ColumnInfo(name = "ItemOpening")
    private String ItemOpening;

    @ColumnInfo(name = "ItemInwards")
    private String ItemInwards;

    @ColumnInfo(name = "ItemOutwards")
    private String ItemOutwards;

    @ColumnInfo(name = "ItemClosing")
    private String ItemClosing;

    public Item()
    {
    }

    public Item(String cmpShortName, String itemName, String itemParent, String itemOpening, String itemInwards, String itemOutwards, String itemClosing)
    {
        CmpShortName = cmpShortName;
        ItemName = itemName;
        ItemParent = itemParent;
        ItemOpening = itemOpening;
        ItemInwards = itemInwards;
        ItemOutwards = itemOutwards;
        ItemClosing = itemClosing;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
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

    @Override
    public String toString() {
        return "Item{" +
                "Id=" + Id +
                ", CmpShortName='" + CmpShortName + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemParent='" + ItemParent + '\'' +
                ", ItemOpening='" + ItemOpening + '\'' +
                ", ItemInwards='" + ItemInwards + '\'' +
                ", ItemOutwards='" + ItemOutwards + '\'' +
                ", ItemClosing='" + ItemClosing + '\'' +
                '}';
    }
}