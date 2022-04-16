package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tallyadmin.gp.gpcropcare.Model.PaymentTrans;
import tallyadmin.gp.gpcropcare.R;

public class Paylist extends RecyclerView.Adapter<Paylist.PaylistViewHolder> {
    Context context;
    ArrayList<PaymentTrans> paymentArrayList;

    public Paylist(Context context, ArrayList<PaymentTrans> paymentArrayList) {
        this.context = context;
        this.paymentArrayList = paymentArrayList;
    }

    @NonNull
    @Override
    public PaylistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.payment_detailsitemnewt,viewGroup,false);
        return new PaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaylistViewHolder holder, int position) {
        holder.txtpaid.setText(paymentArrayList.get(position).getPaidTo());
        holder.piadby.setText(paymentArrayList.get(position).getPaidBy());
        holder.type.setText(paymentArrayList.get(position).getTransactionType());
        holder.instno.setText(paymentArrayList.get(position).getInstrumentNo());
        holder.instdate.setText(paymentArrayList.get(position).getInstrumentDate());
        holder.accountno.setText(paymentArrayList.get(position).getAccountNo());
        holder.txtifscode.setText(paymentArrayList.get(position).getIFSCCode());
        holder.bankname.setText(paymentArrayList.get(position).getBankName());


        ///back
//        holder.accountno.setBackgroundResource(R.drawable.table_content_cell_bg);
//        holder.txtifscode.setBackgroundResource(R.drawable.table_content_cell_bg);
//        holder.bankname.setBackgroundResource(R.drawable.table_content_cell_bg);


    }

    @Override
    public int getItemCount() {
        return paymentArrayList.size();
    }

    public class PaylistViewHolder extends RecyclerView.ViewHolder {
        TextView txtpaid,piadby,type,instno,instdate,accountno,txtifscode,bankname;
        public PaylistViewHolder(@NonNull View itemView) {
            super(itemView);

            txtpaid = itemView.findViewById(R.id.txt_paidtop);
            piadby = itemView.findViewById(R.id.txt_paidby);
            type = itemView.findViewById(R.id.txt_paytypep);
            instno = itemView.findViewById(R.id.txt_instrument);
            instdate = itemView.findViewById(R.id.txt_pdatep);
            accountno = itemView.findViewById(R.id.txt_bankn0);
            txtifscode = itemView.findViewById(R.id.txt_ifscode);
            bankname = itemView.findViewById(R.id.txt_bankname);

        }
    }
}
