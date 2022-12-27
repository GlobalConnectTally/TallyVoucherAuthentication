package tallyadmin.gp.gpcropcare.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tallyadmin.gp.gpcropcare.Model.State;
import tallyadmin.gp.gpcropcare.R;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>
{
    public static List<State> states;
    public List<State> tempStates;
    private final OnStateAdapterListener mOnStateAdapterListener;

    public StateAdapter(List<State> states,OnStateAdapterListener mOnStateAdapterListener)
    {
        this.mOnStateAdapterListener = mOnStateAdapterListener;
        this.states = states;
        this.tempStates = states;
    }


    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.state_item,parent,false);
        return new ViewHolder(listItem,mOnStateAdapterListener);
    }


    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position)
    {
        holder.CmpShortNameTextView.setText(states.get(position).getCmpShortName());
        holder.itemParent.setText(states.get(position).getItemParent().toString());
        holder.totalMlTextView.setText(states.get(position).getTotalClosing());
    }

    @Override
    public int getItemCount()
    {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView CmpShortNameTextView,itemParent, totalMlTextView;

        OnStateAdapterListener mOnStateAdapterListener;
        public ViewHolder(@NonNull View itemView, OnStateAdapterListener listener)
        {
            super(itemView);
            this.mOnStateAdapterListener = listener;
            CmpShortNameTextView = itemView.findViewById(R.id.CmpShortNameId);
            itemParent = itemView.findViewById(R.id.itemParent);
            totalMlTextView = itemView.findViewById(R.id.totalMlId);


            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnStateAdapterListener.onClickState(getAdapterPosition(),states);
                }
            });
        }
    }

    public interface OnStateAdapterListener
    {
        void onClickState(int position, List<State> states);
    }


    public Filter getFilter()
    {
         return  new Filter()
         {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {

                FilterResults filterResults = new FilterResults();

                if(charSequence != null || charSequence.length() > 0)
                {

                    charSequence = charSequence.toString().toLowerCase();
                    ArrayList<State> filterResult = new ArrayList<State>();

                    for (int index = 0; index < tempStates.size(); index++)
                    {
                        if(tempStates.get(index).getCmpShortName().toLowerCase().contains(charSequence))
                        {
                            State stateModel= tempStates.get(index);
                            filterResult.add(stateModel);
                        }
                    }
                    filterResults.count = filterResult.size();
                    filterResults.values = filterResult;
                }
                else
                {
                    synchronized (this)
                    {
                        filterResults.count = tempStates.size();
                        filterResults.values = tempStates;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                states = (ArrayList<State>) filterResults.values;
                notifyDataSetChanged();
            }
         };
    }

}
