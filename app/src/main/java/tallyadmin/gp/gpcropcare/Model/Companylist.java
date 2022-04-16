package tallyadmin.gp.gpcropcare.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Companylist implements Parcelable {

    String CmpGUID,CompanyName;

    public Companylist(String cmpGUID, String companyName) {
        CmpGUID = cmpGUID;
        CompanyName = companyName;
    }

    public Companylist() {

    }


    protected Companylist(Parcel in) {
        CmpGUID = in.readString();
        CompanyName = in.readString();
    }

    public static final Creator<Companylist> CREATOR = new Creator<Companylist>() {
        @Override
        public Companylist createFromParcel(Parcel in) {
            return new Companylist(in);
        }

        @Override
        public Companylist[] newArray(int size) {
            return new Companylist[size];
        }
    };

    public String getCmpGUID() {
        return CmpGUID;
    }

    public void setCmpGUID(String cmpGUID) {
        CmpGUID = cmpGUID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CmpGUID);
        dest.writeString(CompanyName);
    }
}
