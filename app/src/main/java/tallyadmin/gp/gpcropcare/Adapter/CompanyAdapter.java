package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

import tallyadmin.gp.gpcropcare.HomeActivity;
import tallyadmin.gp.gpcropcare.Interface.ItemClickListener;
import tallyadmin.gp.gpcropcare.LoginActivity;
import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>
{
    private ArrayList<Company> companieslist;
    ArrayList<ArrayList<Company>> checkedTeachers = new ArrayList<ArrayList<Company>>();
    Context context;
    Companysave companydata;
    LoginActivity loginActivity;
    UserInfo userInfo;

    public ArrayList<Company> getCompanieslist() {
        return companieslist;
    }

    public CompanyAdapter(ArrayList<Company> companieslist, Context context, LoginActivity loginActivity)
    {
        this.companieslist = companieslist;
        this.context = context;
        this.loginActivity = loginActivity;
        this.userInfo = new UserInfo(context.getApplicationContext());
    }

    @NonNull
    @Override
    public CompanyAdapter.CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        final View itemView = LayoutInflater.from(context).inflate(R.layout.spinner_layoutb, parent, false);
        return new CompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompanyAdapter.CompanyViewHolder holder, final int position)
    {
        holder.companygname.setText(companieslist.get(holder.getAdapterPosition()).getCompanyName());
        if ( companieslist.get(holder.getAdapterPosition()).getPendingSales()!= null &&
                companieslist.get(holder.getAdapterPosition()).getPendingSales()!= 0){
            holder.mBage.setNumber(companieslist.get(holder.getAdapterPosition()).getPendingSales());
            holder.mBage.setVisibility(View.VISIBLE);
        }else {
            holder.mBage.setVisibility(View.GONE);
        }

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {

                /*
                companydata = new Companysave(context.getApplicationContext());
                companydata.setCompanyGid(companieslist.get(position).getCmpGUID());
                Toast.makeText(context,"1"+companieslist.get(position).getCmpGUID(), Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context,HomeActivity.class));
                */

                showConfirmDialog(companieslist.get(holder.getAdapterPosition()).getCmpGUID(),companieslist.get(holder.getAdapterPosition()).getCompanyName(),companieslist.get(holder.getAdapterPosition()).getCmpShortName());

                /*  -----     SET RULES HERE   -------------    */
                userInfo.setFirstLevel(companieslist.get(holder.getAdapterPosition()).getFirstLevel().toString());
                userInfo.setSecondLevel(companieslist.get(holder.getAdapterPosition()).getSecondLevel().toString());

                userInfo.setAllowApprove(companieslist.get(holder.getAdapterPosition()).getAllowedApprove().toString());
                userInfo.setAllowReject(companieslist.get(holder.getAdapterPosition()).getAllowReject().toString());

            }
        });

    }

    private void showConfirmDialog(final String cmpGUID, final String companyName, final String CmpShortName)
    {

        System.out.println("Short-Name - 1:: " + CmpShortName);

        companydata = new Companysave(context.getApplicationContext());
        companydata.setCompanyGid(cmpGUID);
        companydata.setcompany(companyName);
        companydata.setCmpShortName(CmpShortName);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        loginActivity.finish();

    }

    @Override
    public int getItemCount() {
        return companieslist.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ItemClickListener listener;

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        TextView companyguid,companygname;
        NotificationBadge mBage;

        public CompanyViewHolder(@NonNull View view) {
            super(view);

              companygname = view.findViewById(R.id.text_cmpn1);
              mBage = view.findViewById(R.id.salesbadge);

              view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v);
        }

    }
}
