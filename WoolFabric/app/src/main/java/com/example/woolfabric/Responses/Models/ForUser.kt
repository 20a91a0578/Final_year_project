package com.example.woolfabric.Responses.Models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ForUser (
    var image:String,
    var name:String,
    var mobile:String,
    var mail:String,
    var orderid:String,
    var ordereddate:String,
    var paymentstatus:String
)