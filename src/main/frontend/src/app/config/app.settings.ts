export class AppSettings {

    private static DOMAIN_API:string = "http://localhost:";
    private static PORT_API:number = 8080;

    public static PUBLIC_API_URL:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/api/";
    public static ADMIN_API_URL:string = AppSettings.DOMAIN_API + AppSettings.PORT_API + "/admin/";

    public static JWT_STORAGE_KEY:string = "KyPizzAppAuthToken";

}
