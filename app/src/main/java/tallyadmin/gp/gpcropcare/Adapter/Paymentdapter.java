package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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

import tallyadmin.gp.gpcropcare.Activities.PaymentActivity;
import tallyadmin.gp.gpcropcare.Activities.PaytransActivity;
import tallyadmin.gp.gpcropcare.Interface.ItemClickListener;
import tallyadmin.gp.gpcropcare.Model.Payment;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;


public class Paymentdapter extends RecyclerView.Adapter<Paymentdapter.SalesOrderViewHolder> {
    private ArrayList<Payment> orderArrayList;
    Context context;
    Companysave companydata;
    UserInfo userInfo;
    PaymentActivity paymentActivity;
    String Masterid;

    public Paymentdapter(ArrayList<Payment> orderArrayList, Context context, PaymentActivity paymentActivity) {
        this.orderArrayList = orderArrayList;
        this.context = context;
        this.paymentActivity =paymentActivity;

    }




    @NonNull
    @Override
    public Paymentdapter.SalesOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.payment_itemt, parent, false);

        return new SalesOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Paymentdapter.SalesOrderViewHolder holder, final int position) {
        holder.customer_name.setText(orderArrayList.get(position).getNarration());
        holder.date.setText(orderArrayList.get(position).getVoucherDate());
        holder.order_amount.setText(orderArrayList.get(position).getAmount());
        holder.order_no.setText(orderArrayList.get(position).getTallyUserName());
        holder.txt_authenticated.setText(orderArrayList.get(position).getVoucherNumber());

        //onitemclick
        holder.customer_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(paymentActivity)
                        .setMessage(orderArrayList.get(position).getNarration())
                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();

            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Masterid = String.valueOf(orderArrayList.get(position).getMasterID());

                companydata = new Companysave(context.getApplicationContext());
                companydata.setVoucher(orderArrayList.get(position).getVoucherNumber());
                Intent intent = new Intent(context, PaytransActivity.class);
                  intent.putExtra("MasterId",orderArrayList.get(position).getMasterID());
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

//VIEW MORE
        holder.txt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Masterid = String.valueOf(orderArrayList.get(position).getMasterID());
                companydata = new Companysave(context.getApplicationContext());
                companydata.setVoucher(orderArrayList.get(position).getVoucherNumber());
                Intent intent = new Intent(context, PaytransActivity.class);
                  intent.putExtra("MasterId",orderArrayList.get(position).getMasterID());
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent);


            }
        });



        //HANDLE Authentication
        holder.txto_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Masterid = String.valueOf(orderArrayList.get(position).getMasterID());
                Authorize();

            }
        });

        //Cancel
        holder.txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Masterid = String.valueOf(orderArrayList.get(position).getMasterID());
                Rejectinvo();

            }
        });






    }

    private void Rejectinvo() {
        //rejectmethod
        final AlertDialog.Builder settingdialog = new AlertDialog.Builder(paymentActivity);

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
                companydata = new Companysave(context.getApplicationContext());
                userInfo = new UserInfo(context.getApplicationContext());

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

                                        Toast.makeText(context,"Rejection success full",Toast.LENGTH_SHORT).show();
                                        paymentActivity.fetchingJSON();
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
                        params.put("MasterID",Masterid);
                        params.put("AuthenticationFlag","R");
                        params.put("Remark",remark);
                        params.put("TransactionType","2");
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
                                paymentActivity.fetchingJSON();
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
                params.put("MasterID",Masterid);
                params.put("AuthenticationFlag","A");
                params.put("Remark",".");
                params.put("TransactionType","2");
                return params;
            }
        };

        VolleySingleton.getInstance(this.context).addToRequestQueue(stringRequest);

    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class SalesOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView date,customer_name, order_no,order_amount,txt_authenticated,txt_master,txt_sales;
        Button txto_author,txt_cancel,txt_more;
        ItemClickListener itemClickListener;



        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public SalesOrderViewHolder(@NonNull View itemView) {
           super(itemView);
          customer_name = itemView.findViewById(R.id.texto_narration);
            date = itemView.findViewById(R.id.texto_datep);
            order_no = itemView.findViewById(R.id.txt_createdbyp);
            order_amount = itemView.findViewById(R.id.texto_amountp);
            txt_authenticated = itemView.findViewById(R.id.txt_vouchernp);
         txto_author = itemView.findViewById(R.id.txt_authorp);
            txt_cancel= itemView.findViewById(R.id.txt_cancelp);
            txt_more = itemView.findViewById(R.id.txti_morep);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view);
        }
    }
}
