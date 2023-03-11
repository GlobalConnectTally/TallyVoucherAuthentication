package tallyadmin.gp.gpcropcare.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import tallyadmin.gp.gpcropcare.R;

//-----six reports activity-----//
public class Sixreportactivity extends AppCompatActivity {
    LinearLayout outstanding,ncrduebill,businessdate,lastyearbus,currentstatus,limit,month_wise_statement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixreportactivity);

        outstanding = (LinearLayout)findViewById(R.id.outstanding);
        ncrduebill = (LinearLayout)findViewById(R.id.ncrduebill);
        businessdate = (LinearLayout)findViewById(R.id.businessdate);
        lastyearbus = (LinearLayout)findViewById(R.id.lastyearbus);
        currentstatus = (LinearLayout)findViewById(R.id.currentstatus);
        limit = (LinearLayout)findViewById(R.id.limit);
        month_wise_statement = (LinearLayout)findViewById(R.id.month_wise_statement);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Reports");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(Sixreportactivity.this, SalesActivity.class);
                startActivity(i);
            }
        });

        //-----When click on outstanding button------//
        outstanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sixreportactivity.this,Outstanding.class);
                startActivity(intent);
            }
        });

        //-----when click on ncrdue bills button-----//
        ncrduebill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sixreportactivity.this,NCRDueBills.class);
                startActivity(intent);
            }
        });


        //-----when click on business date -----//
        businessdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sixreportactivity.this,BusinessdateActivity.class);
                startActivity(intent);
            }
        });

        //-----when click on business date -----//
        lastyearbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sixreportactivity.this,LastYearbusinessActivity.class);
                startActivity(intent);
            }
        });

        //-----when click on business date -----//
        currentstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sixreportactivity.this,CurrentStatusActivity.class);
                startActivity(intent);
            }
        });

        limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sixreportactivity.this,LimitActivity.class);
                startActivity(intent);
            }
        });

        month_wise_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sixreportactivity.this,MonthWiseStatementActivity.class);
                startActivity(intent);
            }
        });
    }
}