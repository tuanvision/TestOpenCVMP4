package com.example.ReadingMediaFile

import android.graphics.Bitmap
import com.example.repositories.RenderMP4FrameObject


data class ReadingMediaViewState(
    val status: ReadingMediaViewStatus,
    val bitmaps: List<Bitmap>,
)

sealed class ReadingMediaViewStatus {
    object Start: ReadingMediaViewStatus()
    object ReadingMP4Sucess : ReadingMediaViewStatus()

}


sealed class ReadingMediaViewEffect {
    data class RenderMP4Frame(val rmf: RenderMP4FrameObject) : ReadingMediaViewEffect()
    data class ShowToast(val message: String) : ReadingMediaViewEffect()

}

sealed class ReadingMediaViewEvent {
    object LoadingMP4File: ReadingMediaViewEvent()
    object NextFrame : ReadingMediaViewEvent()
}


