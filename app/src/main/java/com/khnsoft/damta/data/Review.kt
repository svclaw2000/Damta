package com.khnsoft.damta.data

import android.content.Context
import com.google.gson.JsonObject
import com.khnsoft.damta.utils.DatabaseHandler
import com.khnsoft.damta.utils.SDF
import java.lang.Exception
import java.util.*

class Review(
    var id: Int = -1,
    var user: User? = null,
    var area: Area? = null,
    var review: String = "",
    var density: Int = 0,
    var createdDate: Date? = null
) {
    companion object {
        fun getFromJson(context: Context, jReview: JsonObject) : Review {
            return Review(
                id = jReview["id"].asInt,
                user = User.getById(context, jReview["user_id"].asInt),
                area = Area.getById(context, jReview["area_id"].asInt),
                review = jReview["review"].asString,
                density = jReview["density"].asInt,
                createdDate = SDF.dateBar.parse(jReview["created_date"].asString)
            )
        }

        fun getByAreaId(context: Context, id: Int) : Array<Review> {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT id, user_id, area_id, review, density, created_date 
                    FROM REVIEW_TB
                    ORDER BY created_date DESC
                    WHERE area_id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                val reviews = Array(jResult.size()) {Review()}

                for (i in 0..jResult.size()-1) {
                    reviews[i] = getFromJson(context, jResult[i].asJsonObject)
                }

                return reviews
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return arrayOf()
        }

        fun getByArea(context: Context, area: Area) : Array<Review> {
            return getByAreaId(context, area.id)
        }

        fun getCountByAreaId(context: Context, id: Int) : Int {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT COUNT(id) AS count 
                    FROM REVIEW_TB
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

        fun getByUserId(context: Context, id: Int) : Array<Review> {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT id, user_id, area_id, created_date 
                    FROM BOOKMARK_TB
                    WHERE user_id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                val reviews = Array(jResult.size()) {Review()}

                for (i in 0..jResult.size()-1) {
                    reviews[i] = getFromJson(context, jResult[i].asJsonObject)
                }

                return reviews
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return arrayOf()
        }

        fun getByUser(context: Context, user: User) : Array<Review> {
            return getByUserId(context, user.id)
        }
    }

    fun add(context: Context) : Boolean {
        val mHandler = DatabaseHandler.open(context)

        try {
            val sql = """
                    INSERT INTO REVIEW_TB (user_id, area_id, review, density, created_date) 
                    VALUES (
                        "${user?.id ?: return false}",
                        "${area?.id ?: return false}",
                        "${review}",
                        ${density},
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