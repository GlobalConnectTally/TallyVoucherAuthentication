package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tallyadmin.gp.gpcropcare.Activities.SalesOrderActivity;
import tallyadmin.gp.gpcropcare.Model.Report;
import tallyadmin.gp.gpcropcare.Model.Reportchild;
import tallyadmin.gp.gpcropcare.Model.SalesOrder;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder>{
    private ArrayList<Report> reports;
    Context context;

    ReportInnerAdapter reportInnerAdapter;
//    Companysave companydata;
//    UserInfo userInfo;
//    SalesOrderActivity salesOrderActivity;
//    int masterID;


    public ReportAdapter(ArrayList<Report> reports, Context context) {
        this.reports = reports;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.reportitem, parent, false);

        return new ReportAdapter.ReportViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ReportAdapter.ReportViewHolder holder, final int position) {
        holder.category.setText(reports.get(position).getCategoryName());
        holder.alawable.setText(reports.get(position).getAllowedSubGrp());

        String creditdayvale = String.valueOf(reports.get(position).getCreditDays());
        if (creditdayvale.equals("#~#") || creditdayvale.equals("")){
            holder.creditday.setText("");
        }else{
            holder.creditday.setText(creditdayvale);
        }

        String credamuntvalue = reports.get(position).getCreditAmt();
        if (credamuntvalue.equals("#~#") || credamuntvalue.equals("")){
            holder.credamt.setText("");
        }else{
            holder.credamt.setText(credamuntvalue);
        }
        String salesamountvalue = reports.get(position).getSalesAmount();
        if (salesamountvalue.equals("#~#") || salesamountvalue.equals("")){
            holder.salesamnt.setText("");
        }else{
            holder.salesamnt.setText(salesamountvalue);
        }
        String salesqntyvalue = String.valueOf(reports.get(position).getSalesQuantity());
        if (salesqntyvalue.equals("#~#") || salesqntyvalue.equals("")){
            holder.salesqnty.setText("");
        }else{
            holder.salesqnty.setText(salesqntyvalue);
        }
        String themiaishivalue = reports.get(position).getThe120Days();
        if (themiaishivalue.equals("#~#") || themiaishivalue.equals("")){
            holder.themiaishi.setText("");
        }else{
            holder.themiaishi.setText(themiaishivalue);
        }
        String miarhevalue = reports.get(position).getThe180Days();
        if (miarhevalue.equals("#~#") || miarhevalue.equals("")){
            holder.miarhe.setText("");
        }else{
            holder.miarhe.setText(miarhevalue);
        }

        holder.partname.setText(reports.get(position).getPartyName());

        if (reports.get(position).getAllowedSubGrp().equals("Yes")){
            holder.subalow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                holder.childlist.setVisibility(View.VISIBLE);


                    holder.layout.setVisibility(View.VISIBLE);
                    setrecycler(holder.childlist,reports.get(position).getChildItemList());
                }
            });
        }else{
            holder.subalow.setVisibility(View.INVISIBLE);
            holder.layout.setVisibility(View.INVISIBLE);
        }



//        holder.childlist.setText(String.valueOf(reports.get(position).getTotalAmt()));



    }

    private void setrecycler(RecyclerView childlist, ArrayList<Reportchild> childItemList) {
        reportInnerAdapter = new ReportInnerAdapter(childItemList,context);
        childlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        childlist.setHasFixedSize(true);
        childlist.setAdapter(reportInnerAdapter);


    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView partname,credit,percentage,salesamnt,sqntnt,subnametdaytw;
        TextView category,alawable,subcategory,creditday,credamt,salesqnty,salesfamnt,themiaishi,miarhe;
        ImageView subalow;
        RecyclerView childlist;
        LinearLayout layout;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.category);
            alawable = (TextView) itemView.findViewById(R.id.allowed);
            subalow = itemView.findViewById(R.id.txt_subccc);
            creditday = (TextView) itemView.findViewById(R.id.crdday);
            credamt = (TextView) itemView.findViewById(R.id.crdamt);
            salesamnt = (TextView) itemView.findViewById(R.id.slesamt);
            childlist = itemView.findViewById(R.id.childrecycler);

            salesqnty = (TextView) itemView.findViewById(R.id.crdqnt);
            themiaishi = (TextView) itemView.findViewById(R.id.miaishi);

            miarhe = (TextView) itemView.findViewById(R.id.miathem);
            partname = (TextView) itemView.findViewById(R.id.partnamesb);

            layout = itemView.findViewById(R.id.juuull);


        }
    }
}
