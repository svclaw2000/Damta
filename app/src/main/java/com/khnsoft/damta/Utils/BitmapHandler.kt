package com.khnsoft.damta.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class BitmapHandler {
    companion object {
        fun bitmapFromBase64(str: String) : Bitmap {
            val decodedImg = Base64.decode(str, Base64.NO_WRAP)
            return BitmapFactory.decodeByteArray(decodedImg, 0, decodedImg.size)
        }

        fun bitmapToJpegBase64(bitmap: Bitmap): String {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
        }

        fun bitmapToPngBase64(bitmap: Bitmap): String {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
        }
    }
}