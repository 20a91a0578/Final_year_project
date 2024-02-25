package com.example.woolfabric.Responses.Models

import android.os.Parcel
import android.os.Parcelable

data class Requests (
    var id:String,
    var fromid:String,
    var toid:String,
    var requestsdate:String,
    var woolweight:String,
    var bidprice:String,
    var status:String,
    var content:String,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(fromid)
        parcel.writeString(toid)
        parcel.writeString(requestsdate)
        parcel.writeString(woolweight)
        parcel.writeString(bidprice)
        parcel.writeString(status)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Requests> {
        override fun createFromParcel(parcel: Parcel): Requests {
            return Requests(parcel)
        }

        override fun newArray(size: Int): Array<Requests?> {
            return arrayOfNulls(size)
        }
    }
}