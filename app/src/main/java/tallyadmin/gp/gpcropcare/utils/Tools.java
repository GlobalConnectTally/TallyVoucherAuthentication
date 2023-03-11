package tallyadmin.gp.gpcropcare.utils;

import android.content.Context;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Tools {
    private Context mContext;

    public Tools(Context mContext) {
        this.mContext = mContext;
    }

    public String toDouble(String data){
        BigDecimal amount = new BigDecimal(data);
        DecimalFormat formatter78 = new DecimalFormat("#.###");
        //DecimalFormat formatter78 = new DecimalFormat("##,##,###.###");
        return formatter78.format(amount);
    }

}
