package com.animebiru.kerjaaja.data.sources.remote

import com.animebiru.kerjaaja.data.sources.remote.dto.CreateProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.GetExistingProjectCategoryDto
import com.animebiru.kerjaaja.data.sources.remote.dto.GetExistingProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.GetProjectByIdDto
import com.animebiru.kerjaaja.data.sources.remote.dto.GetUserByUsernameDto
import com.animebiru.kerjaaja.data.sources.remote.dto.RegisterDto
import com.animebiru.kerjaaja.data.sources.remote.dto.LoginDto
import com.animebiru.kerjaaja.data.sources.remote.dto.UpdateUserProfilePictureDto
import okhttp3.MultipartBody
import org.json.JSONArray
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    //Users Api
    @FormUrlEncoded
    @POST("users/register")
    suspend fun postRegister(
        @Field("username") username: String,
        @Field("fullname") name: String,
        @Field("role") role: String,
        @Field("password") password:String,
        @Field("gender") gender: String
    ): Response<RegisterDto>


    @FormUrlEncoded
    @POST("users/login")
    suspend fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ):Response<LoginDto>

//
//    @FormUrlEncoded
//    @POST("users/verify_password/root")
//    fun verifyPassword(
//        @Field("checked") checked: Boolean
//    ):Response<UserResponse>
//
//    @GET("users/read")
//    fun getAllUsers():Response<UserResponse>
//
    @GET("users/read")
    suspend fun getUserByUsername(
        @Query("username") username: String
    ):Response<GetUserByUsernameDto>

    @Multipart
    @PUT("users/profile_photo/{username}")
    suspend fun changePhoto(
        @Part image: MultipartBody.Part,
        @Path("username") username: String
    ): Response<UpdateUserProfilePictureDto>
//
//    @PUT("users/change_username")
//    fun changeUsername(
//        @Field("username") username: String
//    ):Response<UserResponse>
//
//    @PUT("users/change_password")
//    fun changePassword(
//        @Field("password") password: String
//    ):Response<UserResponse>
//
//    @PUT("users/profile_photo")
//    fun deleteProfilePhoto(
//        @Query("delete") delete: Boolean? = false
//    ):Response<UserResponse>
//
    //Api Project
    @FormUrlEncoded
    @POST("projects/create")
    suspend fun createProject(
        @Field("title") title: String,
        @Field("status") status: String,
        @Field("project_fee") projectEee: Double,
        @Field("deadline") deadline: String,
        @Field("owner_username") ownerUsername: String,
        @Field("region_latitude") regionLatitude: Float,
        @Field("region_longitude") regionLongitude: Float,
        @Field("category_list") categoryList: JSONArray
    ):Response<CreateProjectDto>

    @GET("projects/read")
    suspend fun getAllProjects(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetExistingProjectDto>

    @GET("projects/read")
    suspend fun getProjectsByQuery(
        @Query("title") title: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetExistingProjectDto>

    @GET("/projects/read/history/{username}")
    suspend fun getProjectsByOwner(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetExistingProjectDto>

    @GET("projects/read")
    suspend fun getAllProjectsByCategory(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("category_names") vararg categoryName: String
    ): Response<GetExistingProjectDto>

    @GET("projects/read")
    suspend fun getProjectById(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("id") projectId: String
    ): Response<GetProjectByIdDto>
//
//
//    @PUT("projects/update")
//    fun updateProjects(
//        @Field("status") status: String,
//        @Field("region_latitutde") regionLatitude: Float
//    ):Response<ProjectResponse>
//
//    @DELETE("projects/delete")
//    fun deleteProjects(
//        @Path("id") id: String
//    ):Response<ProjectResponse>
//
//    @GET("projects/read/{id}")
//    fun searchUserById(
//        @Query("id") id: String
//    ):Response<ProjectResponse>
//
    // Api Project Category
//    @Multipart
//    @POST("project_catgories/create")
//    fun createProjectCategory(
//        @Part("name") name: RequestBody,
//        @Part image: MultipartBody.Part
//    ):Response<ProjectCategoryResponse>
//
    @GET("project_categories/read")
    suspend fun getAllProjectCategories(
        @Query("page") page: Int,
        @Query("size") size: Int
    ):Response<GetExistingProjectCategoryDto>
//
//    @GET("project_categories/read/{name}")
//    fun getFilterProjectCategories(
//        @Query("name") name: String
//    ):Response<ProjectCategoryResponse>
//
//    @Multipart
//    @PUT("project_categories/update")
//    fun updateProjectCategories(
//        @Part image: MultipartBody.Part
//    ):Response<ProjectCategoryResponse>
//
//    @DELETE("project_categories/delete")
//    fun deleteProjectCategories(
//        @Path("name") name: String
//    ):Response<ProjectCategoryResponse>
//
//    //Api User Recommendation
//    @FormUrlEncoded
//    @POST("user_recommendation/create")
//    fun createUserRecommendation(
//        @Field("sender_username") senderUsername: String,
//        @Field("receiver_username") receiverUsername: String,
//        @Field("rating") rating: String
//    ):Response<UserRecommendationResponse>
//
//    @GET("user_recommendation/read")
//    fun getAllUserRecommendation():Response<UserRecommendationResponse>
//
//    @GET("user_recommendations/read")
//    fun getFilterUserRecommendation(
//        @Query("sender_username") senderUsername: String,
//        @Query("receiver_username") receiverUsername: String
//    ):Response<UserRecommendationResponse>
//
//    @PUT("user_recommendations/update")
//    fun updateRecommendation(
//        @Query("receiver_username") receiverUsername: String,
//        @Field("rating") rating: Int,
//        @Field("description") description: Int
//    ):Response<UserRecommendationResponse>
//
//    @DELETE("user_recommendations/delete")
//    fun deleteUserRecommendation(
//        @Body request: DeleteRequest
//    ):Response<UserRecommendationResponse>
//
//    @DELETE("user_recommendations/delete")
//    fun deleteReceiverUsername(
//        @Query("receiver_username") receiverUsername: String,
//    ):Response<UserRecommendationResponse>

}



