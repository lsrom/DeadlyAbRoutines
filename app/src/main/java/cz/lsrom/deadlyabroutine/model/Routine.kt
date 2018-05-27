package cz.lsrom.deadlyabroutine.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Lukas Srom <lukas.srom@ahead-itec.com>
 */
data class Routine(
    val title: String,
    val summary: String,
    val finished: Int,
    val averageTime: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(summary)
        parcel.writeInt(finished)
        parcel.writeInt(averageTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Routine> {
        override fun createFromParcel(parcel: Parcel): Routine {
            return Routine(parcel)
        }

        override fun newArray(size: Int): Array<Routine?> {
            return arrayOfNulls(size)
        }
    }
}