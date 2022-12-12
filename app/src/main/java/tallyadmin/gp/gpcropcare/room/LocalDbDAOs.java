package tallyadmin.gp.gpcropcare.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tallyadmin.gp.gpcropcare.Model.Item;

@Dao
public interface LocalDbDAOs
{
    /*------------------------ INSERT ----------------------*/
    @Insert
    long[] insertItems(List<Item> items);

    /*-------------  SELECT / FETCH --------------------*/
    @Query("SELECT * FROM itemList WHERE CmpShortName = :CmpShortNameValue")
    List<Item> getItemsByCompany(String CmpShortNameValue);

    /*------------------------ DELETE ----------------------*/
    @Query("DELETE FROM itemList")
    void deleteItems();

}
