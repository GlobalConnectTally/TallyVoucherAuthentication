package tallyadmin.gp.gpcropcare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import tallyadmin.gp.gpcropcare.Adapter.CompanyAdapter;
import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.Session;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_LOGIN;


public class LoginActivity extends AppCompatActivity {
    Button btn;
    EditText txt_username,txt_password;
    ProgressDialog mProgressDialog;
    Companysave companydata;
    UserInfo userInfo;
    Session session;
    ArrayList<Company> Saleslist;
    Company company;
    AlertDialog alertDialog;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressDialog =  new ProgressDialog(this);
        companydata = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());
        session = new Session(getApplicationContext());
        context = this;

        if (!isNetworkConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Set the Alert Dialog Message
            builder.setMessage("Please make sure you are connected to a stable network and try again")
                    .setCancelable(false)
                    .setTitle("Alert")
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // Restart the Activity
                                    Intent intent = getIntent();
                                    startActivity(intent);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();


        }

        if (session.isLoggedIn()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        txt_username = findViewById(R.id.username);
        txt_password = findViewById(R.id.edt_password);

        btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
                    //                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                finish();
            }
        });

    }


    private void userLogin() {
        final String username = txt_username.getText().toString();
        final String password = txt_password.getText().toString();

        if (TextUtils.isEmpty(username)) {
            txt_username.setError("Please enter your username");
            txt_username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            txt_password.setError("Please enter your password");
            txt_password.requestFocus();
            return;
        }

        final KProgressHUD Hhdprogress = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);


        Hhdprogress.setCancellable(new  DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                finish();
            }
        });

        Hhdprogress.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                Saleslist = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("response");

                                Log.d("Response::" , dataArray.toString());

                                if (dataArray.length() != 0){
                                    Hhdprogress.dismiss();
                                    session.setLogin(true);
                                    userInfo.setAppLoginUserID(username);
                                    userInfo.setpassword(password);

                                    for (int i = 0; i < dataArray.length(); i++) {
                                        Company playerModel = new Company();
                                        JSONObject dataobj = dataArray.getJSONObject(i);


                                        playerModel.setAllowReject(dataobj.getString("AllowReject"));
                                        playerModel.setAllowedApprove(dataobj.getString("AllowApprove"));


                                        playerModel.setCmpGUID(dataobj.getString("CmpGUID"));
                                        playerModel.setCompanyName(dataobj.getString("CompanyName"));
                                        Saleslist.add(playerModel);
                                    }
                                    cmpndialog();
                                }
                                else if (dataArray.length() == 0){
                                    Hhdprogress.dismiss();
                                    session.setLogin(false);
                                   // Toast.makeText(LoginActivity.this,"Login Failed please check you credential's", Toast.LENGTH_LONG).show();
                                }

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
                params.put("AppLoginUserID", username);
                params.put("AppLoginPassword", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    private  void cmpndialog(){
        AlertDialog.Builder settingdialog = new AlertDialog.Builder(this,R.style.MyDialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View settinview = inflater.inflate(R.layout.setting_layoutrc, null);
        final CompanyAdapter companyAdapter= new CompanyAdapter(Saleslist,getApplicationContext(),LoginActivity.this);
        RecyclerView recyclercpm = settinview.findViewById(R.id.recyler_bottomm);
        recyclercpm.setAdapter(companyAdapter);
        recyclercpm.setHasFixedSize(true);
        recyclercpm.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        settingdialog.setView(settinview);
        alertDialog = settingdialog.create();

        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,900); //Controlling width and height.

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null){
            alertDialog.dismiss();
        }

    }
}
