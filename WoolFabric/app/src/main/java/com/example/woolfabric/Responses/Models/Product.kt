package com.example.woolfabric.Responses.Models

import android.os.Parcel
import android.os.Parcelable

data class Product (
    var id:String,
    var pname:String,
    var farmerid:String,
    var updatedate:String,
    var cost:String,
    var leftqty:String,
    var image:String
):Parcelable {
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(pname)
        parcel.writeString(farmerid)
        parcel.writeString(updatedate)
        parcel.writeString(cost)
        parcel.writeString(leftqty)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}