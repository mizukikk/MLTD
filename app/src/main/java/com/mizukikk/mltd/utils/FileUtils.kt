package com.mizukikk.mltd.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.mizukikk.mltd.MLTDApplication
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    private const val PICTURI_DIR_NAME = "gemcard"
    private const val SAVE_MIME_TYPE = "image/png"

    @Throws(IOException::class)
    fun saveImage(bitmap: Bitmap, fileName: String) {
        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        val context = MLTDApplication.appContext
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveBitmap(
                context,
                bitmap,
                Bitmap.CompressFormat.PNG,
                SAVE_MIME_TYPE,
                "${fileName}_${timeStamp}"
            )
        } else {
            val dir =
                Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_PICTURES}/$PICTURI_DIR_NAME")
            if (dir.exists().not())
                dir.mkdir()
            val file = File("$dir", "${fileName}_${timeStamp}.png")
            try {
                val fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
                throw IOException("Failed to save bitmap.")
            }
            MediaScannerConnection.scanFile(MLTDApplication.appContext, arrayOf(file.absolutePath), null, null)
        }
    }


    @Throws(IOException::class)
    private fun saveBitmap(
        context: Context, bitmap: Bitmap,
        format: Bitmap.CompressFormat, mimeType: String,
        displayName: String
    ) {
        val relativeLocation = "${Environment.DIRECTORY_PICTURES}/cardcool"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
        val resolver: ContentResolver = context.contentResolver
        var stream: OutputStream? = null
        var uri: Uri? = null
        try {
            val contentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, contentValues)
            if (uri == null) {
                throw IOException("Failed to create new MediaStore record.")
            }
            stream = resolver.openOutputStream(uri)
            if (stream == null) {
                throw IOException("Failed to get output stream.")
            }
            if (bitmap.compress(format, 95, stream).not()) {
                throw IOException("Failed to save bitmap.")
            }
        } catch (e: IOException) {
            if (uri != null) {
                // Don't leave an orphan entry in the MediaStore
                resolver.delete(uri, null, null)
            }
            throw e
        } finally {
            stream?.close()
        }
    }

}