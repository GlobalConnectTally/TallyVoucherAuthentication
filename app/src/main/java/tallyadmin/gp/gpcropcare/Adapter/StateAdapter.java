package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tallyadmin.gp.gpcropcare.Activities.StockReportActivity;
import tallyadmin.gp.gpcropcare.Model.State;
import tallyadmin.gp.gpcropcare.Model.StateModel;
import tallyadmin.gp.gpcropcare.R;
import static tallyadmin.gp.gpcropcare.Activities.StockReportActivity.states;


public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>
{
    public static List<StateModel> states;
    public List<StateModel> tempStates;
    private final OnStateAdapterListener mOnStateAdapterListener;
    private Context mContext;
    private  int max = 0;

    public StateAdapter(List<StateModel> states, OnStateAdapterListener mOnStateAdapterListener , Context context , int max)
    {
        this.mOnStateAdapterListener = mOnStateAdapterListener;
        this.states = states;
        this.tempStates = states;
        this.mContext = context;
        this.max = max;
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
        /*  holder.CmpShortNameTextView.setText(states.get(position).getCmpShortName());
        holder.itemParent.setText(states.get(position).getItemName().toString());
        holder.totalMlTextView.setText(states.get(position).getItemClosing());
        */
        holder.itemClosingLay.removeAllViews();

        TextView tv2 = new TextView(mContext);
        tv2.setText(states.get(position).getCmpShortName().toString());
        tv2.setTextColor(mContext.getResources().getColor(R.color.color_black));
        tv2.setLayoutParams(new ViewGroup.LayoutParams(
                200, ViewGroup.LayoutParams.WRAP_CONTENT));

        holder.itemClosingLay.addView(tv2);

        for (int n = 0; n < max; n++) {
           if (states.get(position).getItemData().size() >= max)
           {

               TextView tv = new TextView(mContext);
               tv.setText(states.get(position).getItemData().get(n).getItemClosing().toString());
               tv.setTextColor(mContext.getResources().getColor(R.color.color_black));
               tv.setLayoutParams(new ViewGroup.LayoutParams(
                       400, ViewGroup.LayoutParams.WRAP_CONTENT));

               holder.itemClosingLay.addView(tv,n + 1 );

           }
           else
           {
             /*  if (n > states.get(position).getItemData().size()){

                   TextView tv = new TextView(mContext);
                   tv.setText("0.00");
                   tv.setTextColor(mContext.getResources().getColor(R.color.color_black));
                   tv.setLayoutParams(new ViewGroup.LayoutParams(
                           400, ViewGroup.LayoutParams.WRAP_CONTENT));

                   holder.itemClosingLay.addView(tv,n + 1 );

               }else {

                   TextView tv = new TextView(mContext);
                   tv.setText(states.get(position).getItemData().get(p).getItemClosing().toString());
                   tv.setTextColor(mContext.getResources().getColor(R.color.color_black));
                   tv.setLayoutParams(new ViewGroup.LayoutParams(
                           400, ViewGroup.LayoutParams.WRAP_CONTENT));
                   holder.itemClosingLay.addView(tv,n + 1 );


               }*/
           }
        }

        TextView tv3 = new TextView(mContext);
        tv3.setText(String.valueOf(states.get(position).getTotalClosing().toString()));
        tv3.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        tv3.setTextColor(mContext.getResources().getColor(R.color.color_black));
        tv3.setLayoutParams(new ViewGroup.LayoutParams(
                300, ViewGroup.LayoutParams.WRAP_CONTENT));
        holder.itemClosingLay.addView(tv3);
    }

    @Override
    public int getItemCount()
    {
        return states.size() == 0 ? 0 : states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView CmpShortNameTextView,itemParent, totalMlTextView;
        OnStateAdapterListener mOnStateAdapterListener;
        public LinearLayout itemClosingLay;

        public ViewHolder(@NonNull View itemView, OnStateAdapterListener listener)
        {
            super(itemView);
            this.mOnStateAdapterListener = listener;
            //CmpShortNameTextView = itemView.findViewById(R.id.CmpShortNameId);
            //itemParent = itemView.findViewById(R.id.itemParent);
            //totalMlTextView = itemView.findViewById(R.id.totalMlId);
            itemClosingLay = itemView.findViewById(R.id.itemClosingLay);

            itemClosingLay.setOnClickListener(new View.OnClickListener()
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
        void onClickState(int position, List<StateModel> states);
    }

    public Filter getFilter()
    {
         return  new Filter()
         {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {

                FilterResults filterResults = new FilterResults();

               /* if(charSequence != null || charSequence.length() > 0)
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
                }*/
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                //states = (ArrayList<State>) filterResults.values;
                notifyDataSetChanged();
            }
         };
    }

}
