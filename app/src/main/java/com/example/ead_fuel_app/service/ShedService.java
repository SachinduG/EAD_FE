/**
 * @author S.M. Jayasekara
 * @IT_number IT19161648
 */

//https://square.github.io/retrofit/

package com.example.ead_fuel_app.service;


import com.example.ead_fuel_app.models.Shed;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShedService {

    @GET("api/v1/shed/{address}")
    Call<ArrayList<Shed>> ShedsByAddress(@Path ("address") String address);

    @GET("api/v1/shed/{address}/{type}")
    Call<Shed> ShortestQueueByAddressAndType(@Path ("address") String address, @Path("type") String type);

    @POST("api/v1/shed/")
    Call<Shed> registerShed(@Body Shed shed);

    @GET("api/v1/shed/get/{regNo}")
    Call<Shed> ShedById(@Path("regNo") String regNo);

    @GET("api/v1/shed/petrol-arrived/{regNo}")
    Call<Shed> petrolArrived(@Path("regNo") String regNo);

    @GET("api/v1/shed/diesel-arrived/{regNo}")
    Call<Shed> dieselArrived(@Path("regNo") String regNo);

    @GET("api/v1/shed/petrol-finished/{regNo}")
    Call<Shed> petrolFinished(@Path("regNo") String regNo);

    @GET("api/v1/shed/diesel-finished/{regNo}")
    Call<Shed> dieselFinished(@Path("regNo") String regNo);

    @POST("api/v1/shed/login/")
    @FormUrlEncoded
    @Headers({"Content-Type:application/x-www-form-urlencoded"})
    Call<Shed> loginShed(@Field("grant_type") String grantType,
                         @Field("regNo") String regNo,
                         @Field("password") String password);
}
