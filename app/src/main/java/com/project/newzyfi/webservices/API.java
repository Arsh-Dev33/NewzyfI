package com.project.newzyfi.webservices;

import com.project.newzyfi.response.TrendingResponse;

import java.lang.reflect.Array;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {

    @GET("top-headlines/category/{category_name}/{country_code}.json")
    Call<TrendingResponse> getTrending(@Path("category_name") String category_name,@Path("country_code") String country_code);

    @GET("everything/{source_name}.json")
    Call<TrendingResponse>  getSource(@Path("source_name") String source_name);
}
