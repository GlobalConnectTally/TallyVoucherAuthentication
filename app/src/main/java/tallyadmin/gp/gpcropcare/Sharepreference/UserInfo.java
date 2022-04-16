package tallyadmin.gp.gpcropcare.Sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfo {
    private static final String TAG = Session.class.getSimpleName();
    private static final String PREF_NAME = "userinfo";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMEI = "imei";
    private static final String AppLoginUserID = "phone";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;
    private static final String KEY_ACCEPT = "accept";
    private static final String KEY_REJECT = "reject";

    public UserInfo(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setImei(String imei){
        editor.putString(KEY_IMEI, imei);
        editor.apply();
    }

    public void  setAllowApprove(String approve){
        editor.putString(KEY_ACCEPT, approve);
        editor.apply();
    }

    public void  setAllowReject(String reject){
        editor.putString(KEY_REJECT, reject);
        editor.apply();
    }

    public void setpassword(String name){
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public void setAppLoginUserID(String phone){
        editor.putString(AppLoginUserID, phone);
        editor.apply();
    }

    public void clearUserInfo(){
        editor.clear();
        editor.commit();
    }

    public String getAllowReject(){return prefs.getString(KEY_REJECT, "");}

    public String getAllowApprove(){return prefs.getString(KEY_ACCEPT, "");}

    public String getImei() {
        return prefs.getString(KEY_IMEI, null);}

    public String getPassWord(){return prefs.getString(KEY_NAME, "");}

    public String getAppLoginUserID(){return prefs.getString(AppLoginUserID, "");}
}
