package tallyadmin.gp.gpcropcare.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ListView;
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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.NCRDueBillsAdapter;
import tallyadmin.gp.gpcropcare.Model.NCRBill;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import tallyadmin.gp.gpcropcare.utils.VolleyErrors;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORTNrc;


public class NCRDueBills extends AppCompatActivity {
    ListView listView;
    List<NCRBill> arraylist;
    NCRDueBillsAdapter ncrDueBillsAdapter;
    Companysave companydata;
    private VolleyErrors volleyErrors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_c_r_due_bills);

        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("NCR Due Bills");
        companydata = new Companysave(getApplicationContext());
        TextView partyname = findViewById(R.id.ncr_partyname);
        partyname.setText(companydata.getKeyPartyName());
        TextView billvalue = findViewById(R.id.ncr_billvalue);
        billvalue.setText(companydata.getKeyBillAmount());
        TextView reportdate = findViewById(R.id.nce_reportdate);
        reportdate.setText(companydata.getKeyVocherdate());

        listView = (ListView)findViewById(R.id.ncrlist);
        if (!isNetworkConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Set the Alert Dialog Message
            builder.setMessage("Internet connection is required, Please turn on Wifi or Mobile Data")
                    .setCancelable(false)
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // Restart the Activity
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (isNetworkConnected())
        {
            fetchingJSON();
        }

        volleyErrors = new VolleyErrors(this);

    }



public void fetchingJSON() {
    //String urlno = "?CmpGUID=" + companydata.getKeyCmpnGid() + "&LedgerName="+companydata.getKeyPartyName()+ "&LedgerID="+companydata.getKeyLegId()+ "&VchNumber=" + companydata.getVoucher();
    final KProgressHUD Hhdprogress = KProgressHUD.create(NCRDueBills.this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f);
    Hhdprogress.setCancellable(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            dialog.dismiss();
            finish();
        }
    });

    Hhdprogress.show();

    StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
            URL_REPORTNrc,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {


                        JSONObject obj = new JSONObject(response);

                        arraylist = new ArrayList<NCRBill>();
                        JSONArray dataArray = obj.getJSONArray("ReportNcrDueBill");
                        System.out.println("salesorder:"+dataArray);

                        if (dataArray.length() == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NCRDueBills.this);
                            // Set the Alert Dialog Message
                            builder.setMessage("No NcrDueBill found for the selected Bill/Party")
                                    .setCancelable(false)
                                    .setTitle("NcrDueBill Report")
                                    .setNegativeButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                    int id) {
                                                    dialog.dismiss();
                                                    Hhdprogress.dismiss();
                                                    finish();
                                                }
                                            });
                            AlertDialog alert = builder.create();

                            alert.show();


                        }

                        for (int i = 0; i < dataArray.length(); i++) {

                            NCRBill playerModel = new NCRBill();
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            BigDecimal billamo = new BigDecimal(dataobj.getString("BillAmount"));
                            DecimalFormat formatter78 = new DecimalFormat("##,##,###.###");
                            String billamoun = formatter78.format(billamo);
                            playerModel.setAmount(billamoun);


                            String input = dataobj.getString("BillDate");
                            if (input.equals("")){
                                playerModel.setBilldate(input);
                            }else{
                                //String output = input.substring(0, 10);
                                playerModel.setBilldate(input);
                            }

                            playerModel.setBillno(dataobj.getString("BillNo"));

                            String inputdue = dataobj.getString("DueDate");
                            if (inputdue.equals("")){
                                playerModel.setDuedate(inputdue);
                            }else{
                                //String outputdue = inputdue.substring(0, 10);
                                playerModel.setDuedate(inputdue);
                            }

                            arraylist.add(playerModel);
                        }

                        Hhdprogress.dismiss();
                        setupRecycler();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),
                                volleyErrors.exceptionMessage(error).toString(),
                                Toast.LENGTH_SHORT).show();

                        Hhdprogress.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("CmpGUID", companydata.getKeyCmpnGid());
                        params.put("LedgerName", companydata.getKeyPartyName());
                        params.put("LedgerID", companydata.getKeyLegId());
                        params.put("VchNumber", companydata.getVoucher());
                        return params;
                    }
    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
}

    private void setupRecycler() {

        ncrDueBillsAdapter = new NCRDueBillsAdapter(this,arraylist);
        listView.setAdapter(ncrDueBillsAdapter);

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}