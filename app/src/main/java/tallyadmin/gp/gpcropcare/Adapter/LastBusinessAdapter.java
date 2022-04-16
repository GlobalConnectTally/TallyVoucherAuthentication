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
import tallyadmin.gp.gpcropcare.Model.LastBusiness;
import tallyadmin.gp.gpcropcare.R;

public class LastBusinessAdapter  extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<LastBusiness> listoforder = null;
    private ArrayList<LastBusiness> originalList;

    public LastBusinessAdapter(Context mContext, List<LastBusiness> listofitem) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.listoforder = listofitem;
        ArrayList<LastBusiness> itemlist = new ArrayList<>();
        itemlist.addAll(listoforder);
        this.originalList = new ArrayList<LastBusiness>();
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
            convertView = inflater.inflate(R.layout.business_date, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.PGRbs.setText(listoforder.get(position).getPgr());
        holder.Pesti.setText(listoforder.get(position).getPestisides());
        holder.Royalbs.setText(listoforder.get(position).getRoyal());
        holder.Premiumbs.setText(listoforder.get(position).getPremium());
        holder.Classicbs.setText(listoforder.get(position).getClassic());
        holder.Dustbs.setText(listoforder.get(position).getDust());
        /*int valuetotal = Integer.parseInt(String.valueOf(holder.PGRbs.getText())+String.valueOf(holder.Pesti.getText())+String.valueOf(holder.Royalbs.getText())+String.valueOf(holder.Premiumbs.getText())+String.valueOf(holder.Classicbs.getText())+String.valueOf(holder.Dustbs.getText()));
        holder.Totalbs.setText(valuetotal);*/



        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.pgr_bs)
        TextView PGRbs;
        @BindView(R.id.pesti_bs)
        TextView Pesti;
        @BindView(R.id.royal_bs)
        TextView Royalbs;
        @BindView(R.id.premium_bs)
        TextView Premiumbs;
        @BindView(R.id.classic_bs)
        TextView Classicbs;
        @BindView(R.id.dust_bs)
        TextView Dustbs;
        @BindView(R.id.total_bs)
        TextView Totalbs;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
