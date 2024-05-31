package com.sj.trinitywizassesment.model

import android.os.Parcel
import android.os.Parcelable

data class Contact(
    val id: String,
    val firstName: String,
    val lastName: String?,
    val email: String?,
    val dob: String?
) : Parcelable {

    // Computed property to get initials from first and last name
    val initials: String
        get() {
            val firstInitial = firstName.take(1)
            val lastInitial = lastName?.take(1) ?: ""
            return "$firstInitial$lastInitial"
        }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(dob)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}
