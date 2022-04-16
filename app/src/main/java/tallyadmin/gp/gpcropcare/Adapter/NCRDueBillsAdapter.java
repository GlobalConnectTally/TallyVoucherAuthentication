package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import tallyadmin.gp.gpcropcare.Model.NCRBill;
import tallyadmin.gp.gpcropcare.R;

public class NCRDueBillsAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<NCRBill> listoforder ;
    private ArrayList<NCRBill> originalList;

    public NCRDueBillsAdapter(Context mContext, List<NCRBill> listoforder) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.listoforder = listoforder;
//        ArrayList<NCRBill> itemlist = new ArrayList<>();
//        itemlist.addAll(listoforder);
//        this.originalList = new ArrayList<NCRBill>();
//        this.originalList.addAll(listoforder);
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
        holder.Billno.setText(listoforder.get(position).getBillno());
        holder.Billdate.setText(listoforder.get(position).getBilldate());
        holder.Billamount.setText(listoforder.get(position).getAmount());
        holder.Duedate.setText(listoforder.get(position).getDuedate());


        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.billno)
        TextView Billno;
        @BindView(R.id.billdate)
        TextView Billdate;
        @BindView(R.id.billamount)
        TextView Billamount;
        @BindView(R.id.duedate)
        TextView Duedate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


