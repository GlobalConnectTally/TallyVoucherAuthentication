package tallyadmin.gp.gpcropcare.Activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.SalesTransaAdapter;
import tallyadmin.gp.gpcropcare.Adapter.SendAllAdapter;
import tallyadmin.gp.gpcropcare.Model.SalesTransactionDetails;
import tallyadmin.gp.gpcropcare.Model.SendAllDetailsLed;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import tallyadmin.gp.gpcropcare.utils.VolleyErrors;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_SALESTRANS;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_SMS;

public class ShowtransactionOrderActivity extends AppCompatActivity {

    TextView soname,partname,narration,date,voucheno,vouchertype,amount,reffno;
    TextView authorize,reject,showfrag;
    Companysave companysave;
    UserInfo userInfo;
    String masterId;
    RecyclerView recycler, recyl_trans;
    ArrayList<SalesTransactionDetails> Saleslist;
    ArrayList<SendAllDetailsLed> Sendalldetailld;
    private SendAllAdapter sendAllAdapterm;
    private SalesTransaAdapter salesOrderAdapter;
    LinearLayout hideview,hidemoon2;
    ImageView hidebtn;
    String ordernn,tallymobileno,AuthenticationFlag;
    private VolleyErrors volleyErrors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtransactionew);

        companysave = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());

        volleyErrors = new VolleyErrors(this);

        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Vouchertype")+" Details");
        //
//        TextView tooltext = findViewById(R.id.voucher_tool);
//        tooltext.setText(companysave.getVoucher());

        soname = findViewById(R.id.soname);
        partname = findViewById(R.id.txt_party);
        //narration = findViewById(R.id.txt_narration);
        date = findViewById(R.id.txdate);


        amount = findViewById(R.id.txt_amount);
        voucheno = findViewById(R.id.txt_Vnumber);
        //vouchertype = findViewById(R.id.txt_vtype2);
        reffno = findViewById(R.id.txt_ReffNo);

        authorize = findViewById(R.id.btn_author);
        reject = findViewById(R.id.btn_reject);
        showfrag = findViewById(R.id.txt_show);

        recycler = findViewById(R.id.recyl_transaction);
        recyl_trans = findViewById(R.id.recyl_transdisc);
        ordernn = getIntent().getStringExtra("VoucherNo");
        tallymobileno = getIntent().getStringExtra("TallyUserMobNo");

        String rejectString = userInfo.getAllowReject();
        String acceptString = userInfo.getAllowApprove();

        if(rejectString.equals("Yes")){
            reject.setVisibility(View.VISIBLE);
        }else {
            reject.setVisibility(View.INVISIBLE);
        }

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rejectinvo();
            }
        });

        if(acceptString.equals("Yes")){
            authorize.setVisibility(View.VISIBLE);
        }else {
            authorize.setVisibility(View.INVISIBLE);
        }

        authorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Authorize();
            }
        });


        masterId = String.valueOf(getIntent().getIntExtra("MasterId",0));

        showfrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowtransactionOrderActivity.this,Sixreportactivity.class);
                companysave.setMasterId(getIntent().getIntExtra("MasterId",0));
                startActivity(intent);
                finish();
            }
        });

        getData();

        fetchingJSON();

        /* hidebtn = findViewById(R.id.hideunhide);
        hidemoon2 = findViewById(R.id.moon);
        hideview = findViewById(R.id.hide);*/
        //        hideview.setVisibility(View.GONE);
//        hidemoon2.setVisibility(View.GONE);
        /* hideview.setVisibility(View.VISIBLE);
        hidemoon2.setVisibility(View.VISIBLE);
        hidebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideview.setVisibility(View.VISIBLE);
                hidemoon2.setVisibility(View.VISIBLE);

            }
        });*/
   }

    private void getData() {

        AuthenticationFlag = getIntent().getStringExtra("AuthenticationFlag");
        String names = getIntent().getStringExtra("Address");
        //List<String> list = Arrays.asList(names.split(","));
         /*
        if(list != null) {
            LinearLayout linearLayout1 = findViewById(R.id.chip_container);
            for(int i = 0; i < list.size() ; i++){
                final View view = LayoutInflater.from(ShowtransactionOrderActivity.this).inflate(R.layout.chips_view, null);
                ((TextView) view.findViewById(R.id.txt_address)).setText(new StringBuilder(list.get(i)).append(","));
                linearLayout1.addView(view);
            }
        }
        else {
            LinearLayout linearLayout = findViewById(R.id.chip_container);
            for(int i = 0; i < list.size() ; i++){
                final View view = LayoutInflater.from(ShowtransactionOrderActivity.this).inflate(R.layout.chips_view, null);
                ((TextView) view.findViewById(R.id.txt_address)).setText("No address added");
                linearLayout.addView(view);
            }


        }*/

        String valuesonname = getIntent().getStringExtra("BayerName");
        if (valuesonname.equals("#~#") || valuesonname.equals("")){
            soname.setText("");
        }else{
            soname.setText(valuesonname);
        }
        String valuepartname = getIntent().getStringExtra("PartName");
        if (valuepartname.equals("#~#") || valuepartname.equals("")){
            partname.setText("");
        }else{
            partname.setText(valuepartname);
        }
        String valueamount = getIntent().getStringExtra("Amount");
        if (valueamount.equals("#~#") || valueamount.equals("")){
            amount.setText("");
        }else{
            amount.setText(valueamount);
        }
        String valuedate = getIntent().getStringExtra("Date");
        if (valuedate.equals("#~#") || valuedate.equals("")){
            date.setText("");
        }else{
            date.setText(valuedate);
        }
        //        String valuenarration = getIntent().getStringExtra("Narration");
//        if (valuenarration.equals("#~#") || valuenarration.equals("")){
//            narration.setText("");
//        }else{
//            narration.setText(valuenarration);
//        }
        String valuereffno = getIntent().getStringExtra("Reffno");
        if(valuereffno.equals("#~#") || valuereffno.equals("")){
            reffno.setText("");
        }else{
            reffno.setText(valuereffno);
        }
       String valuevoucheno = getIntent().getStringExtra("VoucherNo");
        if(valuevoucheno.equals("#~#") || valuevoucheno.equals("")){
            voucheno.setText("");
        }else{
            voucheno.setText(valuevoucheno);
        }
        //        String valuevouchertype = getIntent().getStringExtra("Vouchertype");
//        if(valuevouchertype.equals("#~#") || valuevouchertype.equals("")){
//            vouchertype.setText("");
//        }else{
//            vouchertype.setText(valuevouchertype);
//        }
    }

    private void Rejectinvo() {
        //rejectmethod
        final AlertDialog.Builder settingdialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View settinview= inflater.inflate(R.layout.rejectmarks_layoutb, null);
        TextView Okbtn = settinview.findViewById(R.id.btn_ok);
        TextView rejct = settinview.findViewById(R.id.btn_cancel);

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
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    int result = obj.getInt("Status");
                                    if (result==1){
                                        Toast.makeText(ShowtransactionOrderActivity.this,"Authorized success full",Toast.LENGTH_SHORT).show();
                                        final String message =   "Dear User Your Transaction With OrderNo -" +ordernn+ "is then Rejected";

                                        sendmessager();
                                        startActivity(new Intent(ShowtransactionOrderActivity.this,SalesOrderActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(ShowtransactionOrderActivity.this,"Authorization Failed"+ "  "+result,Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(
                                        getApplicationContext(),
                                        volleyErrors.exceptionMessage(error).toString(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                        params.put("CmpGUID",companysave.getKeyCmpnGid());
                        params.put("MasterID",masterId);
                        params.put("AuthenticationFlag","R");
                        params.put("Remark",remark);
                        params.put("TransactionType","1");
                        return params;
                    }
                };

                VolleySingleton.getInstance(ShowtransactionOrderActivity.this).addToRequestQueue(stringRequest);

            }
        });


        alertDialog.show();
    }

    private void Authorize() {
        //authorize method

        final String message =   "Dear User Your Transaction With OrderNo -" +ordernn+ "is Approved";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AUTHORIZE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            int result = obj.getInt("Status");
                            if (result==1){
                                Toast.makeText(ShowtransactionOrderActivity.this,"Authorized success full",Toast.LENGTH_SHORT).show();

                                if ( userInfo.getSecondLevel().equalsIgnoreCase("Yes") &&
                                        AuthenticationFlag.equalsIgnoreCase("A1")) {
                                    sendmessage();
                                }

                                startActivity(new Intent(ShowtransactionOrderActivity.this,SalesOrderActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(ShowtransactionOrderActivity.this,"Authorization Failed"+ "  "+result,Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getApplicationContext(),
                                volleyErrors.exceptionMessage(error).toString(),
                                Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                params.put("CmpGUID",companysave.getKeyCmpnGid());
                params.put("MasterID",masterId);

                String accessLevel = " ";
                if (  userInfo.getFirstLevel().equalsIgnoreCase("Yes") &&
                        userInfo.getSecondLevel().equalsIgnoreCase("Yes")){

                    if (AuthenticationFlag.equalsIgnoreCase("P")){
                        //B - For Both P & A1
                        accessLevel = "A1";

                    }else {
                        //B - For Both P & A1
                        accessLevel = "A2";
                    }

                }else if (  userInfo.getFirstLevel().equalsIgnoreCase("Yes")
                        && userInfo.getSecondLevel().equalsIgnoreCase("No")){
                    //P - Pending
                    accessLevel = "A1";

                }else if (  userInfo.getFirstLevel().equalsIgnoreCase("No")
                        && userInfo.getSecondLevel().equalsIgnoreCase("Yes")){
                    //A1 - Approved By First Level
                    accessLevel = "A2";

                }else if (  userInfo.getFirstLevel().equalsIgnoreCase("No")
                        && userInfo.getFirstLevel().equalsIgnoreCase("No")){
                    //Unknown - Returns Empty
                    accessLevel = "Unknown";
                }

                params.put("AuthenticationFlag", accessLevel);

                params.put("Remark",".");
                params.put("TransactionType","1");
                return params;
            }
        };

        VolleySingleton.getInstance(ShowtransactionOrderActivity.this).addToRequestQueue(stringRequest);
    }

    private void fetchingJSON() {

        final KProgressHUD Hhdprogress = KProgressHUD.create(ShowtransactionOrderActivity.this)
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

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_SALESTRANS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            Saleslist = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("SalesTransactionsInv");

                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ShowtransactionOrderActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No found inventory data ")
                                        .setCancelable(false)
                                        .setTitle("Sales")
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
                                //recycler.setVisibility(View.INVISIBLE);
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
                            }                            Hhdprogress.dismiss();
                            setupRecycler();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ///get send all orders

                        try {


                            JSONObject obj = new JSONObject(response);
                            Sendalldetailld = new ArrayList<>();
                            JSONArray dataArray2 = obj.getJSONArray("SendAllDetailsLed");


                            if (dataArray2.length() != 0) {

                                for (int i = 0; i < dataArray2.length(); i++) {

                                    SendAllDetailsLed playerModeld = new SendAllDetailsLed();
                                    JSONObject dataobj = dataArray2.getJSONObject(i);
                                    playerModeld.setLedgerName(dataobj.getString("LedgerName"));
                                    playerModeld.setLedgerAmount(dataobj.getString("LedgerAmount"));
                                    playerModeld.setLedgerPerc(dataobj.getString("LedgerPerc"));
                                    playerModeld.setVoucherNumber(dataobj.getString("VoucherNumber"));
                                    Sendalldetailld.add(playerModeld);
                                }

                                setupRecycler2m();
                            }else {
                                recyl_trans.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                         Toast.makeText(
                                getApplicationContext(),
                                volleyErrors.exceptionMessage(error).toString(),
                                Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("CmpGUID", companysave.getKeyCmpnGid());
                params.put("MasterID", String.valueOf(companysave.getKeyMasterId()));
                return params;
            }
        };

        VolleySingleton.getInstance(ShowtransactionOrderActivity.this).addToRequestQueue(stringRequest);


    }

    private void setupRecycler() {
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(ShowtransactionOrderActivity.this, LinearLayoutManager.VERTICAL, false));
        salesOrderAdapter = new SalesTransaAdapter(Saleslist, ShowtransactionOrderActivity.this);
        recycler.setAdapter(salesOrderAdapter);

    }

    private void setupRecycler2m() {
        recyl_trans.setHasFixedSize(true);
        recyl_trans.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        sendAllAdapterm = new SendAllAdapter(Sendalldetailld, getApplicationContext());
        recyl_trans.setAdapter(sendAllAdapterm);
    }

    private void sendmessage() {

        String msg = "Dear User Your Transaction With OrderNo -" +ordernn+ "  is been Approved";
        String mobilenumber = tallymobileno;

        String URLNA = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to="+mobilenumber+"&msg="+msg+"&senderid=GPCCPL&route=4";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLNA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ShowtransactionOrderActivity.this," "+response.toString(),Toast.LENGTH_SHORT).show();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getApplicationContext(),
                                volleyErrors.exceptionMessage(error).toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        VolleySingleton.getInstance(ShowtransactionOrderActivity.this).addToRequestQueue(stringRequest);

    }

    private void sendmessager() {

        String msg = "Dear User Your Transaction With OrderNo -" +ordernn+ "  is been Rejected";

        String mobilenumber = tallymobileno;


        String URLNA = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to="+mobilenumber+"&msg="+msg+"&senderid=GPCCPL&route=4";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLNA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ShowtransactionOrderActivity.this," "+response.toString(),Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getApplicationContext(),
                                volleyErrors.exceptionMessage(error).toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        VolleySingleton.getInstance(ShowtransactionOrderActivity.this).addToRequestQueue(stringRequest);

    }


}
