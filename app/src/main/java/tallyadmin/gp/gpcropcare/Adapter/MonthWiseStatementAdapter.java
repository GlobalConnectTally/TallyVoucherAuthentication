package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tallyadmin.gp.gpcropcare.Model.MonthlyReportDetails;
import tallyadmin.gp.gpcropcare.R;

public class MonthWiseStatementAdapter extends RecyclerView.Adapter<MonthWiseStatementAdapter.ViewHolder> {


    private final Context mContext;
    private final ArrayList<MonthlyReportDetails> mMonthlyReportDetails;

    public MonthWiseStatementAdapter(ArrayList<MonthlyReportDetails> reportDetails , Context context) {
        this.mContext = context;
        this.mMonthlyReportDetails = reportDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.an_item_month_wise_statement,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MonthlyReportDetails monthlyReportDetails = mMonthlyReportDetails.get(position);

        holder.aMonth.setText(
                monthlyReportDetails.getMonth()
        );

        double totalOpening = monthlyReportDetails.getOpening().getNcr() + monthlyReportDetails.getOpening().getRpl();
        holder.totalOpening.setText(
                String.valueOf(totalOpening)
        );
        holder.openingNcr.setText(
               String.valueOf(monthlyReportDetails.getOpening().getNcr())
        );
        holder.openingRpl.setText(
                String.valueOf(
                        monthlyReportDetails.getOpening().getRpl()
                )
        );

        double totalDebt = monthlyReportDetails.getDebt().getNcr() + monthlyReportDetails.getDebt().getRpl();
        holder.totalDebt.setText(
                String.valueOf(totalDebt)
        );
        holder.debtNcr.setText(
                String.valueOf(
                        monthlyReportDetails.getDebt().getNcr()
                )
        );
        holder.debtRpl.setText(
                String.valueOf(
                        monthlyReportDetails.getDebt().getRpl()
                )
        );

        double totalCredit= monthlyReportDetails.getCredit().getNcr() + monthlyReportDetails.getCredit().getRpl();
        holder.totalCredit.setText(
                String.valueOf(totalCredit)
        );
        holder.creditNcr.setText(
                String.valueOf(
                        monthlyReportDetails.getCredit().getNcr()
                )
        );
        holder.creditRpl.setText(
                String.valueOf(
                        monthlyReportDetails.getCredit().getRpl()
                )
        );

        double totalClosing = monthlyReportDetails.getClosing().getNcr() + monthlyReportDetails.getClosing().getRpl();
        holder.totalClosing.setText(
                String.valueOf(totalClosing)
        );
        holder.closingNcr.setText(
                String.valueOf(
                        monthlyReportDetails.getClosing().getNcr()
                )
        );
        holder.closingRpl.setText(
                String.valueOf(
                        monthlyReportDetails.getClosing().getRpl()
                )
        );
    }

    @Override
    public int getItemCount() {
        return mMonthlyReportDetails != null
                ? mMonthlyReportDetails.size()
                : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView aMonth;
        TextView totalOpening , openingNcr,openingRpl,
                 totalDebt , debtNcr, debtRpl,
                 totalCredit, creditNcr, creditRpl,
                 totalClosing, closingNcr, closingRpl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            aMonth = itemView.findViewById(R.id.aMonth);

            totalOpening = itemView.findViewById(R.id.totalOpening);
            totalDebt = itemView.findViewById(R.id.totalDebt);
            totalCredit = itemView.findViewById(R.id.totalCredit);
            totalClosing = itemView.findViewById(R.id.totalClosing);

            openingNcr = itemView.findViewById(R.id.ncrOpening);
            debtNcr = itemView.findViewById(R.id.ncrDebt);
            creditNcr = itemView.findViewById(R.id.ncrCredit);
            closingNcr = itemView.findViewById(R.id.ncrClosing);


            openingRpl = itemView.findViewById(R.id.rplOpening);
            debtRpl = itemView.findViewById(R.id.rplDebt);
            creditRpl = itemView.findViewById(R.id.rplCredit);
            closingRpl = itemView.findViewById(R.id.rplClosing);




        }
    }
}
