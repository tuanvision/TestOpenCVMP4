package com.example.ReadingMediaFile

import aacmvi.AacMviActivity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import com.example.testopencv.R
import androidx.activity.viewModels
import com.example.toast
import kotlinx.android.synthetic.main.activity_reading_media.*

class ReadingMediaActivity : AacMviActivity<ReadingMediaViewState, ReadingMediaViewEffect, ReadingMediaViewEvent, ReadingMediaActVM>(),
    OnDrawListener,
    OnNormalizeTouchListener{

    companion object {
        const val TAG = "ReadingMediaActivity"
        var bitmapHeart : Bitmap? = null

    }
    override val viewModel: ReadingMediaActVM by viewModels()
    val handle =  Handler()

    private fun pushVideoToCanvas(handler: Handler) {
        handler.postDelayed({
            PlaybackMVI.process(viewModel, ReadingMediaViewEvent.NextFrame)
            pushVideoToCanvas(handler)
        }, 30L)
    }

    override fun onStart() {
        super.onStart()
        pushVideoToCanvas(handle)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading_media)

        iv_draw_canvas.setOnDrawListener(this)
        iv_draw_canvas.setOnNormalizeTouchListener(this)

        bt_load_dicom_file.setOnClickListener {
            Log.w(TAG, "setOnClickListener ReadingMP4MVI")
            ReadingMP4MVI.process(viewModel, ReadingMediaViewEvent.LoadingMP4File)

        }
    }



    override fun renderViewState(viewState: ReadingMediaViewState) {
        if (bitmapHeart == null) {
            bitmapHeart = BitmapFactory.decodeResource(this.resources, R.drawable.heart)

        }
        when(viewState.status) {
            ReadingMediaViewStatus.Start -> {
                Log.w(TAG, "Go ReadingMediaViewStatus.Start ${bitmapHeart?.width} ${bitmapHeart?.height}")
                iv_draw_canvas.setCustomImageBitmap(bitmapHeart)
            }

        }
    }

    override fun renderViewEffect(viewEffect: ReadingMediaViewEffect) {
        when (viewEffect) {
            is ReadingMediaViewEffect.ShowToast -> toast(message = viewEffect.message)
        }
    }

    override fun onTouchEvent(view: DrawCanvasView, event: MotionEvent?, ix: Float, iy: Float) {
        Log.w(TAG, "onTouchEvent ${event?.action} ${ix} ${iy}")
//        TODO("Not yet implemented")
    }

    override fun draw(view: DrawCanvasView, canvas: Canvas?) {
//        TODO("Not yet implemented")
    }

}