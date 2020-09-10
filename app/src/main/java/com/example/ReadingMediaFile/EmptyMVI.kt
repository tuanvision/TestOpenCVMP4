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

import android.util.Log

class EmptyMVI {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: EmptyMVI? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: EmptyMVI()
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

    private fun renderViewEffect(ReadingMediaActivity: ReadingMediaActivity, viewEffect: ReadingMediaViewEffect) {
    }

    private fun renderViewState(ReadingMediaActivity: ReadingMediaActivity, viewState: ReadingMediaViewState) {
    }

    fun process(readingMediaActVM: ReadingMediaActVM, readingMediaViewEvent: ReadingMediaViewEvent) {

        when (readingMediaViewEvent) {

        }
    }

    inner class Reducer(viewModel: ReadingMediaActVM, viewState: ReadingMediaViewState, val viewEvent: ReadingMediaViewEvent)
        : ReadingMediaActReducer(viewModel, viewState, viewEvent) {
        override fun reduce(): StateEffectObject {

            return StateEffectObject()
        }

    }

    inner class ReducerAsync(viewModel: ReadingMediaActVM, viewState: ReadingMediaViewState, val viewEvent: ReadingMediaViewEvent) : ReadingMediaActReducer(viewModel, viewState, viewEvent) {

        override fun reduce(): StateEffectObject {
            return StateEffectObject()
        }

    }
}