package tallyadmin.gp.gpcropcare.Sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

public class Companysave {
    private static final String TAG = Session.class.getSimpleName();
    private static final String PREF_NAME = "company";
    private static final String VOUCHER = "VOUCHERMO";
    private static final String KEY_NAME = "name";
    private static final String TALLY_USERMOBILE = "TALLYMOBILE";
    private static final String CMPGID = "phone";
    private static final String MasterId = "masterid";
    private static final String LegId = "LedgerMasterId";
    private static final String Leghtsize = "size";
    private static final String Vocherdate = "Date";
    private static final String BillAmount = "TotalAmt";
    private static final String PartyName = "PartyName";
    private static final String CmpShortName = "CmpShortName";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;


    public Companysave(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setMasterId(int name){
        editor.putInt(MasterId, name);
        editor.apply();
    }
    public void setLegId(String name){
        editor.putString(LegId, name);
        editor.apply();
    }

    public void setPartyName(String name){
        editor.putString(PartyName, name);
        editor.apply();
    }
    public void setVocherdate(String name){
        editor.putString(Vocherdate, name);
        editor.apply();
    }
    public void setBillAmount(String name){
        editor.putString(BillAmount, name);
        editor.apply();
    }

    public void setsize(int name){
        editor.putInt(Leghtsize, name);
        editor.apply();
    }

    public void setVoucher(String name){
        editor.putString(VOUCHER, name);
        editor.apply();
    }

    public void setTallyUsermobile(String name){
        editor.putString(TALLY_USERMOBILE, name);
        editor.apply();
    }


    public void setcompany(String name){
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public void setCompanyGid(String phone){
        editor.putString(CMPGID, phone);
        editor.apply();
    }

    public void setCmpShortName(String CmpShortNameValue)
    {
        editor.putString(CmpShortName, CmpShortNameValue);
        editor.apply();
    }

    public void clearCompany(){
        editor.clear();
        editor.commit();
    }

    public String getKeyName(){return prefs.getString(KEY_NAME, "");}
    public String getKeyVocherdate(){return prefs.getString(Vocherdate, "");}
    public String getKeyBillAmount(){return prefs.getString(BillAmount, "");}

    public String getTallyUsermobile(){
        return prefs.getString(TALLY_USERMOBILE, "");}

    public String getKeyCmpnGid(){return prefs.getString(CMPGID, "");}
    public String getCmpShortName(){return prefs.getString(CmpShortName, "");}

    public int getKeyMasterId(){
        return prefs.getInt(MasterId, 0);}
    public String getKeyLegId(){
        return prefs.getString(LegId, "");}
    public String getKeyPartyName(){return prefs.getString(PartyName, "");}

    public int getSizel(){
        return prefs.getInt(Leghtsize, 0);}

    public String getVoucher(){return prefs.getString(VOUCHER, "");}



}
