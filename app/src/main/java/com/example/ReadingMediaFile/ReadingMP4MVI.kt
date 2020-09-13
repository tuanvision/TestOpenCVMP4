package com.example.ReadingMediaFile

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

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.repositories.FolderRepository
import kotlinx.coroutines.launch
import org.opencv.android.Utils
import com.example.toast

class ReadingMP4MVI {


    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: ReadingMP4MVI? = null


        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: ReadingMP4MVI()
                        .also { instance = it }
            }

        const val TAG = "ReadingMP4MVI"

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

    private val folderRepository: FolderRepository = FolderRepository.getInstance()


    private fun renderViewEffect(readingMediaActivity: ReadingMediaActivity, viewEffect: ReadingMediaViewEffect) {
    }

    private fun renderViewState(readingMediaActivity: ReadingMediaActivity, viewState: ReadingMediaViewState) {
        when(viewState.status) {
            ReadingMediaViewStatus.ReadingMP4Sucess -> {

//                Toast.makeText(viewS, "", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun process(readingMediaActVM: ReadingMediaActVM, readingMediaViewEvent: ReadingMediaViewEvent) {

        when (readingMediaViewEvent) {
            ReadingMediaViewEvent.LoadingMP4File -> {

                readingMediaActVM.viewStates().value?.let {
                    Log.w(TAG, "GO process ReadingMediaViewEvent.LoadingMP4File")
                    readingMediaActVM.reduce(ReducerLoadingMP4File(readingMediaActVM, it, ReadingMediaViewEvent.LoadingMP4File))
                }
            }
        }
    }

    inner class ReducerLoadingMP4File(viewModel: ReadingMediaActVM, viewState: ReadingMediaViewState, val viewEvent: ReadingMediaViewEvent.LoadingMP4File)
        : ReadingMediaActReducer(viewModel, viewState, viewEvent) {

        override fun reduce(): StateEffectObject {

            if (viewState.status is ReadingMediaViewStatus.OnReadingMP4) {
                return StateEffectObject(null, null)
            }

            val result = StateEffectObject(viewState=viewState.copy(status = ReadingMediaViewStatus.OnReadingMP4))

            Log.w(TAG, "Go ReducerLoadingMP4File")

            viewModel.viewModelScope.launch {

                val fileMP4 = folderRepository.extractorMP4()
                //
//                val study = folderRepository.getStudyInformation()

                Log.w(TAG, "fileMP4: ${fileMP4.data.dicomPath} error: ${fileMP4.error} len bitmaps: ${fileMP4.data.bitmaps.size}")

                if (fileMP4.error == false) {
                    viewModel.reduce(StateEffectObject(
                        viewState.copy(status = ReadingMediaViewStatus.ReadingMP4Sucess, bitmaps = fileMP4.data.bitmaps),
                        viewEffect = ReadingMediaViewEffect.ShowToast("Done Reading MP4 file")
                    ))
                }
            }

            return result
        }

    }

    inner class ReducerAsync(viewModel: ReadingMediaActVM, viewState: ReadingMediaViewState, val viewEvent: ReadingMediaViewEvent) : ReadingMediaActReducer(viewModel, viewState, viewEvent) {

        override fun reduce(): StateEffectObject {
            return StateEffectObject()
        }

    }
}