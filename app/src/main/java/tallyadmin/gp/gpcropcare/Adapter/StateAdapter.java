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

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position)
    {
        holder.CmpShortNameTextView.setText(states.get(position).getCmpShortName());
        holder.oneLtrTextView.setText(states.get(position).getOneLtr());
        holder.fiveHundredMlTextView.setText(states.get(position).getFiveHundredMl());
        holder.twoFiftyMlTextView.setText(states.get(position).getTwoFiftyMl());
        holder.oneHundredMlTextView.setText(states.get(position).getOneHundredMl());
        holder.twentyMlTextView.setText(states.get(position).getTwentyMl());
        holder.totalMlTextView.setText(states.get(position).getTotalMl());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView CmpShortNameTextView, oneLtrTextView, fiveHundredMlTextView, twoFiftyMlTextView, oneHundredMlTextView, twentyMlTextView,totalMlTextView;

        OnStateAdapterListener mOnStateAdapterListener;
        public ViewHolder(@NonNull View itemView, OnStateAdapterListener listener)
        {
            super(itemView);
            this.mOnStateAdapterListener = listener;
            CmpShortNameTextView = itemView.findViewById(R.id.CmpShortNameId);
            oneLtrTextView = itemView.findViewById(R.id.oneLtrId);
            fiveHundredMlTextView = itemView.findViewById(R.id.fiveHundredMl);
            twoFiftyMlTextView = itemView.findViewById(R.id.twoFiftyMlId);
            oneHundredMlTextView = itemView.findViewById(R.id.oneHundredMlId);
            twentyMlTextView = itemView.findViewById(R.id.twentyMlId);
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
