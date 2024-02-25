package com.example.woolfabric.Responses.Models

import android.os.Parcel
import android.os.Parcelable

data class User (
    var id:String,
    var name:String,
    var mobile:String,
    var mail:String,
    var password:String,
    var type:String,
var image:String,
    var count:String):Parcelable{
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
        parcel.writeString(name)
        parcel.writeString(mobile)
        parcel.writeString(mail)
        parcel.writeString(password)
        parcel.writeString(type)
        parcel.writeString(image)
        parcel.writeString(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}