package tallyadmin.gp.gpcropcare.room;

import android.content.Context;

import java.util.List;
import java.util.Locale;



import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.ListOfCompanyShortName;
import tallyadmin.gp.gpcropcare.Model.ListOfItemParents;

public class RoomRepository
{
    private final RoomAppDatabase roomAppDatabase;

    public RoomRepository(Context context)
    {
        roomAppDatabase = RoomAppDatabase.getRoomAppDatabase(context);
    }

    /*------------------------ INSERT ----------------------*/
    public long[] insertItemsToRoom(List<Item> items)
    {
        return roomAppDatabase.getDAOs().insertItems(items);
    }

    /*-------------  SELECT / FETCH --------------------*/
    public List<Item> getItemsByCompany(String CmpShortNameValue)
    {
        return roomAppDatabase.getDAOs().getItemsByCompany(CmpShortNameValue);
    }

    public List<Item> getItemsByCompanyAndParent(String CmpShortNameValue, String ItemParent)
    {
        return roomAppDatabase.getDAOs().getItemsByCompanyAndParent(CmpShortNameValue,ItemParent);
    }

    public List<Item> getItemByParentName(String itemParent)
    {
        return roomAppDatabase.getDAOs().getItemsByParentName(itemParent.toUpperCase(Locale.ROOT));
    }

    public List<ListOfCompanyShortName> getCompanyShortNames(){
         return  roomAppDatabase.getDAOs().getAllCompanyShortName();
    }

    public List<ListOfItemParents> getAllCompanyItemParents(){
         return  roomAppDatabase.getDAOs().getAllCompanyItemParents();
    }

    /*------------------------ DELETE ----------------------*/
    public void deleteItems()
    {
        roomAppDatabase.getDAOs().deleteItems();
    }

}
