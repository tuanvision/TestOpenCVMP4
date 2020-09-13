package com.example.ReadingMediaFile

import android.app.Application
import aacmvi.AacMviViewModel
import android.graphics.Bitmap
import android.util.Log
import com.example.repositories.RenderMP4FrameObject


class ReadingMediaActVM(applicationAnnotate: Application) : AacMviViewModel<ReadingMediaViewState, ReadingMediaViewEffect, ReadingMediaViewEvent>(applicationAnnotate) {

    companion object {
        const val TAG = "ReadingMediaActVM"
    }

    init {
        viewState = ReadingMediaViewState(
            status = ReadingMediaViewStatus.Start,
            bitmaps = emptyList()
        )
    }

    val numFrame get() =  viewState.bitmaps.size

    private var currentFrameIndex: Int = 0
    private var isPlaying: Boolean = true



    override fun process(viewEvent: ReadingMediaViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {

        }
    }

    fun reduce(reducer: ReadingMediaActReducer) {

        val result = reducer.reduce()
//        Log.w(TAG, "Go reduce VM ${result}")
        result.viewState?.let { viewState = it }
        result.viewEffect?.let { viewEffect = it }
    }

    fun reduce(stateEffectObject: StateEffectObject) {

        stateEffectObject.viewState?.let { viewState = it }
        stateEffectObject.viewEffect?.let { viewEffect = it }
    }
    fun getIsPlaying(): Boolean {
        return isPlaying
    }

    fun getCurrentFrameIndex(): Int {
        return currentFrameIndex
    }

    fun setCurrentFrameIndex(nextFrame: Int) {
        currentFrameIndex = nextFrame
    }

    fun setIsPlaying(isPlayingValue: Boolean) {
        isPlaying = isPlayingValue
    }

    fun getRenderReadingMediaFrame(): ReadingMediaViewEffect? {
        return if (currentFrameIndex >= 0 && currentFrameIndex < numFrame) {
            val rmf = RenderMP4FrameObject(numFrame = viewState.bitmaps.size, bitmap = getCurrentFrameBitmap(), idFrame = currentFrameIndex)
            return ReadingMediaViewEffect.RenderMP4Frame(rmf)
        } else null
    }

    private fun getCurrentFrameBitmap(): Bitmap {
        return viewState.bitmaps.get(currentFrameIndex)
    }
}

