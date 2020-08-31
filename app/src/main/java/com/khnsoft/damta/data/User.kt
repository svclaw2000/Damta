package com.khnsoft.damta.data

import android.content.Context
import com.google.gson.JsonObject
import com.khnsoft.damta.utils.DatabaseHandler
import com.khnsoft.damta.utils.SDF
import java.lang.Exception
import java.util.*

class User(
    var id: Int = -1,
    var username: String = "",
    var password: String = "",
    var nickname: String = "",
    var birthday: Date? = null,
    var email: String = "",
    var createdDate: Date? = null
) {
    companion object {
        var current : User? = null

        fun getFromJson(jUser: JsonObject): User {
            return User(
                id = jUser["id"].asInt,
                username = jUser["username"].asString,
                password = jUser["password"].asString,
                nickname = jUser["nickname"].asString,
                birthday = SDF.dateBar.parse(jUser["birthday"].asString),
                email = jUser["email"].asString,
                createdDate = SDF.dateBar.parse(jUser["created_date"].asString)
            )
        }

        fun hasSameUsername(context: Context, username: String): Boolean {
            return getByUsername(context, username) != null
        }

        fun getById(context: Context, id: Int) : User? {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT id, username, password, nickname, birthday, email, created_date 
                    FROM USER_TB
                    WHERE id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                if (jResult.size() > 0) {
                    return getFromJson(jResult[0].asJsonObject)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return null
        }

        fun getByUsername(context: Context, username: String) : User? {
            val mHandler = DatabaseHandler.open(context)
            try {
                val sql = """
                    SELECT id, username, password, nickname, birthday, email, created_date 
                    FROM USER_TB
                    WHERE username="${username}"
                """.trimIndent()

                val jResult = mHandler.read(sql)
                if (jResult.size() > 0) {
                    return getFromJson(jResult[0].asJsonObject)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return null
        }
    }

    fun add(context: Context) : Boolean {
        val mHandler = DatabaseHandler.open(context)

        try {
            val sql = """
                    INSERT INTO USER_TB (username, password, nickname, birthday, email, created_date) 
                    VALUES (
                        "${username}",
                        "${password}",
                        "${nickname}",
                        "${SDF.dateBar.format(birthday)}",
                        "${email}",
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
                    UPDATE USER_TB SET 
                    nickname="${nickname}",
                    birthday="${SDF.dateBar.format(birthday)}",
                    email="${email}"
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