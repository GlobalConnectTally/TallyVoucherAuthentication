package tallyadmin.gp.gpcropcare.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.ListOfCompanyShortName;

@Dao
public interface LocalDbDAOs
{
    /*------------------------ INSERT ----------------------*/
    @Insert
    long[] insertItems(List<Item> items);

    /*-------------  SELECT / FETCH --------------------*/
    @Query("SELECT * FROM itemList WHERE CmpShortName = :CmpShortNameValue")
    List<Item> getItemsByCompany(String CmpShortNameValue);

    @Query("SELECT * FROM itemList WHERE CmpShortName = :CmpShortNameValue AND ItemParent = :itemParent")
    List<Item> getItemsByCompanyAndParent(String CmpShortNameValue, String itemParent);

    @Query("SELECT * FROM itemList WHERE ItemParent = :itemParentName")
    List<Item> getItemsByParentName(String itemParentName);

    @Query("SELECT DISTINCT CmpShortName FROM itemList")
    List<ListOfCompanyShortName> getAllCompanyShortName();

    /*------------------------ DELETE ----------------------*/
    @Query("DELETE FROM itemList")
    void deleteItems();

}
