package tallyadmin.gp.gpcropcare.utils;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import tallyadmin.gp.gpcropcare.R;


public class VolleyErrors {

    private Context mContext;
    public VolleyErrors(Context context) {
        this.mContext = context;
    }

    public String exceptionMessage(VolleyError volleyError){

        String message = " ";

        if (volleyError instanceof NetworkError){

            message = mContext.getResources().getString(R.string.netError);

        }else if (volleyError instanceof ServerError){

            message = mContext.getResources().getString(R.string.serverError);

        }else if (volleyError instanceof AuthFailureError){

            message = mContext.getResources().getString(R.string.authError);

        }else if (volleyError instanceof NoConnectionError){

            message = mContext.getResources().getString(R.string.connError);

        }else if (volleyError instanceof TimeoutError){

            message = mContext.getResources().getString(R.string.timeOutError);

        }else if (volleyError instanceof ParseError){

            message = mContext.getResources().getString(R.string.parseError);

        }else {
            message = "There is problem during fetching data. Try after few seconds.";
        }
        return  message;
    }
}
