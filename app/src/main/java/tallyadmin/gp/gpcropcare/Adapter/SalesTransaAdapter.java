package tallyadmin.gp.gpcropcare.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import tallyadmin.gp.gpcropcare.Activities.ShowtransactionOrderActivity;
import tallyadmin.gp.gpcropcare.Interface.ItemClickListener;
import tallyadmin.gp.gpcropcare.Model.SalesTransactionDetails;
import tallyadmin.gp.gpcropcare.R;


public class SalesTransaAdapter extends RecyclerView.Adapter<SalesTransaAdapter.SalesTransaViewHolder> {
    private ArrayList<SalesTransactionDetails> orderArrayList;
    Context context;

    public SalesTransaAdapter(ArrayList<SalesTransactionDetails> orderArrayList, Context context) {
        this.orderArrayList = orderArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SalesTransaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_detailsitemnewtitlen, parent, false);

        return new SalesTransaViewHolder(itemView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull SalesTransaViewHolder holder, final int position) {
        String valueitemname = orderArrayList.get(position).getItemName();
        if (valueitemname.equals("#~#") || valueitemname.equals("")){
            holder.item_name.setText("");
        }else{
            holder.item_name.setText(valueitemname);
        }

        /*if ((orderArrayList.get(position).getItemName().length()) >=20){




        }
        else if((orderArrayList.get(position).getItemName().length()) < 20){
            holder.hideprc.setVisibility(View.VISIBLE);
            holder.hiderate1.setVisibility(View.GONE);
            holder.hiderate2.setVisibility(View.GONE);
        }*/
         //hideview;
        String ratevalue = orderArrayList.get(position).getInvRate();
        BigDecimal itmamt = BigDecimal.valueOf(Double.parseDouble(ratevalue));
       // DecimalFormat formatter6 = new DecimalFormat("#######.###");
        //String rate_amt = formatter6.format(itmamt);

        String valuerate = String.valueOf(String.format("%.2f", itmamt));
        if (valuerate.equals("#~#") || valuerate.equals("")){
            holder.rate.setText("");
        }else{
            holder.rate.setText(valuerate);
        }

        String valuetxtamount = orderArrayList.get(position).getInvAmount();
        if (valuetxtamount.equals("#~#") || valuetxtamount.equals("")){
            holder.txt_amount.setText("");
        }else{
            holder.txt_amount.setText(valuetxtamount);
        }
//        String valuebatchname = orderArrayList.get(position).getInvBatchName();
//        if (valuebatchname.equals("#~#") || valuebatchname.equals("")){
//            holder.batchname.setText("");
//        }else{
//            holder.batchname.setText(valuebatchname);
//        }

//        holder.voucher.setText(orderArrayList.get(position).getInvMfgDate());
        String exdate = orderArrayList.get(position).getInvExpDate();
        String exda = "Exp Date: ";
        if (exdate.equals("1900-01-01")){
            exdate = "";
            exda ="";
        }
//        holder.txdatem.setText(exdate);
//        holder.txt_fdex.setText(exda);


        String mfgda = "Mfg Date: ";
        String mfgdate = orderArrayList.get(position).getInvMfgDate();
        if (mfgdate.equals("1900-01-01")){
            mfgdate = "";
            mfgda ="";
        }
//        holder.txt_fdmfg.setText(mfgda);
//        holder.voucher.setText(mfgdate);
//        String valuetxtgodwn = orderArrayList.get(position).getInvGodownName();
//        if (valuetxtgodwn.equals("#~#") || valuetxtgodwn.equals("")){
//            holder.txt_godwn.setText("");
//        }else{
//            holder.txt_godwn.setText(valuetxtgodwn);
//        }


        String name = orderArrayList.get(position).getInvDiscPerc();
        String noad = "0";
        if(name.isEmpty() || name.equals("#~#")){
            name = noad;
        }
       holder.txt_ledger.setText(new StringBuffer("").append(name).append("%").toString());
//        String valuetxtlname = orderArrayList.get(position).getSalesLedgerName();
//        if (valuetxtlname.equals("#~#") || valuetxtlname.equals("")){
//            holder.txt_lname.setText("");
//        }else{
//            holder.txt_lname.setText(valuetxtlname);
//        }
        String valuetxttoquantity = orderArrayList.get(position).getInvQty();
        if (valuetxttoquantity.equals("#~#") || valuetxttoquantity.equals("")){
            holder.txto_quantity.setText("");
        }else{
            holder.txto_quantity.setText(valuetxttoquantity);
        }





//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//
//                Bundle bundle = new Bundle();
//                bundle.putString("itemname",orderArrayList.get(position).getItemName());
//                bundle.putString("batchname",orderArrayList.get(position).getInvBatchName());
//                bundle.putString("godwname",orderArrayList.get(position).getInvGodownName());
//                bundle.putString("ledgerm",orderArrayList.get(position).getSalesLedgerName());
//                bundle.putInt("ratei",orderArrayList.get(position).getInvRate());
//                bundle.putString("amount",orderArrayList.get(position).getInvAmount());
//                bundle.putInt("voucher",orderArrayList.get(position).getVoucherNumber());
//                bundle.putString("quantity1",orderArrayList.get(position).getInvQty());
//                bundle.putString("InvDiscPerc",orderArrayList.get(position).getInvDiscPerc());
//
//
//                bundle.putString("indate",orderArrayList.get(position).getInvMfgDate());
//                bundle.putString("inexpdate",orderArrayList.get(position).getInvExpDate());
//
//                Fragment fragment = new InvoicefullFragment();
//                fragment.setArguments(bundle);
//
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_manager, fragment)
//                        .addToBackStack(null)
//                        .commit();
//
//
//            }
//        });

//        holder.txt_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//
//                Bundle bundle = new Bundle();
//                bundle.putString("itemname",orderArrayList.get(position).getItemName());
//                bundle.putString("batchname",orderArrayList.get(position).getInvBatchName());
//                bundle.putString("godwname",orderArrayList.get(position).getInvGodownName());
//                bundle.putString("ledgerm",orderArrayList.get(position).getSalesLedgerName());
//                bundle.putInt("ratei",orderArrayList.get(position).getInvRate());
//                bundle.putFloat("amount",orderArrayList.get(position).getInvAmount());
//                bundle.putInt("voucher",orderArrayList.get(position).getVoucherNumber());
//                bundle.putString("quantity1",orderArrayList.get(position).getInvQty());
//                bundle.putInt("InvDiscPerc",orderArrayList.get(position).getInvDiscPerc());
//                bundle.putString("indate",orderArrayList.get(position).getInvMfgDate());
//                bundle.putString("inexpdate",orderArrayList.get(position).getInvExpDate());
//
//                Fragment fragment = new InvoicefullFragment();
//                fragment.setArguments(bundle);
//
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_manager, fragment)
//                        .addToBackStack(null)
//                        .commit();
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class SalesTransaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemClickListener itemClickListener;
        public TextView item_name, rate, voucher,batchname,txt_godwn,txt_ledger,txt_lname,txt_fdmfg,txto_quantity,txt_amount,txt_fdex,txdatem,exdate;
        LinearLayout hiderate1,hiderate2,hideprc;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public SalesTransaViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.txt_itemname);
            rate = itemView.findViewById(R.id.txt_Rate);
            voucher = itemView.findViewById(R.id.txt_mfdate);
            //batchname = itemView.findViewById(R.id.txt_batchname);
            //txt_godwn = itemView.findViewById(R.id.txt_godwn);
            txt_ledger = itemView.findViewById(R.id.txt_discM);
//            txdatem = itemView.findViewById(R.id.txt_exdate);
//            txt_fdmfg = itemView.findViewById(R.id.fd_mdgdate);


            //txt_lname = itemView.findViewById(R.id.txt_ledgername);
            txto_quantity = itemView.findViewById(R.id.txt_quantity);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            //txt_fdex = itemView.findViewById(R.id.fd_exdate);


//            hideprc = itemView.findViewById(R.id.hiderateprc);
//            hiderate1 = itemView.findViewById(R.id.hiderate1);
//            hiderate2 = itemView.findViewById(R.id.hiderate2);

          // itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view);

        }
    }
}
