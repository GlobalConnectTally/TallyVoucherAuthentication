package tallyadmin.gp.gpcropcare.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import static tallyadmin.gp.gpcropcare.Common.Common.URL_DEMOREGISTER;


public class DemoregisterActivity extends AppCompatActivity {
     TextView txtcmpn,txtperson,txtcount,textmail,textcontact;
     Button submit;
     UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demoregister);

        Toolbar toolbar = findViewById(R.id.toolbarSoop);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration Details");

        userInfo = new UserInfo(getApplicationContext());

        txtcmpn = findViewById(R.id.text_cmpname);
        txtperson = findViewById(R.id.edt_contactp);
        txtcount = findViewById(R.id.edt_country);
        textmail = findViewById(R.id.edt_maild);
        textcontact = findViewById(R.id.edt_contactno);

        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDemo();
            }
        });

    }

    private void registerDemo() {
        final String compan_name = txtcmpn.getText().toString();
        final String person = txtperson.getText().toString();
        final String country = txtcount.getText().toString();
        final String contact = textcontact.getText().toString();
        final String email = textmail.getText().toString();


        if (TextUtils.isEmpty(compan_name)) {
            txtcmpn.setError("Please enter your company name");
            txtcmpn.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(person)) {
            txtperson.setError("Please person name");
            txtperson.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(country)) {
            txtcount.setError("Please enter country name");
            txtcount.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contact)) {
            textcontact.setError("Please enter contact No");
            textcontact.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            textmail.setError("Please enter you Email Id");
            textmail.requestFocus();
            return;
        }

        final KProgressHUD Hhdprogress = KProgressHUD.create(DemoregisterActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DEMOREGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            Hhdprogress.dismiss();
                            JSONObject obj = new JSONObject(response);
                            int status = obj.getInt("status");
                            String message = obj.getString("msg");
                       if (status == 101) {
                                Toast.makeText(DemoregisterActivity.this, "Demo inserted successfully "+message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DemoregisterActivity.this, LoginActivity.class));
                                finish();

                            } else if (status==111){
                                Toast.makeText(DemoregisterActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DemoregisterActivity.this, LoginActivity.class));
                                finish();

                            } else {
                                Toast.makeText(DemoregisterActivity.this, "" + message, Toast.LENGTH_LONG).show();
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
                params.put("CompanyName", compan_name);
                params.put("ContactPerson", person);
                params.put("Country", country);
                params.put("ContactNo", contact);
                params.put("EmailId", email);
                params.put("IMEI", userInfo.getImei());

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
