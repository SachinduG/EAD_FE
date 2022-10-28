/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

//https://square.github.io/retrofit/

package com.example.ead_fuel_app.service;

import com.example.ead_fuel_app.models.Queue;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QueueService {

    @GET("api/v1/queue/waiting-time/{regNo}/{type}")
    Call<Long> AvgWaitingTimeByIdType(@Path("regNo") String regNo, @Path("type") String fuelType);

    @POST("api/v1/queue/insert/")
    Call<Queue> enterQueue(@Body Queue queue);

    @POST("api/v1/queue/exit/{id}")
    Call<Queue> leaveQueue(@Path("id") String id);

    @POST("api/v1/queue/leave/{id}")
    Call<Queue> leaveEarlyQueue(@Path("id") String id);
}
