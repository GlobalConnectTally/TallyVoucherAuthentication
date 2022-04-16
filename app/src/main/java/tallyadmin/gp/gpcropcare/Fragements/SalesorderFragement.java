package tallyadmin.gp.gpcropcare.Fragements;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.SalesTransaAdapter;
import tallyadmin.gp.gpcropcare.Adapter.SendAllAdapter;
import tallyadmin.gp.gpcropcare.Model.SalesTransactionDetails;
import tallyadmin.gp.gpcropcare.Model.SendAllDetailsLed;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_SALESTRANS;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesorderFragement extends Fragment {

    RecyclerView recycler,recyl_trans;
    ArrayList<SalesTransactionDetails> Saleslist;

    ArrayList<SendAllDetailsLed> Sendalldetailld;
    private SendAllAdapter sendAllAdapterm;
    private SalesTransaAdapter salesOrderAdapter;
    private static ProgressDialog mProgressDialog;
    Companysave companydata;
    UserInfo userInfo;
    int masterId;

    Button approve_btn,rejectbtn;


    public SalesorderFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_salesorder, container, false);

        companydata = new Companysave(getContext());
        userInfo = new UserInfo(getContext());



        Toolbar toolbar = view.findViewById(R.id.toolbarSof);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);

        TextView tooltext = view.findViewById(R.id.voucher_tool);
        tooltext.setText(companydata.getVoucher());


        approve_btn = view.findViewById(R.id.btn_authorfn);
        rejectbtn = view.findViewById(R.id.btn_rejectf);
        recycler = view.findViewById(R.id.recyl_transaction);
        recyl_trans = view.findViewById(R.id.recyl_transdisc);

        approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authorize();
            }
        });

        rejectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rejectinvo();
            }
        });

        fetchingJSON();

        return view;
    }

    private void Rejectinvo() {
        //rejectmethod
        final AlertDialog.Builder settingdialog = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View settinview= inflater.inflate(R.layout.rejectmarks_layoutb, null);
        Button Okbtn = settinview.findViewById(R.id.btn_ok);
        Button rejct = settinview.findViewById(R.id.btn_cancel);

        final EditText remarkt = settinview.findViewById(R.id.remark);
        settingdialog.setView(settinview);
        final AlertDialog alertDialog = settingdialog.create();

        rejct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        Okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String remark = remarkt.getText().toString();

                if (TextUtils.isEmpty(remark)) {
                    remarkt.setError("Please enter reason for rejection");
                    remarkt.requestFocus();
                    return;
                }


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AUTHORIZE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                alertDialog.dismiss();
                                Toast.makeText(getContext(),"Success full",Toast.LENGTH_SHORT).show();


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                        params.put("CmpGUID",companydata.getKeyCmpnGid());
                        params.put("MasterID",String.valueOf(companydata.getKeyMasterId()));
                        params.put("AuthenticationFlag","R");
                        params.put("Remark",remark);
                        return params;
                    }
                };

                VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

            }
        });


        alertDialog.show();
    }

    private void Authorize() {
        //authorize method

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AUTHORIZE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);

                            int result = obj.getInt("Status");
                            if (result==1){
                                Toast.makeText(getContext(),"Authorized success full",Toast.LENGTH_SHORT).show();


                            }
                            else {
                                Toast.makeText(getContext(),"Authorization Failed"+ "  "+result,Toast.LENGTH_SHORT).show();
                            }















                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



//                        if (Stutas==(response.equals("Status"))
//                                {
//
//                        }
//                       Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(),"CLICKEDDDDD",Toast.LENGTH_SHORT);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                params.put("CmpGUID",companydata.getKeyCmpnGid());
                params.put("MasterID",String.valueOf(companydata.getKeyMasterId()));
                params.put("AuthenticationFlag","A");
                params.put("Remark","ok to proceed");
                params.put("TransactionType","1");
                return params;
            }
        };

        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(stringRequest);

    }


    private void fetchingJSON() {



      final KProgressHUD Hhdprogress = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        Hhdprogress.setCancellable(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();

            }
        });
        Hhdprogress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SALESTRANS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {



                            JSONObject obj = new JSONObject(response);

                            Saleslist = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("SalesTransactionsInv");

                            if (dataArray.length()==0){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                // Set the Alert Dialog Message
                                builder.setMessage("No data added for sales yet")
                                        .setCancelable(false)
                                        .setTitle("Sales Order")
                                        .setNegativeButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int id) {
                                                        dialog.dismiss();
                                                        Hhdprogress.dismiss();

                                                    }
                                                });
                                AlertDialog alert = builder.create();

                                alert.show();


                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                SalesTransactionDetails playerModel = new SalesTransactionDetails();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setItemName(dataobj.getString("ItemName"));
                                playerModel.setSalesLedgerName(dataobj.getString("SalesLedgerName"));
                                playerModel.setInvBatchName(dataobj.getString("InvBatchName"));
                                playerModel.setInvGodownName(dataobj.getString("InvGodownName"));
                                playerModel.setInvQty(dataobj.getString("InvQty"));
                                playerModel.setInvAmount(dataobj.getString("InvAmount"));
                                playerModel.setInvRate(dataobj.getString("InvRate"));
                                playerModel.setVoucherNumber(dataobj.getString("VoucherNumber"));
                                playerModel.setInvMfgDate(dataobj.getString("InvMfgDate"));
                                playerModel.setInvExpDate(dataobj.getString("InvExpDate"));
                                playerModel.setInvDiscPerc(dataobj.getString("InvDiscPerc"));




                                Saleslist.add(playerModel);




                            }
                            Hhdprogress.dismiss();
                            setupRecycler();





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ///get send all orders

                        try {



                            JSONObject obj = new JSONObject(response);

                            Sendalldetailld = new ArrayList<>();
                            JSONArray dataArray2  = obj.getJSONArray("SendAllDetailsLed");

//                            Toast.makeText(getContext(),""+,Toast.LENGTH_LONG).show();

                            for (int i = 0; i < dataArray2.length(); i++) {

                                SendAllDetailsLed playerModeld = new SendAllDetailsLed();
                                JSONObject dataobj = dataArray2.getJSONObject(i);

                              //  Toast.makeText(getContext(),""+dataobj,Toast.LENGTH_LONG).show();

                                playerModeld.setLedgerName(dataobj.getString("LedgerName"));
                                playerModeld.setLedgerAmount(dataobj.getString("LedgerAmount"));

//                                playerModeld.setLedgerPerc(dataobj.getInt("LedgerPerc"));
                                playerModeld.setVoucherNumber(dataobj.getString("VoucherNumber"));




                                Sendalldetailld.add(playerModeld);




                            }

                            setupRecycler2m();






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                params.put("CmpGUID",companydata.getKeyCmpnGid());
                params.put("MasterID",String.valueOf(companydata.getKeyMasterId()));
                return params;
            }
        };

        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(stringRequest);


    }

    private void setupRecycler(){

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        salesOrderAdapter = new SalesTransaAdapter(Saleslist,getContext());
        recycler.setAdapter(salesOrderAdapter);

    }

    private void setupRecycler2m(){

        recyl_trans.setHasFixedSize(true);
        recyl_trans.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sendAllAdapterm=new SendAllAdapter(Sendalldetailld,getContext());
        recyl_trans.setAdapter(sendAllAdapterm);

    }




}
