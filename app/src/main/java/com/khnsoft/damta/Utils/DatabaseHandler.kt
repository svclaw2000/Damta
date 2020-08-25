package com.khnsoft.damta.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.khnsoft.damta.MyLogger
import com.khnsoft.damta.data.User
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class DatabaseHandler(context: Context?) {
    private val mHelper: DatabaseOpenHelper = DatabaseOpenHelper(context, DB_NAME, null, DB_VERSION)
    private var mDB: SQLiteDatabase = mHelper.readableDatabase

    companion object {
        const val DB_NAME = "Damta.db"
        const val DB_VERSION = 1

        fun open(context: Context?): DatabaseHandler {
            return DatabaseHandler(context)
        }

        fun addUser(context: Context, user: User) : Boolean {
            val mHandler = open(context)

            try {
                val sql = """
                    INSERT INTO USER_TB (username, password, nickname, birthday, email, created_date) 
                    VALUES (
                        "${user.username}",
                        "${user.password}",
                        "${user.nickname}",
                        "${SDF.dateBar.format(user.birthday)}",
                        "${user.email}",
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

        fun updateUser(context: Context, user: User) : Boolean {
            val mHandler = open(context)
            try {
                val sql = """
                    UPDATE USER_TB SET 
                    nickname="${user.nickname}",
                    birthday="${SDF.dateBar.format(user.birthday)}",
                    email="${user.email}"
                    WHERE id=${user.id}
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

        fun getUserById(context: Context, id: Int) : User? {
            val mHandler = open(context)
            try {
                val sql = """
                    SELECT id, username, password, nickname, birthday, email, created_date 
                    FROM USER_TB
                    WHERE id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                if (jResult.size() > 0) {
                    return User.getFromJson(jResult[0].asJsonObject)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return null
        }

        fun getUserByUsername(context: Context, username: String) : User? {
            val mHandler = open(context)
            try {
                val sql = """
                    SELECT id, username, password, nickname, birthday, email, created_date 
                    FROM USER_TB
                    WHERE username="${username}"
                """.trimIndent()

                val jResult = mHandler.read(sql)
                if (jResult.size() > 0) {
                    return User.getFromJson(jResult[0].asJsonObject)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return null
        }
    }

    fun write(sql: String): Boolean {
        mDB = mHelper.writableDatabase
        MyLogger.d("Execute SQL", sql)
        try {
            mDB.execSQL(sql)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun read(sql: String): JsonArray {
        mDB = mHelper.readableDatabase

        val jRet = JsonArray()
        MyLogger.d("Execute SQL for result", sql)
        try {
            val cursor = mDB.rawQuery(sql, null)
            val colNames = cursor.columnNames
            val colCounts = cursor.columnCount
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val jItem = JsonObject()
                    for (i in 0..colCounts-1) {
                        jItem.addProperty(colNames[i], cursor.getString(i))
                    }
                    jRet.add(jItem)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jRet
    }

    fun close() {
        mHelper.close()
    }
}