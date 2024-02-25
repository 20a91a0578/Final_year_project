package com.example.woolfabric.Responses.Models

import android.os.Parcel
import android.os.Parcelable

data class Information(
                    var id:String,
                  var type:String,
                  var title:String,
                    var path:String,
                  var potray:String,
                    var youtubelink:String,
                  var uploaddate:String,) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun describeContents(): Int {
        return this.describeContents()
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(id)
        p0.writeString(type)
        p0.writeString(title)
        p0.writeString(path)
        p0.writeString(potray)
        p0.writeString(youtubelink)
        p0.writeString(uploaddate)
    }




    companion object CREATOR : Parcelable.Creator<Information> {
        override fun createFromParcel(parcel: Parcel): Information {
            return Information(parcel)
        }

        override fun newArray(size: Int): Array<Information?> {
            return arrayOfNulls(size)
        }
    }
}