package com.khnsoft.damta.data

import android.content.Context
import com.google.gson.JsonObject
import com.khnsoft.damta.utils.DatabaseHandler
import com.khnsoft.damta.utils.SDF
import java.lang.Exception
import java.util.*

class Bookmark(
    var id: Int = -1,
    var user: User? = null,
    var area: Area? = null,
    var createdDate: Date? = null
) {
    companion object {
        fun getFromJson(context: Context, jBookmark: JsonObject) : Bookmark {
            return Bookmark(
                id = jBookmark["id"].asInt,
                user = User.getById(context, jBookmark["user_id"].asInt),
                area = Area.getById(context, jBookmark["area_id"].asInt),
                createdDate = SDF.dateBar.parse(jBookmark["created_date"].asString)
            )
        }

        fun getByUserId(context: Context, id: Int) : Array<Bookmark> {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT id, user_id, area_id, created_date 
                    FROM BOOKMARK_TB
                    WHERE user_id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                val areas = Array(jResult.size()) {Bookmark()}

                for (i in 0..jResult.size()-1) {
                    areas[i] = getFromJson(context, jResult[i].asJsonObject)
                }

                return areas
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return arrayOf()
        }

        fun getByUser(context: Context, user: User) : Array<Bookmark> {
            return getByUserId(context, user.id)
        }

        fun isBookmarked(context: Context, user: User?, area: Area?) : Boolean {
            if (user == null || area == null) return false

            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT COUNT(id) AS count 
                    FROM BOOKMARK_TB
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
            return Bookmark(
                user = user,
                area = area,
                createdDate = Date()
            ).add(context)
        }

        fun remove(context: Context, user: User, area: Area) : Boolean {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    DELETE FROM BOOKMARK_TB
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
                    INSERT INTO BOOKMARK_TB (user_id, area_id, created_date) 
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
