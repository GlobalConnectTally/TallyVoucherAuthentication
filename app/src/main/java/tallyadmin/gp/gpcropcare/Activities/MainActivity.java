package tallyadmin.gp.gpcropcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nex3z.notificationbadge.NotificationBadge;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import tallyadmin.gp.gpcropcare.AboutActivity;
import tallyadmin.gp.gpcropcare.LoginActivity;
import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.Session;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_DASHBOARDSBADGES;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_LOGIN;


//-----mainactivity-------//
public class MainActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    Companysave companydata;
    UserInfo userInfo;
    Session session;
    ArrayList<Company> Saleslist;
    private static ProgressDialog mProgressDialog;
    NotificationBadge mBage,paybadge,orderbadge;
    SwipeRefreshLayout swipe;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

        companydata = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());
        session = new Session(getApplicationContext());

        mProgressDialog =  new ProgressDialog(MainActivity.this);
        swipe = findViewById(R.id.swipe_torefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                showbadges();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        if(swipe.isRefreshing())
                        {
                            swipe.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        mBage = findViewById(R.id.salesbadge);
//            paybadge = findViewById(R.id.paymentrderbadge);
//            orderbadge = findViewById(R.id.salesorderbadge);
        showbadges();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
//             Handle navigation view item clicks here.
                    int id = item.getItemId();

                    if (id == R.id.nav_home)
                    {
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        finish();
                    }
                    else if (id == R.id.nav_about)
                    {
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    }
                    else if (id == R.id.action_setting)
                    {
                        companyseting();
                    }
                    else if (id == R.id.nav_logout)
                    {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    session.setLogin(false);
                    userInfo.clearUserInfo();
                    companydata.clearCompany();
                    finish();
                    }
                return false;
            }
        });

        LinearLayout about = findViewById(R.id.about);
        ImageView imgabout = findViewById(R.id.imgabout);
        imgabout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
            }
        });

        LinearLayout help = findViewById(R.id.help);
        ImageView imghelp = findViewById(R.id.imghelp);
        imghelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                companyseting();
            }
        });

        LinearLayout logout = findViewById(R.id.logout);
        ImageView imglogout = findViewById(R.id.imglogout);
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                session.setLogin(false);
                userInfo.clearUserInfo();
                companydata.clearCompany();
            }
        });

        LinearLayout salesorder = findViewById(R.id.sales_order);
        ImageView imgs = findViewById(R.id.imgs);
        imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SalesOrderActivity.class));

            }
        });

        salesorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SalesOrderActivity.class));

            }
        });

        TextView dashcmp = findViewById(R.id.dash_cmpname);
        dashcmp.setText(companydata.getKeyName());
    }

    public void showbadges() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DASHBOARDSBADGES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);

                            //badges number

                            int PendingSales = obj.getInt("PendingSales");
//                            int pendingorders = obj.getInt("PendingSalesOrder");
//                            int pendingpay = obj.getInt("PendingPayment");

                            if (PendingSales !=0){
//                                Toast.makeText(HomeActivity.this, ""+PendingSales,Toast.LENGTH_SHORT).show();
                                mBage.setNumber(PendingSales);
                            }
//                           if (pendingorders!=0){
//                               orderbadge.setNumber(pendingorders);
////                               Toast.makeText(HomeActivity.this, ""+pendingorders,Toast.LENGTH_SHORT).show();
//                           }

//                            if (pendingpay!=0){
//                                paybadge.setNumber(pendingpay);
////                                Toast.makeText(HomeActivity.this, ""+pendingpay,Toast.LENGTH_SHORT).show();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("CmpGUID",companydata.getKeyCmpnGid());
                return params;
            }
        };

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

    private void companyseting() {
        final KProgressHUD Hhdprogress = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
//        Hhdprogress.setCancellable()
        Hhdprogress.setCancellable(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                finish();
            }
        });

        Hhdprogress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            Saleslist = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("response");
                            for (int i = 0; i < dataArray.length(); i++) {
                                Company playerModel = new Company();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                playerModel.setCmpGUID(dataobj.getString("CmpGUID"));
                                playerModel.setCompanyName(dataobj.getString("CompanyName"));
                                Saleslist.add(playerModel);
                            }
                            Hhdprogress.dismiss();
                            cmpndialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() == null ? "" : error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("AppLoginPassword", userInfo.getPassWord());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    //check internet connectivity.....
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private  void cmpndialog(){
        AlertDialog.Builder settingdialog = new AlertDialog.Builder(this,R.style.MyDialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View settinview= inflater.inflate(R.layout.setting_layoutrc, null);
//        final CompanyHomeAdapter companyAdapter= new CompanyHomeAdapter(Saleslist,getApplicationContext(),MainActivity.this);
//        RecyclerView recyclercpm = settinview.findViewById(R.id.recyler_bottomm);
//        recyclercpm.setAdapter(companyAdapter);
//        recyclercpm.setHasFixedSize(true);
//        recyclercpm.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        settingdialog.setView(settinview);

        alertDialog = settingdialog.create();

        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,900); //Controlling width and height.

    }

    public void cmpDialogExit(){
        alertDialog.dismiss();
    }


    @Override
    public void onBackPressed() {
        showbadges();

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        session.setLogin(false);
                        userInfo.clearUserInfo();
                        companydata.clearCompany();
                        Intent i2 = new Intent(MainActivity.this, LoginActivity.class);
                        i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i2);
                        finish();
                    }
                }).show();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        // do some stuff here
        showbadges();
    }







}

