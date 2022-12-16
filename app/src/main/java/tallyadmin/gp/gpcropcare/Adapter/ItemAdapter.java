package tallyadmin.gp.gpcropcare.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.State;
import tallyadmin.gp.gpcropcare.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>
{

    public static List<Item> items;

    public ItemAdapter(List<Item> items)
    {
        this.items = items;
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_card,parent,false);
        return new ItemAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position)
    {
        holder.itemNameTextView.setText(items.get(position).getItemName());
        holder.openTextView.setText(items.get(position).getItemOpening());
        holder.inTextView.setText(items.get(position).getItemInwards());
        holder.outTextView.setText(items.get(position).getItemOutwards());
        holder.closeTextView.setText(items.get(position).getItemClosing());
    }


    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView itemNameTextView, openTextView, inTextView, outTextView, closeTextView;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameId);
            openTextView = itemView.findViewById(R.id.itemOpenValueId);
            inTextView = itemView.findViewById(R.id.itemInValueId);
            outTextView = itemView.findViewById(R.id.itemOutValueId);
            closeTextView = itemView.findViewById(R.id.itemCloseValueId);
        }
    }

}
