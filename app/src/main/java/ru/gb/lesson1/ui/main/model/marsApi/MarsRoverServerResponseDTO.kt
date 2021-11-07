package ru.gb.lesson1.ui.main.model.marsApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsRoverServerResponseDTO(
    val latest_photos: List<LatestPhoto>
) : Parcelable {
    @Parcelize
    data class LatestPhoto(
        val camera: CameraRover,
        val img_src: String?,
        val earth_date: String?,
    ): Parcelable{
        @Parcelize
        data class CameraRover(
            val full_name: String?
        ): Parcelable
    }
}
