package tallyadmin.gp.gpcropcare.Model;

import java.util.ArrayList;

public class categorylist {


    String subcategory;

    ArrayList<Subcategorylist> subcategorylists = new ArrayList<>();

    public categorylist(String subcategory, ArrayList<Subcategorylist> subcategorylists) {
        subcategory = subcategory;
        this.subcategorylists = subcategorylists;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory)
    {
        subcategory = subcategory;
    }


    public ArrayList<Subcategorylist> getSubcategorylists() {
        return subcategorylists;
    }

    public void setSubcategorylists(ArrayList<Subcategorylist> subcategorylists) {
        this.subcategorylists = subcategorylists;
    }
}
