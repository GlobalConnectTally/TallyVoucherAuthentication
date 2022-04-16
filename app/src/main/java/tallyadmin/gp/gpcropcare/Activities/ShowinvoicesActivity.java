package tallyadmin.gp.gpcropcare.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import tallyadmin.gp.gpcropcare.R;

public class ShowinvoicesActivity extends AppCompatActivity {

    RecyclerView recyclerOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinvoices);
        Toolbar toolbar = findViewById(R.id.toolbarSH);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);


    }
}
