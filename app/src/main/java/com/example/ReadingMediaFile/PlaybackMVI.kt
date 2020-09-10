/*
 * Copyright 2020 UET-AILAB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ReadingMediaFile

import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.android.synthetic.main.activity_reading_media.*

class PlaybackMVI {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: PlaybackMVI? = null

        const val TAG = "PlaybackMVI"
        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: PlaybackMVI()
                        .also { instance = it }
            }

        fun process(ReadingMediaActVM: ReadingMediaActVM, ReadingMediaViewEvent: ReadingMediaViewEvent) {
            getInstance().process(ReadingMediaActVM, ReadingMediaViewEvent)
        }

        fun renderViewState(ReadingMediaActivity: ReadingMediaActivity, viewState: ReadingMediaViewState) {
            getInstance().renderViewState(ReadingMediaActivity, viewState)
        }

        fun renderViewEffect(ReadingMediaActivity: ReadingMediaActivity, viewEffect: ReadingMediaViewEffect) {
            getInstance().renderViewEffect(ReadingMediaActivity, viewEffect)
        }

    }

    private fun renderViewEffect(readingMediaActivity: ReadingMediaActivity, viewEffect: ReadingMediaViewEffect) {
        when (viewEffect) {
            is ReadingMediaViewEffect.RenderMP4Frame -> {
                renderReadingMediaFrame(readingMediaActivity, viewEffect)
            }
        }
    }

    fun renderReadingMediaFrame(readingMediaActivity: ReadingMediaActivity, renderMP4Frame: ReadingMediaViewEffect.RenderMP4Frame) {
        Log.w(TAG, "renderReadingMediaFrame")
        if (renderMP4Frame.rmf.numFrame > 0) {
            readingMediaActivity.iv_draw_canvas.setCustomImageBitmap(renderMP4Frame.rmf.bitmap)
        }
    }

    private fun renderButtonPlayPause(ReadingMediaActivity: ReadingMediaActivity, button: Int, isPlaying: Boolean) {
//        if (ReadingMediaActivity.bitmapPlay == null) ReadingMediaActivity.bitmapPlay = BitmapFactory.decodeResource(ReadingMediaActivity.resources, R.drawable.ic_play)
//        if (ReadingMediaActivity.bitmapPause == null) ReadingMediaActivity.bitmapPause = BitmapFactory.decodeResource(ReadingMediaActivity.resources, R.drawable.ic_pause)
//
//        if (button == R.id.bt_play_pause)
//            ReadingMediaActivity.bt_play_pause.setImageBitmap(if (isPlaying) ReadingMediaActivity.bitmapPause else ReadingMediaActivity.bitmapPlay)
    }

    private fun renderViewState(ReadingMediaActivity: ReadingMediaActivity, viewState: ReadingMediaViewState) {

    }

    fun process(ReadingMediaActVM: ReadingMediaActVM, ReadingMediaViewEvent: ReadingMediaViewEvent) {

        ReadingMediaActVM.viewStates().value?.let {
            ReadingMediaActVM.reduce(PlaybackReducer(ReadingMediaActVM, it, ReadingMediaViewEvent))
        }

    }

    inner class PlaybackReducer(viewModel: ReadingMediaActVM, viewState: ReadingMediaViewState, val viewEvent: ReadingMediaViewEvent)
        : ReadingMediaActReducer(viewModel, viewState, viewEvent) {

        private val numFrame get() = viewModel.numFrame
        private val isPlaying get() = viewModel.getIsPlaying()
        private val currentFrameIndex get() = viewModel.getCurrentFrameIndex()

        override fun reduce(): StateEffectObject {
            when(viewEvent) {

//                is ReadingMediaViewEvent.PlayPauseVideo -> {
//                    playPauseVideo()
//                    return StateEffectObject(null, null)
//                }

                is ReadingMediaViewEvent.NextFrame -> {
                    return playNextFrame()
                }

//
//                is ReadingMediaViewEvent.ShowNextFrame -> {
//                    Log.w(TAG, "ReadingMediaViewEvent.ShowNextFrame")
//                    return showNextFrame()
//                }
//
//                is ReadingMediaViewEvent.ShowPreviousFrame -> {
//                    Log.w(TAG, "ReadingMediaViewEvent.ShowPreviousFrame")
//                    return showPreviousFrame()
//                }
//
//                is ReadingMediaViewEvent.ShowFirstFrame -> {
//                    Log.w(TAG, "ReadingMediaViewEvent.ShowFirstFrame")
//                    return showFirstFrame()
//                }
//
//                is ReadingMediaViewEvent.ShowLastFrame -> {
//                    Log.w(TAG, "ReadingMediaViewEvent.ShowLastFrame")
//                    return showLastFrame()
//                }
//
//                is ReadingMediaViewEvent.ShowEsvEdvOrReadingMediaFrame -> {
//                    Log.w(TAG, "ReadingMediaViewEvent.ShowEsvEdvOrReadingMediaFrame ${viewEvent.isEsvEdv}")
//                    return showEsvEdvOrReadingMediaFrame(viewEvent.isEsvEdv)
//                }
                else -> return StateEffectObject()
            }

        }

        private fun playNextFrame(): StateEffectObject {
            if (numFrame > 0 && isPlaying == true) {

                val nextFrame = (currentFrameIndex + 1) % numFrame

                viewModel.setCurrentFrameIndex(nextFrame)
                return StateEffectObject(null, viewModel.getRenderReadingMediaFrame())
            }
            return StateEffectObject()

        }

        private fun showFirstFrame(): StateEffectObject {
            if (numFrame > 0) {
                viewModel.setCurrentFrameIndex(0)
                viewModel.setIsPlaying(isPlayingValue = false)
                return StateEffectObject(null, viewModel.getRenderReadingMediaFrame())
            }
            return StateEffectObject()
        }

        private fun showLastFrame() : StateEffectObject{
            if (numFrame > 0) {
                viewModel.setCurrentFrameIndex(numFrame - 1)
                viewModel.setIsPlaying(isPlayingValue = false)
                return StateEffectObject(null, viewModel.getRenderReadingMediaFrame())
            }
            return StateEffectObject()
        }

        private fun showNextFrame(): StateEffectObject {
            if (numFrame > 0) {
                viewModel.setIsPlaying(isPlayingValue = false)
                viewModel.setCurrentFrameIndex((currentFrameIndex + 1) % numFrame)
//                Log.w(TAG, "showNextFrame $isPlaying $currentFrameIndex")
                return StateEffectObject(null, viewModel.getRenderReadingMediaFrame())
            }
            return StateEffectObject()
        }

        private fun showPreviousFrame(): StateEffectObject {
            if (numFrame > 0) {
                viewModel.setIsPlaying(isPlayingValue = false)
                val frameIdx = if (currentFrameIndex <= 0) numFrame - 1 else currentFrameIndex - 1
                viewModel.setCurrentFrameIndex(frameIdx)
                return StateEffectObject(null, viewModel.getRenderReadingMediaFrame())
            }
            return StateEffectObject()
        }

        private fun playPauseVideo() {
            viewModel.setIsPlaying(isPlayingValue = !isPlaying)
        }

    }

}