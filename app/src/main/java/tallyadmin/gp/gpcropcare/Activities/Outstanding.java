package tallyadmin.gp.gpcropcare.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map;

import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORTNrc;



public class Outstanding extends AppCompatActivity {
    Companysave companydata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outstanding);

        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Outstanding");

        companydata = new Companysave(getApplicationContext());
        TextView partyname = findViewById(R.id.outs_partyname);
        partyname.setText(companydata.getKeyPartyName());
        TextView billvalue = findViewById(R.id.outs_billvalue);
        billvalue.setText(companydata.getKeyBillAmount());
        TextView reportdate = findViewById(R.id.outs_reportdate);
        reportdate.setText(companydata.getKeyVocherdate());


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
        } else if (isNetworkConnected()) {
            fetchingJSON();
        }
    }


    public void fetchingJSON() {

        //String urlno = "?CmpGUID=" + companydata.getKeyCmpnGid() + "&LedgerName="+companydata.getKeyPartyName()+ "&LedgerID="+companydata.getKeyLegId()+ "&VchNumber=" + companydata.getVoucher();
        //System.out.println("url:"+urlno);
        String urlno = "";
       // http://gatewayinfosoft.com/api/GPCropCareSendReport/?CmpGUID=2d807426-3dff-4a12-8fa9-571a55bdb98f-Gujarat-TN&LedgerName=Parshad Joshi App&LedgerID=342&VchNumber=Demo291

        final KProgressHUD Hhdprogress = KProgressHUD.create(Outstanding.this)
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
                            System.out.println("response:"+obj);


                            JSONArray dataArray = obj.getJSONArray("ReportOutstanding");

                            System.out.println("jison"+dataArray);


                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Outstanding.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No outstanding found for the selected Bill/Party")
                                        .setCancelable(false)
                                        .setTitle("Outstanding Report")
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

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                TextView pgrl = findViewById(R.id.pgro);
                                BigDecimal ototal = new BigDecimal(dataobj.getString("PGR0"));
                                DecimalFormat formatter = new DecimalFormat("##,##,###.###");
                                String pgr1 = formatter.format(ototal);
                                pgrl.setText(pgr1);

                                TextView pgrU = findViewById(R.id.pgr100);
                                BigDecimal pgruq = new BigDecimal(dataobj.getString("PGR100"));
                                DecimalFormat formatter1 = new DecimalFormat("##,##,###.###");
                                String pgrua = formatter1.format(pgruq);
                                pgrU.setText(pgrua);

                                TextView pgra = findViewById(R.id.pgr115);
                                BigDecimal pgrasd = new BigDecimal(dataobj.getString("PGR115"));
                                DecimalFormat formatter2 = new DecimalFormat("##,##,###.###");
                                String pgra1 = formatter2.format(pgrasd);
                                pgra.setText(pgra1);

                                TextView pgr130 = findViewById(R.id.pgr130);
                                BigDecimal pgra130 = new BigDecimal(dataobj.getString("PGR130"));
                                DecimalFormat formatter3 = new DecimalFormat("##,##,###.###");
                                String pgrs130 = formatter3.format(pgra130);
                                pgr130.setText(pgrs130);

                                TextView pgr150 = findViewById(R.id.pgr150);
                                BigDecimal pgras150 = new BigDecimal(dataobj.getString("PGR150"));
                                DecimalFormat formatter245 = new DecimalFormat("##,##,###.###");
                                String pgra150 = formatter245.format(pgras150);
                                pgr150.setText(pgra150);

                                TextView pgr180 = findViewById(R.id.pgr180);
                                BigDecimal pgras180 = new BigDecimal(dataobj.getString("PGR180"));
                                DecimalFormat formatter24 = new DecimalFormat("##,##,###.###");
                                String pgra1180 = formatter24.format(pgras180);
                                pgr180.setText(pgra1180);

                                TextView pgr210 = findViewById(R.id.pgr210);
                                BigDecimal pg210 = new BigDecimal(dataobj.getString("PGR210"));
                                DecimalFormat formatter251 = new DecimalFormat("##,##,###.###");
                                String pgrw210 = formatter251.format(pg210);
                                pgr210.setText(pgrw210);

                                TextView pgr250 = findViewById(R.id.pgr250);
                                BigDecimal pg250 = new BigDecimal(dataobj.getString("PGR250"));
                                DecimalFormat formatter25 = new DecimalFormat("##,##,###.###");
                                String pgrw250 = formatter25.format(pg250);
                                pgr250.setText(pgrw250);

                                TextView pgr365 = findViewById(R.id.pgr365);
                                BigDecimal pgrasd365 = new BigDecimal(dataobj.getString("PGR365"));
                                DecimalFormat formatter26 = new DecimalFormat("##,##,###.###");
                                String pgra1365 = formatter26.format(pgrasd365);
                                pgr365.setText(pgra1365);

                                TextView pgrttl = findViewById(R.id.pgrttl);
                                BigDecimal pgratotal = new BigDecimal(dataobj.getString("PGRTotal"));
                                DecimalFormat formatter12 = new DecimalFormat("##,##,###.###");
                                String pgrtotal1 = formatter12.format(pgratotal);
                                pgrttl.setText(pgrtotal1);

                                /*-----------------------            END        ------------------------*/

                                TextView psd0 = findViewById(R.id.psd0);
                                BigDecimal psda0 = new BigDecimal(dataobj.getString("Pesticides0"));
                                DecimalFormat formatter121 = new DecimalFormat("##,##,###.###");
                                String ps0 = formatter121.format(psda0);
                                psd0.setText(ps0);

                                TextView psd100 = findViewById(R.id.psd100);
                                BigDecimal psda100 = new BigDecimal(dataobj.getString("Pesticides100"));
                                DecimalFormat formatter74 = new DecimalFormat("##,##,###.###");
                                String ps100 = formatter74.format(psda100);
                                psd100.setText(ps100);

                                TextView psd115 = findViewById(R.id.psdd115);
                                BigDecimal psda115 = new BigDecimal(dataobj.getString("Pesticides115"));
                                DecimalFormat formatter1021 = new DecimalFormat("##,##,###.###");
                                String ps115 = formatter1021.format(psda115);
                                psd115.setText(ps115);

                                TextView psd130 = findViewById(R.id.psd130);
                                BigDecimal psda130 = new BigDecimal(dataobj.getString("Pesticides130"));
                                DecimalFormat formatter125 = new DecimalFormat("##,##,###.###");
                                String ps130 = formatter125.format(psda130);
                                psd130.setText(ps130);

                                TextView psd150 = findViewById(R.id.psd150);
                                BigDecimal psda150 = new BigDecimal(dataobj.getString("Pesticides150"));
                                DecimalFormat formatter1205 = new DecimalFormat("##,##,###.###");
                                String ps150 = formatter1205.format(psda150);
                                psd150.setText(ps150);

                                TextView psd180 = findViewById(R.id.psd180);
                                BigDecimal psda180 = new BigDecimal(dataobj.getString("Pesticides180"));
                                DecimalFormat formatter123 = new DecimalFormat("##,##,###.###");
                                String ps180 = formatter123.format(psda180);
                                psd180.setText(ps180);

                                TextView psd210 = findViewById(R.id.psd210);
                                BigDecimal psda210 = new BigDecimal(dataobj.getString("Pesticides210"));
                                DecimalFormat formatter28 = new DecimalFormat("##,##,###.###");
                                String ps210 = formatter28.format(psda210);
                                psd210.setText(ps210);

                                TextView psd250 = findViewById(R.id.psd250);
                                BigDecimal psda250 = new BigDecimal(dataobj.getString("Pesticides250"));
                                DecimalFormat formatter128 = new DecimalFormat("##,##,###.###");
                                String ps250 = formatter128.format(psda250);
                                psd250.setText(ps250);

                                TextView psd365 = findViewById(R.id.psd365);
                                BigDecimal psda365 = new BigDecimal(dataobj.getString("Pesticides365"));
                                DecimalFormat formatter1274 = new DecimalFormat("##,##,###.###");
                                String ps365 = formatter1274.format(psda365);
                                psd365.setText(ps365);

                                TextView psdttl = findViewById(R.id.psdttl);
                                BigDecimal psdatotal = new BigDecimal(dataobj.getString("PesticidesTotal"));
                                DecimalFormat formatter129 = new DecimalFormat("##,##,###.###");
                                String pstotal = formatter129.format(psdatotal);
                                psdttl.setText(pstotal);

                                /*-----------------------            END        ------------------------*/

                                TextView ryl0 = findViewById(R.id.ryl0);
                                BigDecimal rya0 = new BigDecimal(dataobj.getString("Royal0"));
                                DecimalFormat formatter10 = new DecimalFormat("##,##,###.###");
                                String ry0 = formatter10.format(rya0);
                                ryl0.setText(ry0);

                                TextView ryl100 = findViewById(R.id.ryl100);
                                BigDecimal rya100 = new BigDecimal(dataobj.getString("Royal100"));
                                DecimalFormat formatter100 = new DecimalFormat("##,##,###.###");
                                String ry100 = formatter100.format(rya100);
                                ryl100.setText(ry100);

                                TextView ryl115 = findViewById(R.id.ryl115);
                                BigDecimal rya115 = new BigDecimal(dataobj.getString("Royal115"));
                                DecimalFormat formatter115 = new DecimalFormat("##,##,###.###");
                                String ry115 = formatter115.format(rya115);
                                ryl115.setText(ry115);

                                TextView ryl130 = findViewById(R.id.ryl130);
                                BigDecimal rya130 = new BigDecimal(dataobj.getString("Royal130"));
                                DecimalFormat formatter130 = new DecimalFormat("##,##,###.###");
                                String ry130 = formatter130.format(rya130);
                                ryl130.setText(ry130);

                                TextView ryl150 = findViewById(R.id.ryl150);
                                BigDecimal rya150 = new BigDecimal(dataobj.getString("Royal150"));
                                DecimalFormat formatter150 = new DecimalFormat("##,##,###.###");
                                String ry150 = formatter150.format(rya150);
                                ryl150.setText(ry150);

                                TextView ryl180 = findViewById(R.id.ryl180);
                                BigDecimal rya180 = new BigDecimal(dataobj.getString("Royal180"));
                                DecimalFormat formatter180 = new DecimalFormat("##,##,###.###");
                                String ry180 = formatter180.format(rya180);
                                ryl180.setText(ry180);

                                TextView ryl210 = findViewById(R.id.ryl210);
                                BigDecimal rya210 = new BigDecimal(dataobj.getString("Royal210"));
                                DecimalFormat formatter210 = new DecimalFormat("##,##,###.###");
                                String ry210 = formatter210.format(rya210);
                                ryl210.setText(ry210);

                                TextView ryl250 = findViewById(R.id.ryl250);
                                BigDecimal rya250 = new BigDecimal(dataobj.getString("Royal250"));
                                DecimalFormat formatter250 = new DecimalFormat("##,##,###.###");
                                String ry250 = formatter250.format(rya250);
                                ryl250.setText(ry250);

                                TextView ryl365 = findViewById(R.id.ryl365);
                                BigDecimal rya365 = new BigDecimal(dataobj.getString("Royal365"));
                                DecimalFormat formatter365 = new DecimalFormat("##,##,###.###");
                                String ry365 = formatter365.format(rya365);
                                ryl365.setText(ry365);

                                TextView rylttl = findViewById(R.id.ryltotal);
                                BigDecimal rya1totl = new BigDecimal(dataobj.getString("RoyalTotal"));
                                DecimalFormat formatter116 = new DecimalFormat("##,##,###.###");
                                String rytotl = formatter116.format(rya1totl);
                                rylttl.setText(rytotl);

                                /*-----------------------            END        ------------------------*/


                                TextView prm0 = findViewById(R.id.prm0);
                                BigDecimal prma0 = new BigDecimal(dataobj.getString("Primium0"));
                                DecimalFormat formatter0 = new DecimalFormat("##,##,###.###");
                                String pr0 = formatter0.format(prma0);
                                prm0.setText(pr0);

                                TextView prm100 = findViewById(R.id.prm100);
                                BigDecimal prma100 = new BigDecimal(dataobj.getString("Primium100"));
                                DecimalFormat formatter010 = new DecimalFormat("##,##,###.###");
                                String pr100 = formatter010.format(prma100);
                                prm100.setText(pr100);

                                TextView prm115 = findViewById(R.id.prm115);
                                BigDecimal prma115 = new BigDecimal(dataobj.getString("Primium115"));
                                DecimalFormat formatter110 = new DecimalFormat("##,##,###.###");
                                String pr115 = formatter110.format(prma115);
                                prm115.setText(pr115);

                                TextView prm130 = findViewById(R.id.prm130);
                                BigDecimal prma130 = new BigDecimal(dataobj.getString("Primium130"));
                                DecimalFormat formatter520 = new DecimalFormat("##,##,###.###");
                                String pr130 = formatter520.format(prma130);
                                prm130.setText(pr130);

                                TextView prm150 = findViewById(R.id.prm150);
                                BigDecimal prma150 = new BigDecimal(dataobj.getString("Primium150"));
                                DecimalFormat formatter1251 = new DecimalFormat("##,##,###.###");
                                String pr150 = formatter1251.format(prma150);
                                prm150.setText(pr150);

                                TextView prm180 = findViewById(R.id.prm180);
                                BigDecimal prma180 = new BigDecimal(dataobj.getString("Primium180"));
                                DecimalFormat formatter1250 = new DecimalFormat("##,##,###.###");
                                String pr180 = formatter1250.format(prma180);
                                prm180.setText(pr180);

                                TextView prm210 = findViewById(R.id.prm210);
                                BigDecimal prma210 = new BigDecimal(dataobj.getString("Primium210"));
                                DecimalFormat formatter20 = new DecimalFormat("##,##,###.###");
                                String pr210 = formatter20.format(prma210);
                                prm210.setText(pr210);

                                TextView prm250 = findViewById(R.id.prm250);
                                BigDecimal prma250 = new BigDecimal(dataobj.getString("Primium250"));
                                DecimalFormat formatter220 = new DecimalFormat("##,##,###.###");
                                String pr250 = formatter220.format(prma250);
                                prm250.setText(pr250);

                                TextView prm365 = findViewById(R.id.prm365);
                                BigDecimal prma365 = new BigDecimal(dataobj.getString("Primium365"));
                                DecimalFormat formatter360 = new DecimalFormat("##,##,###.###");
                                String pr365 = formatter360.format(prma365);
                                prm365.setText(pr365);

                                TextView prmttl = findViewById(R.id.prmtotal);
                                BigDecimal prmatotl = new BigDecimal(dataobj.getString("PrimiumTotal"));
                                DecimalFormat formatter50 = new DecimalFormat("##,##,###.###");
                                String prtotl = formatter50.format(prmatotl);
                                prmttl.setText(prtotl);

                                /*-----------------------            END        ------------------------*/


                                TextView rylcs0 = findViewById(R.id.clsc0);
                                BigDecimal clas0 = new BigDecimal(dataobj.getString("Classic0"));
                                DecimalFormat formatter510 = new DecimalFormat("##,##,###.###");
                                String cla0 = formatter510.format(clas0);
                                rylcs0.setText(cla0);

                                TextView ryl100cs = findViewById(R.id.clsc100);
                                BigDecimal clas100 = new BigDecimal(dataobj.getString("Classic100"));
                                DecimalFormat formatter620 = new DecimalFormat("##,##,###.###");
                                String cla100 = formatter620.format(clas100);
                                ryl100cs.setText(cla100);

                                TextView ryl115cs = findViewById(R.id.clsc115);
                                BigDecimal clas115 = new BigDecimal(dataobj.getString("Classic115"));
                                DecimalFormat formatter62 = new DecimalFormat("##,##,###.###");
                                String cla115 = formatter62.format(clas115);
                                ryl115cs.setText(cla115);

                                TextView ryl130cs = findViewById(R.id.clsc130);
                                BigDecimal clas130 = new BigDecimal(dataobj.getString("Classic130"));
                                DecimalFormat formatter63 = new DecimalFormat("##,##,###.###");
                                String cla130 = formatter63.format(clas130);
                                ryl130cs.setText(cla130);

                                TextView ryl150cs = findViewById(R.id.clsc150);
                                BigDecimal clas150 = new BigDecimal(dataobj.getString("Classic150"));
                                DecimalFormat formatter604 = new DecimalFormat("##,##,###.###");
                                String cla150 = formatter604.format(clas150);
                                ryl150cs.setText(cla150);

                                TextView ryl180cs = findViewById(R.id.clsc180);
                                BigDecimal clas180 = new BigDecimal(dataobj.getString("Classic180"));
                                DecimalFormat formatter64 = new DecimalFormat("##,##,###.###");
                                String cla180 = formatter64.format(clas180);
                                ryl180cs.setText(cla180);

                                TextView ryl210cs = findViewById(R.id.clsc210);
                                BigDecimal clas210 = new BigDecimal(dataobj.getString("Classic210"));
                                DecimalFormat formatter651 = new DecimalFormat("##,##,###.###");
                                String cla210 = formatter651.format(clas210);
                                ryl210cs.setText(cla210);

                                TextView ryl250cs = findViewById(R.id.clsc250);
                                BigDecimal clas250 = new BigDecimal(dataobj.getString("Classic250"));
                                DecimalFormat formatter65 = new DecimalFormat("##,##,###.###");
                                String cla250 = formatter65.format(clas250);
                                ryl250cs.setText(cla250);

                                TextView ryl365cs = findViewById(R.id.clsc365);
                                BigDecimal clas365 = new BigDecimal(dataobj.getString("Classic365"));
                                DecimalFormat formatter66 = new DecimalFormat("##,##,###.###");
                                String cla365 = formatter66.format(clas365);
                                ryl365cs.setText(cla365);

                                TextView rylttlcs = findViewById(R.id.clsctotal);
                                BigDecimal clastotal = new BigDecimal(dataobj.getString("ClassicTotal"));
                                DecimalFormat formatter67 = new DecimalFormat("##,##,###.###");
                                String clatotal = formatter67.format(clastotal);
                                rylttlcs.setText(clatotal);

                                /*-----------------------            END        ------------------------*/

                                TextView dst0 = findViewById(R.id.dst0);
                                BigDecimal dus0 = new BigDecimal(dataobj.getString("Dust0"));
                                DecimalFormat formatter70 = new DecimalFormat("##,##,###.###");
                                String dust0 = formatter70.format(dus0);
                                dst0.setText(dust0);

                                TextView dst100 = findViewById(R.id.dust100);
                                BigDecimal dus100 = new BigDecimal(dataobj.getString("Dust100"));
                                DecimalFormat formatter71 = new DecimalFormat("##,##,###.###");
                                String dust100 = formatter71.format(dus100);
                                dst100.setText(dust100);

                                TextView prmds115 = findViewById(R.id.dust115);
                                BigDecimal dus115 = new BigDecimal(dataobj.getString("Dust115"));
                                DecimalFormat formatter72 = new DecimalFormat("##,##,###.###");
                                String dust115 = formatter72.format(dus115);
                                prmds115.setText(dust115);

                                TextView prmds130 = findViewById(R.id.dust130);
                                BigDecimal dus130 = new BigDecimal(dataobj.getString("Dust130"));
                                DecimalFormat formatter73 = new DecimalFormat("##,##,###.###");
                                String dust130 = formatter73.format(dus130);
                                prmds130.setText(dust130);

                                TextView prmds150 = findViewById(R.id.dust150);
                                BigDecimal dus150 = new BigDecimal(dataobj.getString("Dust150"));
                                DecimalFormat formatter751 = new DecimalFormat("##,##,###.###");
                                String dust150 = formatter751.format(dus150);
                                prmds150.setText(dust150);

                                TextView prmds180 = findViewById(R.id.dust180);
                                BigDecimal dus180 = new BigDecimal(dataobj.getString("Dust180"));
                                DecimalFormat formatter75 = new DecimalFormat("##,##,###.###");
                                String dust180 = formatter75.format(dus180);
                                prmds180.setText(dust180);

                                TextView prmds210 = findViewById(R.id.dust210);
                                BigDecimal dus210 = new BigDecimal(dataobj.getString("Dust210"));
                                DecimalFormat formatter726 = new DecimalFormat("##,##,###.###");
                                String dust210 = formatter726.format(dus210);
                                prmds210.setText(dust210);

                                TextView prmds250 = findViewById(R.id.dust250);
                                BigDecimal dus250 = new BigDecimal(dataobj.getString("Dust250"));
                                DecimalFormat formatter76 = new DecimalFormat("##,##,###.###");
                                String dust250 = formatter76.format(dus250);
                                prmds250.setText(dust250);

                                TextView prmds365 = findViewById(R.id.dust365);
                                BigDecimal dus365 = new BigDecimal(dataobj.getString("Dust365"));
                                DecimalFormat formatter77 = new DecimalFormat("##,##,###.###");
                                String dust365 = formatter77.format(dus365);
                                prmds365.setText(dust365);

                                TextView prmdsttl = findViewById(R.id.dusttl);
                                BigDecimal dustot = new BigDecimal(dataobj.getString("DustTotal"));
                                DecimalFormat formatter78 = new DecimalFormat("##,##,###.###");
                                String dusttotl = formatter78.format(dustot);
                                prmdsttl.setText(dusttotl);

                            }

                            Hhdprogress.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    }
