package com.khnsoft.damta.data

import android.content.Context
import com.khnsoft.damta.utils.DatabaseHandler
import com.khnsoft.damta.utils.MyLogger
import com.khnsoft.damta.utils.SDF
import java.lang.Exception
import java.util.*

class Thumb(
    var id: Int = -1,
    var user: User? = null,
    var area: Area? = null,
    var createdDate: Date? = null
) {
    companion object {
        fun getCountByAreaId(context: Context, id: Int) : Int {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT COUNT(id) AS count 
                    FROM THUMB_TB
                    WHERE area_id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                if (jResult.size() > 0) {
                    return jResult[0].asJsonObject["count"].asInt
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return -1
        }

        fun getCountByArea(context: Context, area: Area) : Int {
            return getCountByAreaId(context, area.id)
        }

        fun isThumbed(context: Context, user: User?, area: Area?) : Boolean {
            if (user == null || area == null) return false

            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT COUNT(id) AS count 
                    FROM THUMB_TB
                    WHERE user_id=${user.id} AND area_id=${area.id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                if (jResult.size() > 0) {
                    return jResult[0].asJsonObject["count"].asInt >= 1
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return false
        }

        fun add(context: Context, user: User, area: Area) : Boolean {
            MyLogger.d("@@@", "Add Thumb")
            return Thumb(
                user = user,
                area = area,
                createdDate = Date()
            ).add(context)
        }

        fun remove(context: Context, user: User, area: Area) : Boolean {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    DELETE FROM THUMB_TB
                    WHERE user_id=${user.id} AND area_id=${area.id}
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

    fun add(context: Context) : Boolean {
        val mHandler = DatabaseHandler.open(context)

        try {
            val sql = """
                    INSERT INTO THUMB_TB (user_id, area_id, created_date) 
                    VALUES (
                        "${user?.id ?: return false}",
                        "${area?.id ?: return false}",
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
}
