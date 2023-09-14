package com.example.mylap.services;

import com.example.mylap.models.User;
import com.example.mylap.responsive.CountLearnRes;
import com.example.mylap.responsive.CourseDetailRes;
import com.example.mylap.responsive.GetCategory;
import com.example.mylap.responsive.GetListCourse;
import com.example.mylap.responsive.GetListTopicRes;
import com.example.mylap.responsive.LoginRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
//    @GET("api/endpoint")
//    Call<YourResponseModel> yourGetMethod(@Query("param1") String param1, @Query("param2") int param2);

    //api/category/load-category-by-status
    @POST("api/category/load-category-by-status")
    Call<GetCategory> getCategorys(@Query("status") int status);

    @FormUrlEncoded
    @POST("/api-mobile/count-topic-in-course")
    Call<CountLearnRes> countTopicInCourse(@Field("courseId") String courseId);

    @FormUrlEncoded
    @POST("/api-mobile/get-list-course-by-category-id")
    Call<GetListCourse> getListCourseByCategoryId(@Field("categoryId") String categoryId);

    @FormUrlEncoded
    @POST("/api-mobile/login")
    Call<LoginRes> login(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api-mobile/register")
    Call<LoginRes> register(
            @Field("account") String account,
            @Field("password") String password,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phoneNumber") String phoneNumber,
            @Field("gender") int gender
    );

    @FormUrlEncoded
    @POST("/api-mobile/session")
    Call<Void> session(@Field("token") String token);

    @FormUrlEncoded
    @POST("/api-mobile/get-course-by-id")
    Call<CourseDetailRes> getCourseById(@Field("courseId") String courseId);

    @FormUrlEncoded
    @POST("/api-mobile/get-list-topic-by-courseId")
    Call<GetListTopicRes> getTopicByCourse(@Field("courseId") String courseId, @Field("type") int type, @Field("status") int status);

    @FormUrlEncoded
    @POST("/api-mobile/user")
    Call<User> getUserInfo(@Field("token") String token);

    @FormUrlEncoded
    @POST("/api-mobile/update-user")
    Call<User> updateUser(@Field("_id") String _id, @Field("email") String email, @Field("account") String account, @Field("phoneNumber") String phoneNumber, @Field("gender") String gender);
    // Các phương thức API khác...
}
