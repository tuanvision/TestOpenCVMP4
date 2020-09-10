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
import androidx.lifecycle.viewModelScope
import com.example.repositories.FolderRepository
import kotlinx.coroutines.launch

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
//                Log.w("")

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
            var result = StateEffectObject()
            Log.w(TAG, "Go ReducerLoadingMP4File")
            viewModel.viewModelScope.launch {

                val fileMP4 = folderRepository.getFileMP4()
                //
//                val study = folderRepository.getStudyInformation()

                Log.w(TAG, "fileMP4: ${fileMP4} len bitmaps: ${fileMP4.data.bitmaps.size}")

                if (fileMP4.error == false) {
                    result = StateEffectObject(
                        viewState.copy(status = ReadingMediaViewStatus.ReadingMP4Sucess, bitmaps = fileMP4.data.bitmaps),
                        viewEffect = ReadingMediaViewEffect.ShowToast("Done Reading MP4 file")
                    )
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