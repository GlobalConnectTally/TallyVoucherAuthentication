package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import tallyadmin.gp.gpcropcare.Activities.ReportActivity;
import tallyadmin.gp.gpcropcare.Activities.SalesOrderActivity;
import tallyadmin.gp.gpcropcare.Activities.ShowtransactionOrderActivity;
import tallyadmin.gp.gpcropcare.Activities.Sixreportactivity;
import tallyadmin.gp.gpcropcare.Interface.ItemClickListener;
import tallyadmin.gp.gpcropcare.Model.SalesOrder;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_SMS;


public class SalesOrderAdapter  extends RecyclerView.Adapter<SalesOrderAdapter.SalesOrderViewHolder> {

    private ArrayList<SalesOrder> orderArrayList;
    Context context;
    Companysave companydata;
    UserInfo userInfo;
    SalesOrderActivity salesOrderActivity;
    int masterID;
    String Legid;
    String ordernn,tallymobilenumber,AuthenticationFlag;


    public SalesOrderAdapter(ArrayList<SalesOrder> orderArrayList, Context context, SalesOrderActivity salesOrderActivity) {
        this.orderArrayList = orderArrayList;
        this.context = context;
        this.salesOrderActivity = salesOrderActivity;
    }


    @NonNull
    @Override
    public SalesOrderAdapter.SalesOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_redesignewt, parent, false);

        return new SalesOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SalesOrderAdapter.SalesOrderViewHolder holder, final int position) {
        String valuecustomername = orderArrayList.get(position).getPartyName();
        if (valuecustomername.equals("#~#") || valuecustomername.equals("")){
            holder.customer_name.setText("");
        }else{
            holder.customer_name.setText(valuecustomername);
        }

        String valuedate = orderArrayList.get(position).getDate();
        if (valuedate.equals("#~#") || valuedate.equals("")){
            holder.date.setText("");
        }else{
            holder.date.setText(valuedate);
        }

        String  vauleorderamount  = String.valueOf(orderArrayList.get(position).getTotalAmt());
        if (vauleorderamount.equals("#~#") || vauleorderamount.equals("")){
            holder.order_amount.setText("");
        }else{
            holder.order_amount.setText(vauleorderamount);
        }

        String valuetxtauthenticated = orderArrayList.get(position).getVoucherNumber();
        if (valuetxtauthenticated.equals("#~#") || valuetxtauthenticated.equals("")){
            holder.txt_authenticated.setText("");
        }else{
            holder.txt_authenticated.setText(valuetxtauthenticated);
        }

        String valuesoname = orderArrayList.get(position).getBuyerName();
        if (valuesoname.equals("#~#") || valuesoname.equals("")){
            holder.txt_soname.setText("");
        }else{
            holder.txt_soname.setText(valuesoname);
        }

        String accept = orderArrayList.get(position).getAllowApprove().toString();
        if (accept.equals("Yes")){
            holder.txto_author.setVisibility(View.VISIBLE);
        }else {
            holder.txto_author.setVisibility(View.INVISIBLE);
        }

        String reject = orderArrayList.get(position).getAllowReject().toString();
        if (reject.equals("Yes")){
            holder.txt_cancel.setVisibility(View.VISIBLE);
        }else {
            holder.txt_cancel.setVisibility(View.INVISIBLE);
        }

        String authFlag = orderArrayList.get(position).getAuthenticationFlag().toString();
        if (authFlag.equals("A1")){
            holder.authFlagText.setVisibility(View.VISIBLE);
        }else {
            holder.authFlagText.setVisibility(View.INVISIBLE);
        }

        /*

        holder.order_no.setText(orderArrayList.get(position).getTallyUserName());
        holder.txt_sales.setText(orderArrayList.get(position).getVoucherTypeName());
        holder.txt_master.setText(new StringBuffer("").append(orderArrayList.get(position).getMasterID()).append(":").toString());
*/

        ordernn = orderArrayList.get(position).getVoucherNumber();

        //onitemclick
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                masterID  = orderArrayList.get(position).getMasterID();
                Legid = orderArrayList.get(position).getLedgerMasterId();
                companydata = new Companysave(context.getApplicationContext());
                companydata.setMasterId(masterID);
                companydata.setPartyName(orderArrayList.get(position).getPartyName());
                companydata.setTallyUsermobile(orderArrayList.get(position).getTallyUsermobileno());
                companydata.setVoucher(orderArrayList.get(position).getVoucherNumber());
                companydata.setLegId(Legid);
                companydata.setBillAmount(orderArrayList.get(position).getTotalAmt());
                companydata.setVocherdate(orderArrayList.get(position).getDate());
                Intent intent = new Intent(context, ShowtransactionOrderActivity.class);
                //intent.putExtra()
                intent.putExtra("BayerName",orderArrayList.get(position).getBuyerName());
                intent.putExtra("PartName",orderArrayList.get(position).getPartyName());
                intent.putExtra("Date",orderArrayList.get(position).getDate());
                intent.putExtra("Amount",orderArrayList.get(position).getTotalAmt());
                intent.putExtra("Vouchertype",orderArrayList.get(position).getVoucherTypeName());
                intent.putExtra("VoucherNo",orderArrayList.get(position).getVoucherNumber());
                intent.putExtra("Narration",orderArrayList.get(position).getNarration());
                intent.putExtra("Reffno",orderArrayList.get(position).getReffNo());
                intent.putExtra("MasterId",orderArrayList.get(position).getMasterID());
                intent.putExtra("AuthenticationFlag",orderArrayList.get(position).getAuthenticationFlag());

                String name = orderArrayList.get(position).getBuyerAddress();
                String noad = "No address added!";
                if(name.isEmpty()){ name = noad; }
                intent.putExtra("Address",name);
                tallymobilenumber = orderArrayList.get(position).getTallyUsermobileno();
                System.out.println("mobile:"+tallymobilenumber);
                intent.putExtra("TallyUserMobNo",tallymobilenumber);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //VIEW MORE
        holder.txt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterID  = orderArrayList.get(position).getMasterID();
                Legid = orderArrayList.get(position).getLedgerMasterId();
                companydata = new Companysave(context.getApplicationContext());
                companydata.setVoucher(orderArrayList.get(position).getVoucherNumber());
                companydata.setMasterId(masterID);
                companydata.setPartyName(orderArrayList.get(position).getPartyName());
                companydata.setTallyUsermobile(orderArrayList.get(position).getTallyUsermobileno());
                Intent intent = new Intent(context, ShowtransactionOrderActivity.class);
//              /intent.putExtra
                companydata.setLegId(Legid);
                companydata.setBillAmount(orderArrayList.get(position).getTotalAmt());
                companydata.setVocherdate(orderArrayList.get(position).getDate());
                intent.putExtra("BayerName",orderArrayList.get(position).getBuyerName());
                intent.putExtra("PartName",orderArrayList.get(position).getPartyName());
                intent.putExtra("Date",orderArrayList.get(position).getDate());
                intent.putExtra("Amount",orderArrayList.get(position).getTotalAmt());
                intent.putExtra("Vouchertype",orderArrayList.get(position).getVoucherTypeName());
                intent.putExtra("VoucherNo",orderArrayList.get(position).getVoucherNumber());
                String name = orderArrayList.get(position).getBuyerAddress();
                String noad = "No address added!";
                if(name.isEmpty()){
                    name = noad;
                }
                intent.putExtra("Address",name);
                intent.putExtra("Narration",orderArrayList.get(position).getNarration());
                intent.putExtra("Reffno",orderArrayList.get(position).getReffNo());
                intent.putExtra("MasterId",orderArrayList.get(position).getMasterID());
                intent.putExtra("TallyUserMobNo",orderArrayList.get(position).getTallyUsermobileno());
                intent.putExtra("AuthenticationFlag",orderArrayList.get(position).getAuthenticationFlag());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //HANDLE Authentication
        holder.txto_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterID  = orderArrayList.get(position).getMasterID();
                AuthenticationFlag = orderArrayList.get(position).getAuthenticationFlag();
                Authorize();
            }
        });

        //Cancel
        holder.txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterID  = orderArrayList.get(position).getMasterID();
                AuthenticationFlag = orderArrayList.get(position).getAuthenticationFlag();
                Rejectinvo();
            }
        });

        holder.report_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Sixreportactivity.class);
                masterID  = orderArrayList.get(position).getMasterID();
                Legid = orderArrayList.get(position).getLedgerMasterId();
                companydata = new Companysave(context.getApplicationContext());
                companydata.setVoucher(orderArrayList.get(position).getVoucherNumber());
                companydata.setMasterId(masterID);
                companydata.setLegId(Legid);
                companydata.setBillAmount(orderArrayList.get(position).getTotalAmt());
                companydata.setVocherdate(orderArrayList.get(position).getDate());
                companydata.setTallyUsermobile(orderArrayList.get(position).getTallyUsermobileno());
                companydata.setPartyName(orderArrayList.get(position).getPartyName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    private void Rejectinvo() {
        //rejectmethod
        final AlertDialog.Builder settingdialog = new AlertDialog.Builder(salesOrderActivity);

        View settinview= LayoutInflater.from(context ).inflate(R.layout.rejectmarks_layoutb, null);
        TextView Okbtn = settinview.findViewById(R.id.btn_ok);
        TextView rejct = settinview.findViewById(R.id.btn_cancel);

        final EditText remarkt = settinview.findViewById(R.id.remark);
        settingdialog.setView(settinview);
        final AlertDialog alertDialog = settingdialog.create();

        rejct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                companydata = new Companysave(context.getApplicationContext());
                userInfo = new UserInfo(context.getApplicationContext());

                final String remark = remarkt.getText().toString();

                if (TextUtils.isEmpty(remark)) {
                    remarkt.setError("Please enter reason for rejection");
                    remarkt.requestFocus();
                    return;
                }

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        URL_AUTHORIZE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                alertDialog.dismiss();
                                try {

                                    JSONObject obj = new JSONObject(response);

                                    int result = obj.getInt("Status");
                                    if (result==1){
                                        salesOrderActivity.fetchingJSON();
                                        Toast.makeText(context,"Authorized success full",Toast.LENGTH_SHORT).show();
                                        sendmessaged();
                                    }
                                    else {
                                        Toast.makeText(context,"Authorization Failed"+ "  "+result,Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                  {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                        params.put("CmpGUID",companydata.getKeyCmpnGid());
                        params.put("MasterID",String.valueOf(masterID));
                        params.put("AuthenticationFlag","R");
                        params.put("Remark",remark);
                        params.put("TransactionType","1");
                        return params;
                    }
                 };

                VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

            }
        });

        alertDialog.show();
    }

    private void Authorize() {
        //authorize method
        companydata = new Companysave(context.getApplicationContext());
        userInfo = new UserInfo(context.getApplicationContext());

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_AUTHORIZE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);

                            int result = obj.getInt("Status");
                            if (result==1){
                                salesOrderActivity.fetchingJSON();

                                if (    userInfo.getSecondLevel().equalsIgnoreCase("Yes") &&
                                        AuthenticationFlag.equalsIgnoreCase("A1")) {

                                     sendmessage();
                                }

                                Toast.makeText(context,"Authorized success full",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context,"Authorization Failed"+ "  "+result,Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                       /*
                       if (Stutas==(response.equals("Status")) {

                        }
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(),"CLICKEDDDDD",Toast.LENGTH_SHORT);
                        */

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                params.put("CmpGUID",companydata.getKeyCmpnGid());
                params.put("MasterID",String.valueOf(masterID));

                /*---- USER-LEVEL AUTHENTICATION----------*/
                String accessLevel = " ";
                if (  userInfo.getFirstLevel().equalsIgnoreCase("Yes") &&
                        userInfo.getSecondLevel().equalsIgnoreCase("Yes")){

                  if (AuthenticationFlag.equalsIgnoreCase("P")){
                      //B - For Both P & A1
                      accessLevel = "A1";

                  }else {
                      //B - For Both P & A1
                      accessLevel = "A2";
                  }

                }else if (  userInfo.getFirstLevel().equalsIgnoreCase("Yes")
                        && userInfo.getSecondLevel().equalsIgnoreCase("No")){
                    //P - Pending
                    accessLevel = "A1";

                }else if (  userInfo.getFirstLevel().equalsIgnoreCase("No")
                        && userInfo.getSecondLevel().equalsIgnoreCase("Yes")){
                    //A1 - Approved By First Level
                    accessLevel = "A2";

                }else if (  userInfo.getFirstLevel().equalsIgnoreCase("No")
                        && userInfo.getFirstLevel().equalsIgnoreCase("No")){
                    //Unknown - Returns Empty
                    accessLevel = "Unknown";

                }

                params.put("AuthenticationFlag", accessLevel);
                params.put("Remark",".");
                params.put("TransactionType","1");
                return params;
            }
        };

        VolleySingleton.getInstance(this.context).addToRequestQueue(stringRequest);

    }

    private void sendmessage() {

         String msg = "Dear User Your Transaction With OrderNo -" +ordernn+ "  is been Approved ";

        // http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to=9427036393&msg=YOURMESSAGE&senderid=GPCCPL&route=4
        String mobile_number = tallymobilenumber;


        String URLNA = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to="+mobile_number+"&msg="+msg+"&senderid=GPCCPL&route=4";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLNA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context," "+response.toString(),Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
           //            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("mobile","9879518214");
//                params.put("pass","9879518214");
//                params.put("to","9427036393");
//                params.put("msg","Dear User Your Transaction With OrderNo -" +ordernn+ "is then Approved");
//                params.put("senderid","GPCCPL");
//                params.put("route","4");
//                return params;
//            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    private void sendmessaged()
    {

        String msg = "Dear User Your Transaction With OrderNo -" +ordernn+ "  is been Rejected";
        String mobile_number = tallymobilenumber;
        System.out.println("Mobileno:"+mobile_number);


        String URLNA = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to="+mobile_number+"&msg="+msg+"&senderid=GPCCPL&route=4";





        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLNA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context," "+response.toString(),Toast.LENGTH_SHORT).show();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class SalesOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView customer_name, date, order_no,order_amount,txt_authenticated,txt_master,txt_sales,txt_soname;
        TextView txto_author,txt_cancel,report_more,authFlagText;
        ItemClickListener itemClickListener;
        ImageView txt_more;


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public SalesOrderViewHolder(@NonNull View itemView) {

            super(itemView);
            customer_name = itemView.findViewById(R.id.texto_cuname);
            date = itemView.findViewById(R.id.texto_date);
            order_no = itemView.findViewById(R.id.txt_createdbys);
            order_amount = itemView.findViewById(R.id.texto_amount);
            txt_authenticated = itemView.findViewById(R.id.txt_vouchernrs);
//            txt_master = itemView.findViewById(R.id.txto_master);
//            txt_sales = itemView.findViewById(R.id.txt_type);
            txt_soname = itemView.findViewById(R.id.text_soname);

            report_more = itemView.findViewById(R.id.report_more);

            txto_author = itemView.findViewById(R.id.txt_author);
            txt_cancel= itemView.findViewById(R.id.txt_cancel);
            txt_more = itemView.findViewById(R.id.txti_more);
            authFlagText = itemView.findViewById(R.id.authFlag);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view);
        }
    }

    public void filterList(ArrayList<SalesOrder> filterdNames) {
        this.orderArrayList = filterdNames;
        notifyDataSetChanged();
    }
}
