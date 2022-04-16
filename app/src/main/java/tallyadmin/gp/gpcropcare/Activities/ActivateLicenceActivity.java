package tallyadmin.gp.gpcropcare.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import tallyadmin.gp.gpcropcare.LoginActivity;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_ACTIVATE;


public class ActivateLicenceActivity extends AppCompatActivity {
    TextView mailid,txtserial,txtactivation;
    private RadioGroup radioGroup;
    String status;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_licence);

        Toolbar toolbar = findViewById(R.id.toolbarSoopA);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Licence");
        userInfo = new UserInfo(getApplicationContext());
        radioGroup= findViewById(R.id.radiobutton);
        mailid = findViewById(R.id.text_regmaild);
        txtserial = findViewById(R.id.edt_serial);
        txtactivation = findViewById(R.id.edt_actkey);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.surrender:
                        status = "surrender";
                        break;
                    case R.id.activate:
                        status = "Active";
                        break;

                }
            }
        });



        Button cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ActivateLicenceActivity.this,LicenceActivity.class));
               finish();
            }
        });

        Button buttoactivate = findViewById(R.id.btn_done);
        buttoactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDemo();
            }

        });
    }

    private void registerDemo() {
        final String serial = txtserial.getText().toString();
        final String actkey = txtactivation.getText().toString();
        final String email = mailid.getText().toString();


        if (TextUtils.isEmpty(serial)) {
            txtserial.setError("Please enter your serial number");
            txtserial.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(actkey)) {
            txtactivation.setError("Please person name");
            txtactivation.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            mailid.setError("Please enter country name");
            mailid.requestFocus();
            return;
        }


        final KProgressHUD Hhdprogress = KProgressHUD.create(ActivateLicenceActivity.this)
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ACTIVATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            Hhdprogress.dismiss();
                            JSONObject obj = new JSONObject(response);
                            String message =obj.getString("msg");
                            Toast.makeText(ActivateLicenceActivity.this,message,Toast.LENGTH_SHORT).show();
                            if (message.equals("Already Active")){
                                startActivity(new Intent(ActivateLicenceActivity.this, ActivateLicenceActivity.class));
                                finish();
                            }
                            else if (message.equals("Successfully Active")){
                                startActivity(new Intent(ActivateLicenceActivity.this, LoginActivity.class));
                                finish();
                            } else if (message.equals("Successfully Deactive")){
                                startActivity(new Intent(ActivateLicenceActivity.this, ActivateLicenceActivity.class));
                                finish();
                            }
                            else if (message.equals("Already Deactive")){
                                startActivity(new Intent(ActivateLicenceActivity.this, ActivateLicenceActivity.class));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RegMailId",email);
                params.put("AppSerialNumber",serial);
                params.put("AppAuthenticationKey", actkey);
                params.put("AppCurrentStatus",status);
                params.put("IMEI1",userInfo.getImei());
                params.put("IMEI2","");
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
