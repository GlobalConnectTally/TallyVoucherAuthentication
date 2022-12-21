package tallyadmin.gp.gpcropcare.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.ListOfItemParents;
import tallyadmin.gp.gpcropcare.R;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ViewHolder> implements Filterable {

    private  ArrayList<ListOfItemParents> modelArrayList = new ArrayList<>();
    private  ArrayList<ListOfItemParents> FilterModelArrayList = new ArrayList<>();
    private Context context;
    private OnItemClickListenaer mOnItemClickListenaer;


    public ItemsListAdapter(Context context, ArrayList<ListOfItemParents> modelArrayList, OnItemClickListenaer onItemClickListenaer){
        this.context = context;
        this.modelArrayList.addAll(modelArrayList) ;
        this.FilterModelArrayList.addAll(modelArrayList) ;
        this.mOnItemClickListenaer = onItemClickListenaer;
    }

    public void updateList(ArrayList<ListOfItemParents> filterArrayList){
          this.FilterModelArrayList = filterArrayList;
           FilterModelArrayList.addAll(filterArrayList);
          notifyDataSetChanged();
    }

    public void addItem(ListOfItemParents itemMdel){
        this.FilterModelArrayList.add(itemMdel);
        notifyItemInserted(FilterModelArrayList.size()-1);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.an_item_card_dialog,parent,false);
        return new ViewHolder(view,mOnItemClickListenaer);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(FilterModelArrayList.get(position).getItemParent().toUpperCase().toString());
        holder.textView.setTag(position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return FilterModelArrayList ==null ? 0 : FilterModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    FilterModelArrayList = modelArrayList;
                } else {
                    ArrayList<ListOfItemParents> filteredList = new ArrayList<>();

                    for (ListOfItemParents row : modelArrayList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getItemParent().toLowerCase().contains(charString.toLowerCase()) || row.getItemParent().contains(constraint)) {
                            filteredList.add(row);
                        }
                    }

                    FilterModelArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = FilterModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                FilterModelArrayList = (ArrayList<ListOfItemParents>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView textView;
        OnItemClickListenaer onItemClickListenaer;

        public void bind(int pos){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos2 = (int) textView.getTag();
                    onItemClickListenaer.OnItemClick(FilterModelArrayList.get(pos2));
                }
            });
        }

        public ViewHolder(@NonNull View itemView, OnItemClickListenaer onItemClickListenaer) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemDesc);


            this.onItemClickListenaer = onItemClickListenaer;

            //  itemView.setOnClickListener(this);
        }

       /* @Override
        public void onClick(View v) {
            onItemClickListenaer.OnItemClick(getAdapterPosition());

        }*/
    }


    public ArrayList<ListOfItemParents> getItemList(){
        return FilterModelArrayList;
    }

    public interface OnItemClickListenaer{
        void OnItemClick(ListOfItemParents list);
    }

}
