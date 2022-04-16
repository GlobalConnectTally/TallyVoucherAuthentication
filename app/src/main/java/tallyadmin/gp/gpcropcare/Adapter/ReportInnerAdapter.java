package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tallyadmin.gp.gpcropcare.Model.Report;
import tallyadmin.gp.gpcropcare.Model.Reportchild;
import tallyadmin.gp.gpcropcare.R;

public class ReportInnerAdapter extends RecyclerView.Adapter<ReportInnerAdapter.ReportViewHolder>{
    private ArrayList<Reportchild> reports;
    Context context;


    public ReportInnerAdapter(ArrayList<Reportchild> reports, Context context) {
        this.reports = reports;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportInnerAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.reportsubitem, parent, false);

        return new ReportInnerAdapter.ReportViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ReportInnerAdapter.ReportViewHolder holder, int position) {
        String valuesubamet = reports.get(position).getSubcategoryname();
        if(valuesubamet.equals("#~#") || valuesubamet.equals("")){
            holder.subnamet.setText("");
        }else {
            holder.subnamet.setText(valuesubamet);
        }
        String vaulecreditamount = reports.get(position).getCreditAmtsb();
        if(vaulecreditamount.equals("#~#") || vaulecreditamount.equals("")){
            holder.creditamount.setText("");
        }else {
            holder.creditamount.setText(vaulecreditamount);
        }
        String valuepercentage = String.valueOf(reports.get(position).getPercentage());
        if(valuepercentage.equals("#~#") || valuepercentage.equals("")){
            holder.percentage.setText("");
        }else {
            holder.percentage.setText(valuepercentage);
        }
        String valesalesamnt = reports.get(position).getSalesAmountsb();
        if(valesalesamnt.equals("#~#") || valesalesamnt.equals("")){
            holder.salesamnt.setText("");
        }else {
            holder.salesamnt.setText(valesalesamnt);
        }
        String valuesqntnt = reports.get(position).getSalesQuantitysb();
        if(valuesqntnt.equals("#~#") || valuesqntnt.equals("")){
            holder.sqntnt.setText("");
        }else {
            holder.sqntnt.setText(valuesqntnt);
        }
        String valuecreditday = String.valueOf(reports.get(position).getCreditDayssb());
        if(valuecreditday.equals("#~#") || valuecreditday.equals("")){
            holder.creditday.setText("");
        }else {
            holder.creditday.setText(valuecreditday);
        }
        String valuethemia = reports.get(position).getThe120Days();
        if(valuethemia.equals("#~#") || valuethemia.equals("")){
            holder.themia.setText("");
        }else {
            holder.themia.setText(valuethemia);
        }
       String valueathema = String.valueOf(reports.get(position).getThe180Days());
        if(valueathema.equals("#~#") || valueathema.equals("")){
            holder.athema.setText("");
        }else {
            holder.athema.setText(valueathema);
        }

    }



    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView subnamet,creditamount,percentage,salesamnt,sqntnt,subnametdaytw;
        TextView themia,athema,subcategory,creditday,credamt,salesqnty,salesfamnt,themiaishi,miarhe;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);


            subnamet = (TextView) itemView.findViewById(R.id.subnamet);
            creditamount = (TextView) itemView.findViewById(R.id.texto_crd);
            percentage = itemView.findViewById(R.id.texto_pcdt);
            salesamnt = (TextView) itemView.findViewById(R.id.txt_slsamountt);
            sqntnt = (TextView) itemView.findViewById(R.id.txt_sq);
            creditday = (TextView) itemView.findViewById(R.id.txt_daycr);
            themia = (TextView) itemView.findViewById(R.id.txt_miasb);
            athema = (TextView) itemView.findViewById(R.id.txt_themasb);



        }
    }
}
