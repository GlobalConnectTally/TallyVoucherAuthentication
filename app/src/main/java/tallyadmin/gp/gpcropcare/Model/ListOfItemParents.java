package tallyadmin.gp.gpcropcare.Model;

 public class ListOfItemParents {

    String ItemParent;

    public String getItemParent() {
        return ItemParent;
    }

    public void setItemParent(String itemParent) {
        ItemParent = itemParent;
    }

    @Override
    public String toString() {
        return "{" +
                "ItemParent='" + ItemParent + '\'' +
                '}';
    }
}
