package com.animebiru.kerjaaja.data.source.remote

import com.animebiru.kerjaaja.data.source.remote.response.DeleteRequest
import com.animebiru.kerjaaja.data.source.remote.response.ProjectCategoryResponse
import com.animebiru.kerjaaja.data.source.remote.response.ProjectResponse
import com.animebiru.kerjaaja.data.source.remote.response.UserRecommendationResponse
import com.animebiru.kerjaaja.data.source.remote.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {


    //Users Api
    @FormUrlEncoded
    @POST("/users/register")
    fun postRegister(
        @Field("username") username: String,
        @Field("name") name: String,
        @Field("gender") gender: String,
        @Field("password") password:String ,
        @Field("role") role: String
    ):Call<UserResponse>

    @FormUrlEncoded
    @POST("/users/login")
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ):Call<UserResponse>

    @FormUrlEncoded
    @POST("/users/verify_password/root")
    fun verifyPassword(
        @Field("checked") checked: Boolean
    ):Call<UserResponse>

    @GET("/users/read")
    fun getAllUsers():Call<UserResponse>

    @GET("users/read/{username}")
    fun getFilterUsers(
        @Path("username") username:String
    ):Call<UserResponse>

    @Multipart
    @PUT("/users/profile_photo")
    fun changePhoto(
        @Part("file") file: MultipartBody.Part?,
        @PartMap data: Map<String,RequestBody>
    ):Call<UserResponse>

    @PUT("/users/change_username")
    fun changeUsername(
        @Field("username") username: String
    ):Call<UserResponse>

    @PUT("/users/change_password")
    fun changePassword(
        @Field("password") password: String
    ):Call<UserResponse>

    @PUT("/users/profile_photo")
    fun deleteProfilePhoto(
        @Query("delete") delete: Boolean? = false
    ):Call<UserResponse>

    //Api Project
    @FormUrlEncoded
    @POST("/projects/create")
    fun createProject(
        @Field("title") title: String,
        @Field("status") status: String,
        @Field("project_fee") projectEee: Int,
        @Field("deadline") deadline: String,
        @Field("owner_username") ownerUsername: String,
        @Field("region_latitude") regionLatitude: Float,
        @Field("region_longitude") regionLongitude: Float
    ):Call<ProjectResponse>

    @GET("/projects/read")
    fun getAllProjects():Call<ProjectResponse>


    @PUT("/projects/update")
    fun updateProjects(
        @Field("status") status: String,
        @Field("region_latitutde") regionLatitude: Float
    ):Call<ProjectResponse>

    @DELETE("/projects/delete")
    fun deleteProjects(
        @Path("id") id: String
    ):Call<ProjectResponse>

    @GET("/projects/read/{id}")
    fun searchUserById(
        @Query("id") id: String
    ):Call<ProjectResponse>

    // Api Project Category
    @Multipart
    @POST("/project_catgories/create")
    fun createProjectCategory(
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part
    ):Call<ProjectCategoryResponse>

    @GET("/project_categories/read")
    fun getAllProjectCategories():Call<ProjectCategoryResponse>

    @GET("/project_categories/read/{name}")
    fun getFilterProjectCategories(
        @Query("name") name: String
    ):Call<ProjectCategoryResponse>

    @Multipart
    @PUT("/project_categories/update")
    fun updateProjectCategories(
        @Part image: MultipartBody.Part
    ):Call<ProjectCategoryResponse>

    @DELETE("/project_categories/delete")
    fun deleteProjectCategories(
        @Path("name") name: String
    ):Call<ProjectCategoryResponse>

    //Api User Recommendation
    @FormUrlEncoded
    @POST("/user_recommendation/create")
    fun createUserRecommendation(
        @Field("sender_username") senderUsername: String,
        @Field("receiver_username") receiverUsername: String,
        @Field("rating") rating: String
    ):Call<UserRecommendationResponse>

    @GET("/user_recommendation/read")
    fun getAllUserRecommendation():Call<UserRecommendationResponse>

    @GET("/user_recommendations/read")
    fun getFilterUserRecommendation(
        @Query("sender_username") senderUsername: String,
        @Query("receiver_username") receiverUsername: String
    ):Call<UserRecommendationResponse>

    @PUT("/user_recommendations/update")
    fun updateRecommendation(
        @Query("receiver_username") receiverUsername: String,
        @Field("rating") rating: Int,
        @Field("description") description: Int
    ):Call<UserRecommendationResponse>

    @DELETE("/user_recommendations/delete")
    fun deleteUserRecommendation(
        @Body request: DeleteRequest
    ):Call<UserRecommendationResponse>

    @DELETE("/user_recommendations/delete")
    fun deleteReceiverUsername(
        @Query("receiver_username") receiverUsername: String,
    ):Call<UserRecommendationResponse>

}



