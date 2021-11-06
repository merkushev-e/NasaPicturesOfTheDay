package ru.gb.lesson1.ui.main.model.pictureOfTheDayApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NasaServerResponseData(
    val copyright: String?,
    val date: String?,
    val explanation: String?,
    val mediaType: String?,
    val title: String?,
    val url: String?,
    val hdurl: String?

): Parcelable
