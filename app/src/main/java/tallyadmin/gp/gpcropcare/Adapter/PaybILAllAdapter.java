package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tallyadmin.gp.gpcropcare.Model.Paybillwise;
import tallyadmin.gp.gpcropcare.R;

public class PaybILAllAdapter extends RecyclerView.Adapter<PaybILAllAdapter.SendViewHolder> {
    private ArrayList<Paybillwise> paybillwises;
    Context context;


    public PaybILAllAdapter(ArrayList<Paybillwise> companieslist, Context context) {
        this.paybillwises = companieslist;
        this.context = context;
    }

    @NonNull
    @Override
    public SendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View itemView = LayoutInflater.from(context)
                .inflate(R.layout.payment_billwiset, parent, false);

        return new SendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SendViewHolder holder, int position) {
        holder.vchname.setText(String.valueOf(paybillwises.get(position).getPartyName()));
        holder.ledgprc.setText(String.valueOf(paybillwises.get(position).getAmount()));
        holder.lname.setText(paybillwises.get(position).getBillDate());
        holder.lamunt.setText(paybillwises.get(position).getBillReff());





    }

    @Override
    public int getItemCount() {
        return paybillwises.size();
    }

    public class SendViewHolder extends RecyclerView.ViewHolder {
        TextView vchname,lname,ledgprc,lamunt;
        public SendViewHolder(@NonNull View itemView) {
            super(itemView);

            vchname = itemView.findViewById(R.id.textp_part);
            lname = itemView.findViewById(R.id.texto_amountpp);
            ledgprc = itemView.findViewById(R.id.texto_datepp);
            lamunt = itemView.findViewById(R.id.txt_reffpp);
        }
    }
}
