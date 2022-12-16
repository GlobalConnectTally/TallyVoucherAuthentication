package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

import tallyadmin.gp.gpcropcare.HomeActivity;
import tallyadmin.gp.gpcropcare.Interface.ItemClickListener;
import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;

public class CompanyHomeAdapter extends RecyclerView.Adapter<CompanyHomeAdapter.CompanyViewHolder> {
    private ArrayList<Company> companieslist;
    ArrayList<ArrayList<Company>> checkedTeachers = new ArrayList<ArrayList<Company>>();
    Context context;
    Companysave companydata;
    HomeActivity Activity;
    OnRecyclerViewItemClickListener listener;
    UserInfo userInfo;


    public ArrayList<Company> getCompanieslist() {
        return companieslist;
    }

    public CompanyHomeAdapter(ArrayList<Company> companieslist, Context context, HomeActivity Activity,OnRecyclerViewItemClickListener listener) {
        this.companieslist = companieslist;
        this.context = context;
        this.Activity = Activity;
        this.listener = listener;
        this.userInfo = new UserInfo(context.getApplicationContext());
    }

    @NonNull
    @Override
    public CompanyHomeAdapter.CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View itemView = LayoutInflater.from(context)
                .inflate(R.layout.spinner_layoutb, parent, false);

        return new CompanyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final CompanyHomeAdapter.CompanyViewHolder holder, final int pos) {

        int position = holder.getAdapterPosition();;

        holder.companygname.setText(companieslist.get(position).getCompanyName());

        if (companieslist.get(position).getPendingSales() != null &&
                companieslist.get(position).getPendingSales() != 0)
        {
            holder.mBage.setNumber(companieslist.get(position).getPendingSales());
            holder.mBage.setVisibility(View.VISIBLE);
        } else {
            holder.mBage.setVisibility(View.GONE);
        }

        holder.companygname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecyclerViewItemClicked(position,companieslist.get(position));
            }
        });

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog(
                        companieslist.get(position).getCmpGUID().toString(),
                        companieslist.get(position).getCompanyName().toString(),
                        companieslist.get(position).getCmpShortName().toString()
                );
            }
        });

    }

    private void showConfirmDialog(final String cmpGUID, final String companyName ,final String companyShortName)
    {
      //      final AlertDialog.Builder settingdialog = new AlertDialog.Builder(Activity,R.style.my_dialog);
//      View settinview = LayoutInflater.from(context ).inflate(R.layout.setting_layoutb, null);
//
//      settingdialog.setView(settinview);
//      settingdialog.setPositiveButton("CONFIRM TO CONTINUE", new DialogInterface.OnClickListener() {
//            @Override
//          public void onClick(DialogInterface dialog, int which) {
//        Toast.makeText(context,"1"+cmpGUID, Toast.LENGTH_LONG).show();

        System.out.println("Short-Name - 2:: " + companyShortName);

        companydata = new Companysave(context.getApplicationContext());
        companydata.setCompanyGid(cmpGUID);
        companydata.setcompany(companyName);
        companydata.setCmpShortName(companyShortName);
        Activity.cmpDialogExit();
        Toast.makeText(context, "Company changed success full", Toast.LENGTH_LONG).show();
       ////
//
//            }
//        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//               Activity.cmpDialogExit();
//                Toast.makeText(context,"Company not changed", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        settingdialog.show();
    }

    @Override
    public int getItemCount() {
        return companieslist.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener listener;

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        TextView companyguid, companygname;
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


    public interface OnRecyclerViewItemClickListener {
         public void onRecyclerViewItemClicked(int position, Company data);
    }

}

