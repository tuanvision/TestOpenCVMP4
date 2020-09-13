package com.example.repositories

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.LCE
import com.example.ReadingMediaFile.ExtractMpegFramesTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader


@RequiresApi(Build.VERSION_CODES.N)
class FolderRepository {


    companion object {
        const val TAG = "FolderRepository"

        val DEFAULT_FOLDER_DOWNLOAD = "/storage/emulated/0/Download"

        val SORT_TYPE_NAME_DESC = "name_desc"
        val SORT_TYPE_TIME_DESC = "time_desc"
        val SORT_TYPE_NAME_ASC = "name_asc"
        val SORT_TYPE_TIME_ASC = "time_asc"

        // For Singleton instantiation
        @Volatile
        private var instance: FolderRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: FolderRepository()
                        .also { instance = it }
            }


    }
    suspend fun extractorMP4(): LCE.Result<DicomObject> = withContext(Dispatchers.IO) {
        val extractor = ExtractMpegFramesTest()
        val start = System.currentTimeMillis()

        val fileName = "${DEFAULT_FOLDER_DOWNLOAD}/1.2.40.0.13.0.11.2672.5.2013102492.1340595.20130717095716/1.2.840.113663.1500.1.341642571.3.1.20130717.100712.234____F8IB5HTG.mp4"
        val bitmaps = extractor.extractMpegFrames(fileName)

        val end = System.currentTimeMillis()

        Log.w(TAG, "Time: ${end - start}")
        return@withContext LCE.Result(error =  false, message = "Done Reading MP4 file", data = DicomObject(bitmaps = bitmaps, dicomPath = fileName, tags = JSONObject()))
    }


    suspend fun getFileMP4(): LCE.Result<DicomObject> = withContext(Dispatchers.IO) {
        // {
        //      "StudyInstanceUID": "1.2.40.0.13.0.11.2672.5.2013102492.1340595.20130717095716",
        //      "SOPInstanceUID": "1.2.840.113663.1500.1.341642571.3.1.20130717.100712.234",
        //      "SeriesInstanceUID": "1.2.840.113663.1500.1.341642571.2.1.20130717.100700.656",
        //      "SeriesNumber": "1",
        //      "InstanceNumber": "1",
        //      "InstitutionName": "BV VL",
        //      "AcquisitionDateTime": "20191009111756.000",
        //      "PatientName": "Le Duy Thang",
        //      "ReferringPhysicianName": "BS. Nguyen Vu Ngoc Quyen",
        //      "StudyDescription": "Echokardiographie transthorakal\u0000",
        //      "PixelData": [
        //        75,
        //        600,
        //        800,
        //        3
        //      ],
        //      "SequenceOfUltrasoundRegions": [
        //        {
        //          "PhysicalDeltaX": "0.03228219215117199",
        //          "PhysicalDeltaY": "0.03228219215117199"
        //        }
        //      ],
        //      "NumberOfFrames": "75",
        //      "Rows": "600",
        //      "Columns": "800",
        //      "FrameTime": "20.068",
        //      "HeartRate": "80",
        //      "relative_path": "1.2.840.113663.1500.1.341642571.3.1.20130717.100712.234____F8IB5HTG"
        //    }

        var fileName = "${DEFAULT_FOLDER_DOWNLOAD}/1.2.40.0.13.0.11.2672.5.2013102492.1340595.20130717095716/1.2.840.113663.1500.1.341642571.3.1.20130717.100712.234____F8IB5HTG.mp4"
//        val videoMP4 = VideoCapture().open(fileName)
//        fileName = "${DEFAULT_FOLDER_DOWNLOAD}/new_video.avi"

        val cap = VideoCapture(fileName)
//        cap.open(fileName)
        val framem = Mat()
        Log.w(TAG,"exist: ${File(fileName).exists()} ${cap.isOpened}");
//        if (cap.isOpened) {
//            var cnt = 0
//            while (true) {
//                cnt += 1
//                cap.read(frame)
//                Log.w(TAG,"Load to frame: ${cnt}")
//            }
//        } else {
//            Log.w(TAG, "Video capture failed")
//        }
//
//
//        try {
//
//            var cnt = 0
//            while (true) {
//                cap.read(framem)
//                cnt += 1
//                Log.w(TAG,"Load to frame: ${cnt}")
////                Log.w(TAG,"Load to frame: ${frame.height()} ${frame.width()} ${frame.empty()}")
////
////                if (frame.empty()) {
////                    break
////                }
//
////                if (cnt == 10)
////                    break
//
//
//            }
//        } catch (e: Exception) {
//            Log.w(TAG, "Error load file: ${e}")
//        }

//        val frameGrabber = FFmpegFrameGrabber(fileName)
//        val numFrame = frameGrabber.lengthInVideoFrames
//        frameGrabber.start()
//        val bitmapConverter = AndroidFrameConverter()

//        val fileMP4 = File(fileName)

//        val mmr = MediaMetadataRetriever()
//        mmr.setDataSource(fileName)
//        val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toDouble()
//        val width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).toInt()
//        val height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).toInt()
//        val frame = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT).toInt()
//        val frameRate =  0 // mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE).toDouble()
//        val frameTime = 20.068
//        val start = System.currentTimeMillis()
//
        val arrayListBitmap = ArrayList<Bitmap>()
//        repeat(frame) {
//            val b = mmr.getFrameAtIndex(it)
//            arrayListBitmap.add(b)
//            Log.w(TAG, "Load to :${it}")
//        }
//        val end = System.currentTimeMillis()
//
//        Log.w(TAG, "Read ${frame} in ${end - start}")

//        Log.w(TAG, "frameRate: ${frameRate} duration: ${duration} ft: ${frameTime} nFrame: ${duration / frameTime} width: ${width} ${height} ${frame}")

//        val mmr = FFmpegFrameGrabber(fileName)
//        val mmr = FFmpegMediaMetadataRetriever()
//        mmr.setDataSource(fileName)
//        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM)
//        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST)
//        val duration = mmr.metadata.getDouble("duration")
//        val frameRate = mmr.metadata.getDouble("framerate")
//        val numberOfFrame = (duration/frameRate).toInt()


//        val frame = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_FRAMERATE).toDouble()
////        mmr.frame
//        Log.w(TAG, "FRAME: ${numberOfFrame} frameRate: ${frameRate} duration: ${duration}")
//        print()
//        val b = mmr.getFrameAtTime(
//            2000000,
//            FFmpegMediaMetadataRetriever.OPTION_CLOSEST
//        ) // frame at 2 seconds

//        val artwork = mmr.embeddedPicture

//        mmr.release()


//        return
//
//        for (it in 0..numFrame) {
//            val frame = frameGrabber.grabFrame()
//            if (frame == null) {
//                break
//            }
//            if (frame.image == null) {
//                continue
//            }
//            val bitmap = bitmapConverter.convert(frame)
//
//            bitmap?.let {
//                arrayListBitmap.add(bitmap)
//            }
//            Log.w(TAG, "go to index: ${it} / ${numFrame}")
//        }
//        Log.w(TAG, "time: ${end - start} eachframe: ${(end - start) / numFrame}")
//
        val dicom_tags = JSONObject()
//        dicom_tags.put()
        val results = DicomObject(fileName, dicom_tags, arrayListBitmap)

//        mmr.release() // all done, release the object

        return@withContext LCE.Result(data = results, error = false, message = "no error")
    }

    suspend fun getStudyInformation(fileName: String = "/storage/emulated/0/Download/1.2.40.0.13.0.11.2672.5.2013102492.1340595.20130717095716/1.2.40.0.13.0.11.2672.5.2013102492.1340595.20130717095716.json"): LCE<JSONObject> = withContext(
        Dispatchers.IO
    ) {
        try {
            val inp = InputStreamReader(FileInputStream(fileName))
            val str = inp.readText()
            inp.close()
            return@withContext LCE.Result(
                error = false, message = "Get DATA from Disk Succeed", data = JSONObject(
                    str
                )
            )

        } catch (e: Exception) {
            return@withContext LCE.Result(
                error = true,
                message = "Get DATA from Disk Failed",
                data = JSONObject()
            )
        }

    }


//    suspend fun getListile()

//    suspend fun getSetLatestFolderList(folderPath: String, type: String = SORT_TYPE_NAME_ASC): LCE<List<FolderItem>> = withContext(Dispatchers.IO) {
//
//        val folder = File(folderPath)
//
//        val folderList = sortByKey(
//            folder.listFiles()?.filter{
//                !it.absolutePath.contains(".thumbnail") && !it.absolutePath.contains(".json")
//            }?.map {
//                FolderItem(description = it.absolutePath, name = it.absolutePath, path = it.absolutePath,
//                    modifiedTime = it.lastModified(), isFile = it.isFile, hasAnnotation = File(it.absolutePath+".json").exists())
//            } ?: emptyList(),
//            type = type
//        )
//
//        Log.w("CHECK DATA", "$folderList ${folder.listFiles()}")
//
//        return@withContext if (folderList.isNotEmpty()) {
//            LCE.Result(data = folderList, error = false, message = "no error")
//        } else {
//            LCE.Result(data = emptyList(), error = true, message = "error")
//        }
//    }
//
//    fun sortByKey(folderList: List<FolderItem>, type: String=SORT_TYPE_NAME_ASC): List<FolderItem> {
//        // key ["name", "time"]
//        when (type) {
//            SORT_TYPE_NAME_DESC -> {
//                return folderList.sortedWith(
//                    compareBy<FolderItem>({ it.name }, { it.modifiedTime }).reversed()
//                )
//            }
//            SORT_TYPE_NAME_ASC -> {
//                return folderList.sortedWith(
//                    compareBy<FolderItem>({ it.name }, { it.modifiedTime })
//                )
//            }
//
//            SORT_TYPE_TIME_DESC -> {
//                return folderList.sortedWith(
//                    compareBy<FolderItem>({ it.modifiedTime }, { it.name }).reversed()
//                )
//            }
//            SORT_TYPE_TIME_ASC -> {
//                return folderList.sortedWith(
//                    compareBy<FolderItem>({ it.modifiedTime }, { it.name })
//                )
//            }
//        }
//        return folderList
//    }

}