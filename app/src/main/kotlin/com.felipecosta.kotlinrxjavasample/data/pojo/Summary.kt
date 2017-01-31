package com.felipecosta.kotlinrxjavasample.data.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Summary : Serializable, Parcelable {
    @SerializedName("resourceURI")
    var resourceURI: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("type")
    var type: String = ""

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.resourceURI)
        dest.writeString(this.name)
        dest.writeString(this.type)
    }

    constructor(`in`: Parcel) {
        this.resourceURI = `in`.readString()
        this.name = `in`.readString()
        this.type = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Summary> = object : Parcelable.Creator<Summary> {
            override fun createFromParcel(source: Parcel): Summary = Summary(source)
            override fun newArray(size: Int): Array<Summary?> = arrayOfNulls(size)
        }
    }
}