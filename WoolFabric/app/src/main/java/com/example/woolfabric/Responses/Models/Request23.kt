package com.example.woolfabric.Responses.Models

import com.google.gson.annotations.SerializedName

data class Request23 (
    @SerializedName("id")val id:String?=null,
    @SerializedName("woolenprocessing")val woolenprocessing:String?=null,
    @SerializedName("requestid")val requestid:String?=null,
    @SerializedName("scouring")val scouring:String?=null,
    @SerializedName("carding")val carding:String?=null,
    @SerializedName("spinning")val spinning:String?=null,
)