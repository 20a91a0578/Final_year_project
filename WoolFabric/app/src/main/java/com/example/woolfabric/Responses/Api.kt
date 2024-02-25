package com.example.woolfabric.Responses

import android.util.Log
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
@FormUrlEncoded
@POST("signup.php")
fun signup(
    @Field("name")name:String,
    @Field("mail")mail:String,
    @Field("password")password:String,
    @Field("mobile")mobile:String
): Call<CommonResponse>

@FormUrlEncoded
@POST("functions.php")
    fun login(
    @Field("mail")mail:String,
    @Field("password")password:String
):Call<LoginResponse>

    @Multipart
    @POST("addinformation.php")
    fun addinfo(
        @Part file: MultipartBody.Part,
        @Part("type")type:RequestBody,
        @Part("title")title:RequestBody,
        @Part("potray")potray:RequestBody,
        @Part("youtubelink")youtubelink:RequestBody,
        @Part("uploaddate")uploaddate:RequestBody,
                    ):Call<CommonResponse>
@FormUrlEncoded
@POST("functions.php")
    fun gettradeprice(

    @Field("condition")condition:String
    ):Call<PriceResponse>
@FormUrlEncoded
@POST("updatefun.php")
    fun addprice(
    @Field("today")today:String,
    @Field("price")price:String,
    @Field("percentage")percentage:String
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("mainusers.php")
    fun addfarmers(
        @Field("name")name:String,
        @Field("mobile")mobile:String,
        @Field("email")email:String,
        @Field("password")password:String,
        @Field("count")count:String,
        @Field("image")image:String,
        @Field("type")type:String,
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getperosons(
@Field("condition")condition:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getdata(
        @Field("condition")condition:String
    ):Call<DoubleResponse>

    @FormUrlEncoded
    @POST("addproduct.php")
    fun addproduct(
        @Field("pname")pname:String,
        @Field("farmerid")farmerid:String,
        @Field("updatedate")updatedate:String,
        @Field("cost")cost:String,
        @Field("leftqty")leftqty:String,
        @Field("image")image:String
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getProducts(
        @Field("condition")condition:String,
        @Field("id")id:String
    ):Call<CustomResponse>

    @FormUrlEncoded
    @POST("addproduct.php")
    fun updateproduct(
        @Field("condition")condition:String,
        @Field("farmerid")farmerid:String,
        @Field("updatedate")updatedate:String,
        @Field("cost")cost:String,
        @Field("leftqty")leftqty:String,
        @Field("id")id:String
        ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getFarmerProducts(
        @Field("condition")condition:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getboth(
        @Field("condition")condition:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("order.php")
    fun addorders(
        @Field("farmerid")farmerid:String,
        @Field("userid")userid:String,
        @Field("productid")productid:String,
        @Field("cost")cost:String,
        @Field("qty")qty:String,
        @Field("ordereddate")ordereddate:String,
        @Field("orderid")orderid:String
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun viewData(
        @Field("condition")condition:String,
        @Field("id")id:String
    ):Call<UsersOrders>

    @FormUrlEncoded
    @POST("functions.php")
     fun getuserproducts(
        @Field("condition")condition: String,
        @Field("id")id: String):Call<CustomReponse>

     @FormUrlEncoded
     @POST("functions.php")
    fun getIndustires(
        @Field("condition")condition:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("sendrequest.php")
    fun sendrequets(
        @Field("farmerid")farmerid:String,
        @Field("indutryid")indutryid:String,
        @Field("requestsdate")requestsdate:String,
        @Field("woolweight")woolweight:String,
        @Field("bidprice")bidprice:String,
        @Field("status")status:String,
        @Field("content")content:String,
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getrequest(
        @Field("condition")condition:String,
        @Field("id")id:String
    ):Call<RequestResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getuserid(
        @Field("condition")condition: String,
        @Field("id")id:String
    ):Call<LoginResponse>
@FormUrlEncoded
@POST("updatefun.php")
    fun updatefun(
        @Field("condition")condition:String,
        @Field("id")id:String
    ):Call<CommonResponse>

    @FormUrlEncoded

    @POST("updateprofile.php")
    fun updateprofile(
        @Field("name")name:String,
        @Field("mobile")mobile:String,
        @Field("mail")mail:String,
        @Field("password")password:String,
        @Field("count")count:String,
        @Field("id")id:String
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("functions.php")
    fun getcount(
    @Field("condition")condition:String
    ):Call<CountResponse>



    @FormUrlEncoded
    @POST("addprocessing.php")
    fun processing(
@Field("requestid") requestid:String,
@Field("woolenprocessing") woolenprocessing:String,
@Field("scouring") scouring:String,
@Field("carding") carding:String,
@Field("spinning") spinning:String,
@Field("dateof") dateof:String,
    ):Call<CommonResponse>


    @FormUrlEncoded
    @POST("functions.php")
    fun getmycompletedlist(
        @Field("condition")condition:String,
        @Field("id")id:String
    ):Call<CustomeResponse2>

    @FormUrlEncoded
    @POST("functions.php")
    fun getreport(
        @Field("condition")condition:String,
        @Field("id")id:String,
        @Field("dateof")dateof:String
    ):Call<ReportResponse>
}