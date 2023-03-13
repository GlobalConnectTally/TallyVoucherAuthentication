package tallyadmin.gp.gpcropcare.Activities;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORTMonthWiseStatement;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORTNrc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.MonthWiseStatementAdapter;
import tallyadmin.gp.gpcropcare.Model.Closing;
import tallyadmin.gp.gpcropcare.Model.Credit;
import tallyadmin.gp.gpcropcare.Model.Debt;
import tallyadmin.gp.gpcropcare.Model.MonthlyReportDetails;
import tallyadmin.gp.gpcropcare.Model.NCRBill;
import tallyadmin.gp.gpcropcare.Model.Opening;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.ThreadManager;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import tallyadmin.gp.gpcropcare.utils.Tools;
import tallyadmin.gp.gpcropcare.utils.VolleyErrors;

public class MonthWiseStatementActivity extends AppCompatActivity {

    private VolleyErrors volleyErrors;
    private ArrayList<MonthlyReportDetails> arraylist;
    private Tools mTools;
    private MonthWiseStatementAdapter monthWiseStatementAdapter;
    private Companysave companydata;
    private LinearLayout linearTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_wise_statement);

        initView();

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

    private void initView() {

        linearTotal = findViewById(R.id.linearTotal);
        linearTotal.setVisibility(View.GONE);


        companydata = new Companysave(this);
        arraylist = new ArrayList<MonthlyReportDetails>();

        monthWiseStatementAdapter = new MonthWiseStatementAdapter(arraylist,this);

        mTools = new Tools(this);

        Toolbar toolbar = findViewById(R.id.toolbarSo);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.month_wise_statement_rep));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchingJSON() {
        //String urlno = "?CmpGUID=" + companydata.getKeyCmpnGid() + "&LedgerName="+companydata.getKeyPartyName()+ "&LedgerID="+companydata.getKeyLegId()+ "&VchNumber=" + companydata.getVoucher();
        final KProgressHUD Hhdprogress = KProgressHUD.create(this)
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
                URL_REPORTMonthWiseStatement,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject obj = new JSONObject(response);

                            JSONArray dataArray = obj.getJSONArray("MonthlyReportDetails");


                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MonthWiseStatementActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No Month Wise Statement found for the selected Bill/Party")
                                        .setCancelable(false)
                                        .setTitle("Month Wise Statement")
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

                                MonthlyReportDetails reportDetails = new MonthlyReportDetails();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                reportDetails.setCmpShortName(
                                        dataobj.getString("CmpShortName")
                                );
                                reportDetails.setLedgerName(
                                        dataobj.getString("LedgerName")
                                );
                                reportDetails.setMonth(
                                         dataobj.getString("month")
                                );

                                JSONObject objectOpening = dataobj.getJSONObject("opening");
                                reportDetails.setOpening(
                                       new Opening(
                                               Double.parseDouble(
                                                       mTools.toDouble(
                                                               objectOpening.getString("ncr")
                                                       )
                                               ),
                                               Double.parseDouble(
                                                       mTools.toDouble(
                                                               objectOpening.getString("rpl")
                                                       )
                                               )
                                       )
                                );

                                JSONObject objectDebt = dataobj.getJSONObject("debt");
                                reportDetails.setDebt(
                                        new Debt(
                                                Double.parseDouble(
                                                        mTools.toDouble(
                                                                objectDebt.getString("ncr")
                                                        )
                                                ),
                                                Double.parseDouble(
                                                        mTools.toDouble(
                                                                objectDebt.getString("rpl")
                                                        )
                                                )
                                        )
                               );

                                JSONObject objectCredit = dataobj.getJSONObject("credit");
                               reportDetails.setCredit(
                                        new Credit(
                                                Double.parseDouble(
                                                        mTools.toDouble(
                                                                objectCredit.getString("ncr")
                                                        )
                                                ),
                                                Double.parseDouble(
                                                        mTools.toDouble(
                                                                objectCredit.getString("rpl")
                                                        )
                                                )
                                        )
                               );

                                JSONObject objectClosing = dataobj.getJSONObject("closing");

                                reportDetails.setClosing(
                                       new Closing(
                                               Double.parseDouble(
                                                       mTools.toDouble(
                                                               objectClosing.getString("ncr")
                                                       )
                                               ),
                                               Double.parseDouble(
                                                       mTools.toDouble(
                                                               objectClosing.getString("rpl")
                                                       )
                                               )
                                       )
                               );

                                arraylist.add(reportDetails);
                            }
                            getTotalData(arraylist);
                            linearTotal.setVisibility(View.VISIBLE);
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

                        Toast.makeText(
                                getApplicationContext(),
                                volleyErrors.exceptionMessage(error).toString(),
                                Toast.LENGTH_SHORT).show();

                        Hhdprogress.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("CmpShortName", companydata.getCmpShortName());
                params.put("LedgerName", companydata.getKeyPartyName());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getTotalData(ArrayList<MonthlyReportDetails> arraylist) {

        ThreadManager.getInstance(this).executeTask(new Runnable() {
            @Override
            public void run() {

                JSONObject  jsonObject = new JSONObject();
                double totalOpening = 0.0,openingTotalNcr = 0.0,openingTotalRpl = 0.0;
                double totalDebt = 0.0,debtTotalNcr = 0.0,debtTotalRpl = 0.0;
                double totalCredit = 0.0,creditTotalNcr = 0.0,creditTotalRpl = 0.0;
                double totalClosing = 0.0,closingTotalNcr = 0.0,closingTotalRpl = 0.0;

                for (MonthlyReportDetails reportDetails: arraylist){
                     totalOpening = (reportDetails.getOpening().getNcr() + reportDetails.getOpening().getRpl()) + totalOpening;
                     openingTotalNcr = openingTotalNcr + reportDetails.getOpening().getNcr();
                     openingTotalRpl = openingTotalRpl + reportDetails.getOpening().getRpl();


                     totalDebt = (reportDetails.getDebt().getNcr() + reportDetails.getDebt().getRpl()) + totalDebt;
                     debtTotalNcr = debtTotalNcr + reportDetails.getDebt().getNcr();
                     debtTotalRpl = debtTotalRpl + reportDetails.getDebt().getRpl();

                     totalCredit = (reportDetails.getCredit().getNcr() + reportDetails.getCredit().getRpl()) + totalCredit;
                     creditTotalNcr = creditTotalNcr + reportDetails.getCredit().getNcr();
                     creditTotalRpl = creditTotalRpl + reportDetails.getCredit().getRpl();

                     totalClosing = (reportDetails.getClosing().getNcr() + reportDetails.getClosing().getRpl()) + totalClosing;
                     closingTotalNcr = closingTotalNcr + reportDetails.getClosing().getNcr();
                     closingTotalRpl = closingTotalRpl + reportDetails.getClosing().getRpl();
                }

                try {
                    jsonObject.put("totalOpening",String.valueOf(totalOpening));
                    jsonObject.put("openingTotalNcr",String.valueOf(openingTotalNcr));
                    jsonObject.put("openingTotalRpl",String.valueOf(openingTotalRpl));

                    jsonObject.put("totalDebt",String.valueOf(totalDebt));
                    jsonObject.put("debtTotalNcr",String.valueOf(debtTotalNcr));
                    jsonObject.put("debtTotalRpl",String.valueOf(debtTotalRpl));

                    jsonObject.put("totalCredit",String.valueOf(totalCredit));
                    jsonObject.put("creditTotalNcr",String.valueOf(creditTotalNcr));
                    jsonObject.put("creditTotalRpl",String.valueOf(creditTotalRpl));

                    jsonObject.put("totalClosing",String.valueOf(totalClosing));
                    jsonObject.put("closingTotalNcr",String.valueOf(closingTotalNcr));
                    jsonObject.put("closingTotalRpl",String.valueOf(closingTotalRpl));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {


                               if (jsonObject.length() != 0){

                                   System.out.println("Datas Total");
                                   System.out.println(jsonObject.toString());

                                   TextView totalOpening = findViewById(R.id.totalOpeningT);
                                   totalOpening.setText(jsonObject.getString("totalOpening").toString());
                                   TextView rplOpening = findViewById(R.id.rplOpeningT);
                                   rplOpening.setText(jsonObject.getString("openingTotalRpl").toString());
                                   TextView ncrOpening = findViewById(R.id.ncrOpeningT);
                                   ncrOpening.setText(jsonObject.getString("openingTotalNcr").toString());


                                   TextView totalDebt = findViewById(R.id.totalDebtT);
                                   totalDebt.setText(jsonObject.getString("totalDebt").toString());
                                   TextView rplDebt = findViewById(R.id.rplDebtT);
                                   rplDebt.setText(jsonObject.getString("debtTotalRpl").toString());
                                   TextView ncrDebt = findViewById(R.id.ncrDebtT);
                                   ncrDebt.setText(jsonObject.getString("debtTotalNcr").toString());


                                   TextView totalCredit = findViewById(R.id.totalCreditT);
                                   totalCredit.setText(jsonObject.getString("totalCredit").toString());
                                   TextView rplCredit = findViewById(R.id.rplCreditT);
                                   rplCredit.setText(jsonObject.getString("creditTotalRpl").toString());
                                   TextView ncrCredit = findViewById(R.id.ncrCreditT);
                                   ncrCredit.setText(jsonObject.getString("creditTotalNcr").toString());


                                   TextView totalClosing = findViewById(R.id.totalClosingT);
                                   totalClosing.setText(jsonObject.getString("totalClosing").toString());
                                   TextView rplClosing = findViewById(R.id.rplClosingT);
                                   rplClosing.setText(jsonObject.getString("closingTotalRpl").toString());
                                   TextView ncrClosing = findViewById(R.id.ncrClosingT);
                                   ncrClosing.setText(jsonObject.getString("closingTotalNcr").toString());

                               }
                           }catch (JSONException e){
                               e.printStackTrace();
                           }
                        }
                    });

                } catch (JSONException e) {
                       e.printStackTrace();
                }

            }
        });
    }

    private void setupRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recyclerMonthWiseState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(monthWiseStatementAdapter);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}