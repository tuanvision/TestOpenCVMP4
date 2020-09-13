package com.example.repositories

import android.graphics.Bitmap

data class RenderMP4FrameObject(
    val numFrame: Int,
    val idFrame: Int,
    val bitmap: Bitmap
)