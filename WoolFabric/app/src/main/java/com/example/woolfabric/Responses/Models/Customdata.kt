package com.example.woolfabric.Responses.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
data class Customdata(
    @SerializedName("woolweight"   ) var woolweight   : String? = null,
    @SerializedName("bidprice"     ) var bidprice     : String? = null,
    @SerializedName("content"      ) var content      : String? = null,
    @SerializedName("requestsdate" ) var requestsdate : String? = null,
    @SerializedName("name"         ) var name         : String? = null,
    @SerializedName("mobile"       ) var mobile       : String? = null,
    @SerializedName("image"        ) var image        : String? = null,
    @SerializedName("id"           ) var id           : String? = null):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(woolweight)
        parcel.writeString(bidprice)
        parcel.writeString(content)
        parcel.writeString(requestsdate)
        parcel.writeString(name)
        parcel.writeString(mobile)
        parcel.writeString(image)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Customdata> {
        override fun createFromParcel(parcel: Parcel): Customdata {
            return Customdata(parcel)
        }

        override fun newArray(size: Int): Array<Customdata?> {
            return arrayOfNulls(size)
        }
    }
}