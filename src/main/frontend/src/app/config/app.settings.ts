export class AppSettings {

    private static DOMAIN_API:string = "http://localhost:";
    private static PORT_API:number = 8080;

    public static PUBLIC_API:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/api/";
    public static PUBLIC_CATEGORY_API:string = AppSettings.PUBLIC_API + "category/";

    public static ACCOUNT_API_URL:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/account/";

    public static ADMIN_API:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/admin/api/";
    public static ADMIN_CATEGORY_API:string = AppSettings.ADMIN_API + "category";
    public static ADMIN_USER_API:string = AppSettings.ADMIN_API + "user/";


    public static JWT_STORAGE_KEY:string = "KyPizzAppAuthToken";
    public static COOKIE_CART_KEY:string = "";
    public static JWT_HEADER:string = "Authorization";



    public static ERROR_UNAUTHORIZED:string = "Unauthorized";

}
