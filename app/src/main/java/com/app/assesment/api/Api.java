package com.app.assesment.api;

import com.app.assesment.model.ProfileData;
import com.app.assesment.model.SliderResponse;
import com.app.assesment.model.Timing;
import com.app.assesment.model.UserLogin;
import com.app.assesment.model.UserRegister;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * CREATED BY SANJAIKUMAR On 17-04-2020
 */
public interface Api {


    @FormUrlEncoded
    @POST("user_register.php")
    Call<UserRegister> user_register(
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile_no") String mobileNumber,
            @Field("first_name") String firstName,
            @Field("last_name") String LastName
    );

    @FormUrlEncoded
    @POST("user_login.php")
    Call<UserLogin> user_login(@Field("email") String email, @Field("password") String pasword);


    @GET("sliders.json")
    Call<List<SliderResponse>> Slider();

    @GET("timings.json")
    Call<JsonObject> timings();


    @FormUrlEncoded
    @POST("profile.php")
    Call<JsonObject> Profile(@Field("email") String email);

    @FormUrlEncoded
    @POST("forget_pass.php")
    Call<JsonObject> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("profile_update.php")
    Call<JsonObject> ProfileUpdate( @Field("email") String email,
                                    @Field("mobile_no") String mobileNumber,
                                    @Field("first_name") String first_name,
                                    @Field("last_name") String last_name);


}
