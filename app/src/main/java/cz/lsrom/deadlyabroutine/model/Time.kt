package cz.lsrom.deadlyabroutine.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
data class Time(var minutes: Int, var seconds: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(minutes)
        parcel.writeInt(seconds)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Time> {
        override fun createFromParcel(parcel: Parcel): Time {
            return Time(parcel)
        }

        override fun newArray(size: Int): Array<Time?> {
            return arrayOfNulls(size)
        }
    }
}