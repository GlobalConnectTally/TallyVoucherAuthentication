package tallyadmin.gp.gpcropcare.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.ItemListModel;
import tallyadmin.gp.gpcropcare.Model.ListOfCompanyShortName;
import tallyadmin.gp.gpcropcare.Model.ListOfItemParents;


@Dao
public interface LocalDbDAOs {

    /*------------------------ INSERT ----------------------*/
    @Insert
    long[] insertItems(List<Item> items);

    @Insert
    long[] insertCompanies(List<Company> companies);

    /*-------------  SELECT / FETCH --------------------*/
    @Query("SELECT * FROM itemList WHERE CmpShortName = :CmpShortNameValue")
    List<Item> getItemsByCompany(String CmpShortNameValue);

    @Query("SELECT DISTINCT ItemName, ItemParent ,CmpShortName ,ItemOpening, ItemClosing,ItemInwards , ItemOutwards , AppLoginUserID  FROM itemList WHERE CmpShortName = :CmpShortNameValue AND ItemParent = :itemParent AND AppLoginUserID = :AppUserId  ORDER BY ItemName DESC")
    List<ItemListModel> getItemsByCompanyAndParent(String CmpShortNameValue, String itemParent, String AppUserId);

    @Query("SELECT * FROM itemList WHERE ItemParent = :itemParentName")
    List<Item> getItemsByParentName(String itemParentName);

    @Query("SELECT DISTINCT CmpShortName FROM Company")
    List<ListOfCompanyShortName> getAllCompanyShortName();

    @Query("SELECT DISTINCT ItemParent FROM itemList ORDER BY ItemParent ASC")
    List<ListOfItemParents> getAllCompanyItemParents();

    /*------------------------ DELETE ----------------------*/
    @Query("DELETE FROM itemList")
    void deleteItems();

    @Query("DELETE FROM Company")
    void deleteCompanies();
}
