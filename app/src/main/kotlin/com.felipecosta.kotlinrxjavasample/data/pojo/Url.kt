package com.felipecosta.kotlinrxjavasample.data.pojo

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Url : Serializable, Parcelable {
    @SerializedName("type")
    var type: String = ""
    @SerializedName("url")
    var url: String = ""

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.type)
        dest.writeString(this.url)
    }

    constructor() {}

    constructor(`in`: Parcel) {
        this.type = `in`.readString()
        this.url = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Url> = object : Parcelable.Creator<Url> {
            override fun createFromParcel(source: Parcel): Url = Url(source)
            override fun newArray(size: Int): Array<Url?> = arrayOfNulls(size)
        }
    }
}
