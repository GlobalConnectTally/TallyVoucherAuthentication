package tallyadmin.gp.gpcropcare.Fragements;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tallyadmin.gp.gpcropcare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvoicefullFragment extends Fragment {
    TextView itemn,mfgdate,exdate,bathname,vouchern,rate,amounti,ledger,quantity,godwn,distpr;
    Bundle bundle;


    public InvoicefullFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoicefull, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbarSomo);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Invoice Item details");

        itemn = view.findViewById(R.id.text_itemn);
        bathname = view.findViewById(R.id.txt_parbatch);
        vouchern = view.findViewById(R.id.txt_iVnumber);
        rate = view.findViewById(R.id.txt_irate);
        amounti = view.findViewById(R.id.txt_invamount);
        ledger = view.findViewById(R.id.txt_ivtype2);
        quantity = view.findViewById(R.id.txt_invqty);
        godwn = view.findViewById(R.id.txt_igodown);
        distpr = view.findViewById(R.id.txt_iprc);
        exdate = view.findViewById(R.id.txt_iexdate);
        mfgdate = view.findViewById(R.id.txt_ivdate);

        bundle = this.getArguments();

        getData();


        return view;
    }

    private void getData() {
        if(bundle != null){
            itemn.setText(bundle.getString("itemname"));
            bathname.setText(bundle.getString("batchname"));
            godwn.setText(bundle.getString("godwname"));
            ledger.setText(bundle.getString("ledgerm"));
            rate.setText(String.valueOf(bundle.getInt("ratei",0)));
            amounti.setText(bundle.getString("amount"));
            vouchern.setText(String.valueOf(bundle.getInt("voucher",0)));
            quantity.setText(bundle.getString("quantity1"));
            distpr.setText(bundle.getString("InvDiscPerc"));
            mfgdate.setText(bundle.getString("indate"));
            exdate.setText(bundle.getString("inexpdate"));
        }

    }

}
