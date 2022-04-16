package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tallyadmin.gp.gpcropcare.Model.Currentstatus;
import tallyadmin.gp.gpcropcare.Model.NCRBill;
import tallyadmin.gp.gpcropcare.R;

public class CurrentstatusAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Currentstatus> listoforder = null;
    private ArrayList<Currentstatus> originalList;

    public CurrentstatusAdapter(Context mContext, List<Currentstatus> listofitem) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.listoforder = listofitem;
        ArrayList<Currentstatus> itemlist = new ArrayList<>();
        itemlist.addAll(listoforder);
        this.originalList = new ArrayList<Currentstatus>();
        this.originalList.addAll(listoforder);
    }

    @Override
    public int getCount() {
        return listoforder.size();
    }

    @Override
    public Object getItem(int position) {
        return listoforder.get(position);

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ncr_bills, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.opening.setText(listoforder.get(position).getOpening());
        holder.debit.setText(listoforder.get(position).getDebit());
        holder.credit.setText(listoforder.get(position).getCredit());
        holder.closing.setText(listoforder.get(position).getClosing());


        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.billno)
        TextView opening;
        @BindView(R.id.billdate)
        TextView debit;
        @BindView(R.id.billamount)
        TextView credit;
        @BindView(R.id.duedate)
        TextView closing;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
