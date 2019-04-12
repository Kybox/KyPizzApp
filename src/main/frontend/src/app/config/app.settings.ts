export class AppSettings {

    private static DOMAIN_API:string = "http://localhost:";
    private static PORT_API:number = 8080;

    public static PUBLIC_API:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/api/";
    public static PUBLIC_CATEGORY_API:string = AppSettings.PUBLIC_API + "category/";
    public static PUBLIC_DELIVERY_API:string = AppSettings.PUBLIC_API + "delivery/";

    public static ACCOUNT_API_URL:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/account/api/";

    public static ADMIN_API:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/admin/api/";
    public static ADMIN_CATEGORY_API:string = AppSettings.ADMIN_API + "category";
    public static ADMIN_USER_API:string = AppSettings.ADMIN_API + "user/";
    public static ADMIN_PAYMENT_API:string = AppSettings.ADMIN_API + "payment/";
    public static ADMIN_DELIVERY_API:string = AppSettings.ADMIN_API + "delivery/";

    public static CUSTOMER_ORDER_API:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/order/api/";
    public static CUSTOMER_PAYMENT_API:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/payment/api/";

    public static JWT_STORAGE_KEY:string = "KyPizzAppAuthToken";
    public static COOKIE_CART_KEY:string = "";
    public static JWT_HEADER:string = "Authorization";


    public static ERROR_UNAUTHORIZED:string = "Unauthorized";
    public static HTTP_CODE_UNAUTHORIZED:number = 401;

    public static URL_PAYMENT_SUCCESS:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/order/" + btoa("paymentSuccess");
    public static URL_PAYMENT_FAILED:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/order/" + btoa("paymentFailed");

}
