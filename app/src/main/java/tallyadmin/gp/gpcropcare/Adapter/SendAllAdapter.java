package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import tallyadmin.gp.gpcropcare.Model.SendAllDetailsLed;
import tallyadmin.gp.gpcropcare.R;


public class SendAllAdapter extends RecyclerView.Adapter<SendAllAdapter.SendViewHolder> {
    private ArrayList<SendAllDetailsLed> companieslist;
    Context context;

    public SendAllAdapter(ArrayList<SendAllDetailsLed> companieslist, Context context) {
        this.companieslist = companieslist;
        this.context = context;
    }

    @NonNull
    @Override
    public SendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View itemView = LayoutInflater.from(context)
                .inflate(R.layout.salesdiscount, parent, false);

        return new SendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SendViewHolder holder, int position) {
        String valueledgprc = String.valueOf(companieslist.get(position).getLedgerAmount());
        if (valueledgprc.equals("#~#") || valueledgprc.equals("")){
            holder.ledgprc.setText("");
        }else{
            holder.ledgprc.setText(valueledgprc);
        }
       String valuelamunt = companieslist.get(position).getLedgerAmount();
        if (valuelamunt.equals("#~#") || valuelamunt.equals("")){
            holder.lamunt.setText("");
        }else{
            holder.lamunt.setText(valuelamunt);
        }
//        holder.lname.setText(companieslist.get(position).getLedgerName());

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class SendViewHolder extends RecyclerView.ViewHolder
    {
        TextView vchname,lname,ledgprc,lamunt;
        public SendViewHolder(@NonNull View itemView)
        {
            super(itemView);
//            lname = itemView.findViewById(R.id.ledgerdi);
            ledgprc = itemView.findViewById(R.id.prddi);
            lamunt = itemView.findViewById(R.id.amountdi);
        }
    }
}
