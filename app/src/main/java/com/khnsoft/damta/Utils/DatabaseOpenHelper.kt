package com.khnsoft.damta.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.khnsoft.damta.MyLogger
import java.lang.Exception

class DatabaseOpenHelper(val context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        createTableUser(db)
        createTableArea(db)
        createTableReview(db)
        createTableBookmark(db)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        db?.setForeignKeyConstraintsEnabled(true)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    private fun createTableUser(db: SQLiteDatabase) {
        val sql = """
            CREATE TABLE IF NOT EXISTS USER_TB (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                nickname TEXT NOT NULL,
                birthday TEXT NOT NULL,
                email TEXT NOT NULL,
                created_date TEXT NOT NULL
            )
        """.trimIndent()

        MyLogger.d("Initialize USER_TB", sql)

        try {
            db.execSQL(sql)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createTableArea(db: SQLiteDatabase) {
        val sql = """
            CREATE TABLE IF NOT EXISTS AREA_TB (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                address TEXT NOT NULL,
                x REAL NOT NULL,
                y REAL NOT NULL
            )
        """.trimIndent()

        MyLogger.d("Initialize AREA_TB", sql)

        try {
            db.execSQL(sql)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createTableReview(db: SQLiteDatabase) {
        val sql = """
            CREATE TABLE IF NOT EXISTS REVIEW_TB (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL REFERENCES USER_TB(id) ON DELETE CASCADE,
                area_id INTEGER NOT NULL REFERENCES AREA_TB(id) ON DELETE CASCADE,
                review TEXT NOT NULL,
                density INT NOT NULL,
                created_date TEXT NOT NULL
            )
        """.trimIndent()

        MyLogger.d("Initialize REVIEW_TB", sql)

        try {
            db.execSQL(sql)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createTableBookmark(db: SQLiteDatabase) {
        val sql = """
            CREATE TABLE IF NOT EXISTS BOOKMARK_TB (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL REFERENCES USER_TB(id) ON DELETE CASCADE,
                area_id INTEGER NOT NULL REFERENCES AREA_TB(id) ON DELETE CASCADE,
                created_date TEXT NOT NULL
            )
        """.trimIndent()

        MyLogger.d("Initialize BOOKMARK_TB", sql)

        try {
            db.execSQL(sql)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}