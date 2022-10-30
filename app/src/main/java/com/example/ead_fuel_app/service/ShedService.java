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

    //get sheds by city
    @GET("api/v1/shed/{address}")
    Call<ArrayList<Shed>> ShedsByAddress(@Path ("address") String address);

    //get the shed with shortest queue by city and fuel type
    @GET("api/v1/shed/{address}/{type}")
    Call<Shed> ShortestQueueByAddressAndType(@Path ("address") String address, @Path("type") String type);

    //register the shed
    @POST("api/v1/shed/")
    Call<Shed> registerShed(@Body Shed shed);

    //get shed details by register number
    @GET("api/v1/shed/get/{regNo}")
    Call<Shed> ShedById(@Path("regNo") String regNo);

    // update petrol arrival time
    @GET("api/v1/shed/petrol-arrived/{regNo}")
    Call<Shed> petrolArrived(@Path("regNo") String regNo);

    // update diesel arrival time
    @GET("api/v1/shed/diesel-arrived/{regNo}")
    Call<Shed> dieselArrived(@Path("regNo") String regNo);

    //update petrol finish time
    @GET("api/v1/shed/petrol-finished/{regNo}")
    Call<Shed> petrolFinished(@Path("regNo") String regNo);

    //update diesel finish time
    @GET("api/v1/shed/diesel-finished/{regNo}")
    Call<Shed> dieselFinished(@Path("regNo") String regNo);

    //login shed using register number and password
    @POST("api/v1/shed/login/")
    @FormUrlEncoded
    @Headers({"Content-Type:application/x-www-form-urlencoded"})
    Call<Shed> loginShed(@Field("grant_type") String grantType,
                         @Field("regNo") String regNo,
                         @Field("password") String password);
}
