/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

//https://square.github.io/retrofit/

package com.example.ead_fuel_app.service;

import android.os.Build;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {

    private static final String URL = "https://ead-be.herokuapp.com/";

    // create logging interceptor
    private static final HttpLoggingInterceptor httpLoggingInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    // create an okhttp client
    private static final OkHttpClient.Builder okHttp = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chain -> {
                Request request = chain.request();

                request = request.newBuilder()
                        .addHeader("x-device-type", Build.DEVICE)
                        .build();

                return chain.proceed(request);
            });

    // create retrofit builder
    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build());

    // build retrofit
    private static final Retrofit retrofit = builder.build();

    // build service
    public static <T> T buildService(Class<T> serviceType){
        return retrofit.create(serviceType);
    }

}
