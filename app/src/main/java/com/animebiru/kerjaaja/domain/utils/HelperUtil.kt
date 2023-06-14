package com.animebiru.kerjaaja.domain.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.Locale

object HelperUtil {
    private fun createCustomTempFile(context: Context, fileExtension: String): File {
        val timeStamp = System.currentTimeMillis().toString()
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".$fileExtension", storageDir)
    }

    private fun getFileExtensionFromUri(uri: Uri, contentResolver: ContentResolver): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri))
    }

    fun getFileExtensionFromFile(file: File, toLowercase: Boolean = false): String {
        return file.absolutePath.substring(file.absolutePath.lastIndexOf(".")+1).let {
            if (toLowercase) it.lowercase() else it
        }
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val fileExtension = getFileExtensionFromUri(selectedImg, contentResolver)
        val myFile: File = createCustomTempFile(context, fileExtension ?: "jpeg")

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    fun reduceFileImage(file: File, sizeInBytes: Int): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > sizeInBytes)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
}