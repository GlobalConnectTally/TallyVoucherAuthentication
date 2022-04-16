package tallyadmin.gp.gpcropcare.Activities;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;


public class LicenceActivity extends AppCompatActivity {


    UserInfo userInfo;
    Button buttondemo,buttoactivate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);
        userInfo = new UserInfo(getApplicationContext());


        buttondemo = findViewById(R.id.btn_demo);
        buttondemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LicenceActivity.this,DemoregisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        buttoactivate = findViewById(R.id.active_licence);
        buttoactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LicenceActivity.this,ActivateLicenceActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }


}
