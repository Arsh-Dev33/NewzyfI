package com.project.newzyfi.webservices;

import retrofit2.Retrofit;

public class RestClient {

    public static final String Base_url = "https://saurav.tech/NewsAPI/";
    public static Retrofit client = new Retrofit.Builder().baseUrl(Base_url).build();

}
