package com.example.android_mp4_download


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import java.io.File
import java.io.FileOutputStream
import java.net.URL



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoUrl = "https://test-pvg-video-contents-bucket.s3.ap-northeast-1.amazonaws.com/flower.mp4"
        val fileName = "video.mp4"

        downloadVideoFromUrl(videoUrl, fileName)
    }

    private fun downloadVideoFromUrl(url: String, fileName: String) {
        Thread {
            try {
                val connection = URL(url).openConnection()
                val inputStream = connection.getInputStream()

                val outputStream = FileOutputStream(File(filesDir, fileName))
                val buffer = ByteArray(1024)
                var bytesRead: Int

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                outputStream.close()
                inputStream.close()

                runOnUiThread {
                    println("動画をダウンロードしました")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    // ここに保存される
    // /data/data/com.example.android_mp4_download/files/


    // こっちは外部ストレージに保存　権限を色々付与する必要がある。
//    private fun downloadVideoFromUrl(url: String, fileName: String) {
//        Thread {
//            try {
//                val connection = URL(url).openConnection()
//                val inputStream = connection.getInputStream()
//
//                val outputStream = FileOutputStream(getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath + "/$fileName")
//                val buffer = ByteArray(1024)
//                var bytesRead: Int
//
//                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//                    outputStream.write(buffer, 0, bytesRead)
//                }
//
//                outputStream.close()
//                inputStream.close()
//
//                runOnUiThread {
//                    println("動画をダウンロードしました")
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }.start()
//    }
}