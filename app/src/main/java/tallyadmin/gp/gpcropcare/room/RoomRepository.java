package tallyadmin.gp.gpcropcare.room;

import android.content.Context;

import java.util.List;

import tallyadmin.gp.gpcropcare.Model.Item;

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

    public List<Item> getItemByParentName(String itemParent)
    {
        return roomAppDatabase.getDAOs().getItemsByParentName(itemParent);
    }

    /*------------------------ DELETE ----------------------*/
    public void deleteItems()
    {
        roomAppDatabase.getDAOs().deleteItems();
    }

}
