package com.khnsoft.damta

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.khnsoft.damta.data.Image
import kotlinx.android.synthetic.main.frag_register_area6.*
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

class RegisterArea6Fragment : Fragment() {
    var curImage : Bitmap? = null

    companion object {
        const val RC_CAMERA = 100
        const val RC_GALLARY = 101
        val ROOT_DIR = "${Environment.getExternalStorageDirectory()}/Hicarcom"
        val TEMP_DIR = "${ROOT_DIR}/.TEMP"
        val TEMP_NAME = "temp_image"

        var frag: RegisterArea6Fragment? = null

        fun getInstance() : RegisterArea6Fragment {
            if (frag == null) {
                frag = RegisterArea6Fragment().apply { arguments = Bundle() }
            }
            return frag!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_register_area6, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_image.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("이미지 추가")
                .setItems(arrayOf("갤러리", "직접 촬영")) { dialog, which ->
                    when (which) {
                        0 -> {
                            val intent = Intent(Intent.ACTION_PICK)
                            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
                            startActivityForResult(intent, RC_GALLARY)
                        }
                        1 -> {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            if (intent.resolveActivity(context!!.packageManager) != null) {
                                try {
                                    val dir = File(TEMP_DIR)
                                    dir.mkdirs()

                                    val file = File(TEMP_DIR, TEMP_NAME)
                                    if (file.exists()) {
                                        file.delete()
                                    }

                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context!!, "${context?.packageName}.provider", file))
                                    startActivityForResult(intent, RC_CAMERA)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                }
                .create()
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val inputStream = FileInputStream(File(TEMP_DIR, TEMP_NAME))
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()
                        if (bitmap != null) {
                            val matrix = Matrix()
                            matrix.postRotate(90f)
                            showImage(Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            RC_GALLARY -> {
                if (data == null) return
                val photoUri = data.data
                var cursor: Cursor? = null
                val tempFile: File?

                try {
                    val proj = arrayOf(MediaStore.Images.Media.DATA)

                    cursor = context!!.contentResolver.query(photoUri ?: return, proj, null, null, null)

                    val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) ?: return

                    cursor.moveToFirst()
                    tempFile = File(cursor.getString(column_index))
                } finally {
                    cursor?.close()
                }

                val options = BitmapFactory.Options()
                val img = BitmapFactory.decodeFile(tempFile?.absolutePath ?: return, options)
                showImage(img)
            }
        }
    }

    fun showImage(bitmap: Bitmap?) {
        curImage = bitmap
        iv_image.setImageBitmap(bitmap)
    }
}