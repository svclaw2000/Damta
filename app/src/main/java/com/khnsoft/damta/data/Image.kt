package com.khnsoft.damta.data

import android.content.Context
import android.graphics.Bitmap
import com.google.gson.JsonObject
import com.khnsoft.damta.utils.BitmapHandler
import com.khnsoft.damta.utils.DatabaseHandler
import com.khnsoft.damta.utils.SDF
import java.lang.Exception
import java.util.*

class Image(
    var id : Int = -1,
    var area : Area? = null,
    var image : Bitmap? = null,
    var createdDate : Date? = null
) {
    companion object {
        fun getFromJson(context: Context, jImage: JsonObject) : Image {
            return Image(
                id = jImage["id"].asInt,
                area = Area.getById(context, jImage["area_id"].asInt),
                image = BitmapHandler.bitmapFromBase64(jImage["image"].asString),
                createdDate = SDF.dateBar.parse(jImage["created_date"].asString)
            )
        }

        fun getById(context: Context, id: Int) : Image? {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT id, area_id, image, created_date 
                    FROM IMAGE_TB  
                    WHERE id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                if (jResult.size() > 0) {
                    return getFromJson(context, jResult[0].asJsonObject)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return null
        }

        fun getByAreaId(context: Context, id: Int) : Array<Image> {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT id, area_id, image, created_date 
                    FROM IMAGE_TB  
                    WHERE area_id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                val images = Array(jResult.size()) { Image() }

                for (i in 0..jResult.size()-1) {
                    images[i] = getFromJson(context, jResult[i].asJsonObject)
                }

                return images
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return arrayOf()
        }

        fun getByArea(context: Context, area: Area) : Array<Image> {
            return getByAreaId(context, area.id)
        }
    }

    fun add(context: Context) : Boolean {
        val mHandler = DatabaseHandler.open(context)

        try {
            val sql = """
                    INSERT INTO IMAGE_TB (area_id, image, created_date)
                    VALUES (
                        "${area?.id ?: return false}",
                        "${BitmapHandler.bitmapToPngBase64(image ?: return false)}",
                        "${SDF.dateBar.format(Date())}"
                    )
                """.trimIndent()

            mHandler.write(sql)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mHandler.close()
        }

        return false
    }

    fun save(context: Context) : Boolean {
        val mHandler = DatabaseHandler.open(context)

        try {
            val sql = """
                    UPDATE IMAGE_TB SET
                    area_id="${area?.id ?: return false}",
                    image="${BitmapHandler.bitmapToPngBase64(image ?: return false)}"
                    WHERE id=${id}
                """.trimIndent()

            mHandler.write(sql)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mHandler.close()
        }

        return false
    }
}