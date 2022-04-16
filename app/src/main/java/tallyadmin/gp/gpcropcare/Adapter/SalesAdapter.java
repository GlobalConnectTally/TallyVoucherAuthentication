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
import android.widget.Button;
import android.widget.EditText;
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

import tallyadmin.gp.gpcropcare.Activities.SalesActivity;
import tallyadmin.gp.gpcropcare.Activities.ShowtransactionActivity;
import tallyadmin.gp.gpcropcare.Activities.ShowtransactionOrderActivity;
import tallyadmin.gp.gpcropcare.Interface.ItemClickListener;
import tallyadmin.gp.gpcropcare.Model.SalesOrder;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;


public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesOrderViewHolder> {
    private ArrayList<SalesOrder> orderArrayList;
    Context context;
    Companysave companydata;
    UserInfo userInfo;
    SalesActivity salesOrderActivity;
    int masterID;
    String ordernn,mobilenumber;



    public SalesAdapter(ArrayList<SalesOrder> orderArrayList, Context context, SalesActivity salesOrderActivity) {
        this.orderArrayList = orderArrayList;
        this.context = context;
        this.salesOrderActivity = salesOrderActivity;
    }




    @NonNull
    @Override
    public SalesAdapter.SalesOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_redesignewt, parent, false);

        return new SalesOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SalesAdapter.SalesOrderViewHolder holder, final int position) {
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
       String valueprderno = orderArrayList.get(position).getTallyUserName();
        if (valueprderno.equals("#~#") || valueprderno.equals("")){
            holder.order_no.setText("");
        }else{
            holder.order_no.setText(valueprderno);
        }
        String valuesoname = orderArrayList.get(position).getBuyerName();
        if (valuesoname.equals("#~#") || valuesoname.equals("")){
            holder.txt_soname.setText("");
        }else{
            holder.txt_soname.setText(valuesoname);
        }

        mobilenumber = orderArrayList.get(position).getTallyUsermobileno();

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                masterID  = orderArrayList.get(position).getMasterID();
                companydata = new Companysave(context.getApplicationContext());
                companydata.setVoucher(orderArrayList.get(position).getVoucherNumber());
                Intent intent = new Intent(context, ShowtransactionActivity.class);
//                intent.putExtra()
                intent.putExtra("BayerName",orderArrayList.get(position).getBuyerName());
                intent.putExtra("PartName",orderArrayList.get(position).getPartyName());
                intent.putExtra("Date",orderArrayList.get(position).getDate());
                intent.putExtra("Amount",orderArrayList.get(position).getTotalAmt());
                intent.putExtra("Vouchertype",orderArrayList.get(position).getVoucherTypeName());
                ordernn = orderArrayList.get(position).getVoucherNumber();
                intent.putExtra("VoucherNo",ordernn);
//                intent.putExtra("Address",orderArrayList.get(position).getBuyerAddress());
                intent.putExtra("Narration",orderArrayList.get(position).getNarration());
                intent.putExtra("Reffno",orderArrayList.get(position).getReffNo());
                intent.putExtra("MasterId",orderArrayList.get(position).getMasterID());

                String name = orderArrayList.get(position).getBuyerAddress();
                String noad = "No address added!";
                if(name.isEmpty()){
                    name = noad;
                }
                intent.putExtra("Address",name);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

//VIEW MORE
        holder.txt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterID  = orderArrayList.get(position).getMasterID();
                companydata = new Companysave(context.getApplicationContext());
                companydata.setVoucher(orderArrayList.get(position).getVoucherNumber());
                Intent intent = new Intent(context, ShowtransactionActivity.class);
//                intent.putExtra()
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
//                intent.putExtra("Address",orderArrayList.get(position).getBuyerAddress());
                intent.putExtra("Narration",orderArrayList.get(position).getNarration());
                intent.putExtra("Reffno",orderArrayList.get(position).getReffNo());
                intent.putExtra("MasterId",orderArrayList.get(position).getMasterID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });



        //HANDLE Authentication
        holder.txto_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterID  = orderArrayList.get(position).getMasterID();
                Authorize();

            }
        });

        //Cancel
        holder.txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterID  = orderArrayList.get(position).getMasterID();
                Rejectinvo();

            }
        });






    }

    private void Rejectinvo() {
        companydata = new Companysave(context.getApplicationContext());
        userInfo = new UserInfo(context.getApplicationContext());
        //rejectmethod
        final AlertDialog.Builder settingdialog = new AlertDialog.Builder(salesOrderActivity);

        View settinview= LayoutInflater.from(context ).inflate(R.layout.rejectmarks_layoutb, null);
        Button Okbtn = settinview.findViewById(R.id.btn_ok);
        Button rejct = settinview.findViewById(R.id.btn_cancel);

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


                final String remark = remarkt.getText().toString();

                if (TextUtils.isEmpty(remark)) {
                    remarkt.setError("Please enter reason for rejection");
                    remarkt.requestFocus();
                    return;
                }


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AUTHORIZE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                alertDialog.dismiss();
                                try {

                                    JSONObject obj = new JSONObject(response);

                                    int result = obj.getInt("Status");
                                    if (result==1){
                                        Toast.makeText(context,"Authorized success full",Toast.LENGTH_SHORT).show();
                                        sendmessager();
                                        salesOrderActivity.fetchingJSON();
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
                        }) {
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AUTHORIZE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);

                            int result = obj.getInt("Status");
                            if (result==1){
                                Toast.makeText(context,"Authorized success full",Toast.LENGTH_SHORT).show();
                                sendmessage();
                                salesOrderActivity.fetchingJSON();

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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                params.put("CmpGUID",companydata.getKeyCmpnGid());
                params.put("MasterID",String.valueOf(masterID));
                params.put("AuthenticationFlag","A");
                params.put("Remark",".");
                params.put("TransactionType","1");
                return params;
            }
        };

        VolleySingleton.getInstance(this.context).addToRequestQueue(stringRequest);

    }

    private void sendmessage() {

        String msg = "Dear User Your Transaction With OrderNo -" +ordernn+ "  is been Approved";
        //String mobilenumber = mobilenumber;

        String URLNA = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to="+mobilenumber+"&msg="+msg+"&senderid=GPCCPL&route=4";





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

    private void sendmessager() {

        String msg = "Dear User Your Transaction With OrderNo -" +ordernn+ "  is been Rejected";

        //String mobilenumber = tallymobileno;


        String URLNA = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to="+mobilenumber+"&msg="+msg+"&senderid=GPCCPL&route=4";


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

    public class SalesOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView customer_name, date, order_no,order_amount,txt_authenticated,txt_master,txt_sales,txt_soname;
        Button txto_author,txt_cancel,txt_more;
        ItemClickListener itemClickListener;


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

            txto_author = itemView.findViewById(R.id.txt_author);
            txt_cancel= itemView.findViewById(R.id.txt_cancel);
            txt_more = itemView.findViewById(R.id.txti_more);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view);
        }
    }
}
