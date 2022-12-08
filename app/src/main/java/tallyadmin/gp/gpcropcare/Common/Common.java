package tallyadmin.gp.gpcropcare.Common;

public class Common {
    public static final String BASE_URL = "http://gatewayinfosoft.com/api/";
    //public static final String BASE_URL = "http://110.227.252.89:3232/api/";
    //public static final String BASE_URL = "http://20.213.11.187:1024/api/";
    public static final String URL_LOGIN = BASE_URL + "AppLogin";
    public static final String URL_ITEMS = BASE_URL + "SendStockDetails";
    public static final String URL_AUTHORIZE = BASE_URL + "TransactionAuthentication";

    public static final String URL_SALES = BASE_URL + "SendSalesDetails";

    public static final String URL_SALESTRANS = BASE_URL + "SendSalesAllDetails";

    public static final String URL_DEMOREGISTER = "https://globalsoftwares.net/tallyauthentication/RegInsertData.php";

    public static final String URL_ACTIVATE = "https://globalsoftwares.net/tallyauthentication/licenseappapi.php";

    public static final String URL_CHECKDEMO = "https://globalsoftwares.net/tallyauthentication/RegInsertData.php";

    public static final String URL_PAYMENTGET = BASE_URL + "SendPaymentMaster";

    public  static final String URL_SENDPAYMENTDETAILS = BASE_URL + "SendPaymentDetails";

    public  static final String URL_DASHBOARDSBADGES = BASE_URL + "AppDashboard";

    public  static final String URL_SMS = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx";

    public  static final String URL_REPORT = BASE_URL + "GetGpcReportDetails";

    public  static final String URL_REPORTNrc = BASE_URL + "GPCropCareSendReport";

}
