package tallyadmin.gp.gpcropcare.Sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private static final String TAG = Session.class.getSimpleName();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int num= 0;
    private static final String PREFS_NAME = "Username";
    private static final String STATE = "ISLOGIN";
    Context context;

    public Session(Context context) {
        this.context= context;
        sharedPreferences=context.getSharedPreferences(PREFS_NAME,num);
        editor=sharedPreferences.edit();

    }

    public void setLogin(boolean b){
        editor.putBoolean(STATE,b);
        editor.commit();

    }
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(STATE, false);}
}
