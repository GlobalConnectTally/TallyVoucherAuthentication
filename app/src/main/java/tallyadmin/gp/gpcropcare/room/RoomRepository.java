package tallyadmin.gp.gpcropcare.room;

import android.content.Context;

import java.util.List;
import java.util.Locale;


import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.ItemListModel;
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

    public long[] insertCompaniesToRoom(List<Company> items)
    {
        return roomAppDatabase.getDAOs().insertCompanies(items);
    }

    /*-------------  SELECT / FETCH --------------------*/
    public List<Item> getItemsByCompany(String CmpShortNameValue)
    {
        return roomAppDatabase.getDAOs().getItemsByCompany(CmpShortNameValue);
    }

    public List<ItemListModel> getItemsByCompanyAndParent(String CmpShortNameValue, String ItemParent, String appUserID)
    {
        return roomAppDatabase.getDAOs().getItemsByCompanyAndParent(CmpShortNameValue,ItemParent,appUserID);
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

    public void deleteCompanies() {
        roomAppDatabase.getDAOs().deleteCompanies();
    }

}
