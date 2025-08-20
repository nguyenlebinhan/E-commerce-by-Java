package com.example.demo.Config;

public class AppConstants {

    public static final String PAGE_NUMBER ="0";
    public static final String PAGE_SIZE ="2";
    public static final String SORT_CATEGORIES_BY ="categoryId";
    public static final String SORT_PRODUCT_BY ="productId";
    public static final String SORT_USER_BY ="userId";
    public static final String SORT_ORDERS_BY ="totalAmount";
    public static final String SORT_DIR ="asc";
    public static final Long ADMIN_ID =101L;
    public static final Long USER_ID=102L ;
    public static final long JWT_TOKEN_VALIDITY =1000*60*60*10;
    public static final String[] PUBLIC_URLS ={"/login","/register"};
    public static final String[] USER_URLS = { "/public/**" };
    public static final String[] ADMIN_URLS = { "/admin/**" };

}
