/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

//https://square.github.io/retrofit/

package com.example.ead_fuel_app.service;

import com.example.ead_fuel_app.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    // register the user
    @POST("api/v1/users/")
    Call<User> registerUser(@Body User user);

    //login user with nic number and password
    @POST("api/v1/users/login/")
    @FormUrlEncoded
    @Headers({"Content-Type:application/x-www-form-urlencoded"})
    Call<User> loginUser(@Field("grant_type") String grantType,
                         @Field("nic") String nic,
                         @Field("password") String password);
}
