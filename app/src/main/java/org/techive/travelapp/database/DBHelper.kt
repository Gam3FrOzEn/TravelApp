package org.techive.travelapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.techive.travelapp.database.DBHelper
import org.techive.travelapp.models.City
import org.techive.travelapp.models.Landmark

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var columnsCity = arrayOf(KEY_ID, KEY_NAME, KEY_DESC)
    var columnsLandmark = arrayOf(KEY_ID, KEY_NAME, KEY_DESC, KEY_CITY)

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CITY)
        db.execSQL(CREATE_TABLE_LANDMARK)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CITY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LANDMARK")
        onCreate(db)
    }

    fun insertCity(city: City) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, city.name)
        values.put(KEY_DESC, city.description)
        db.insert(TABLE_CITY, null, values)
    }

    @get:SuppressLint("Range")
    val cities: MutableList<City>
        get() {
            val list: MutableList<City> = ArrayList()
            val database = this.readableDatabase
            @SuppressLint("Recycle") val cursor =
                database.query(TABLE_CITY, columnsCity, null, null, null, null, null)
            while (cursor.moveToNext()) {
                list.add(
                    City(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESC))
                    )
                )
            }
            return list
        }

    fun deleteCity(id: String) {
        val db = this.writableDatabase
        val selection = "$KEY_ID = ?"
        val selectionArgs = arrayOf(id)
        db.delete(TABLE_CITY, selection, selectionArgs)
        db.close()
    }

    fun insertLandmark(landmark: Landmark) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, landmark.name)
        values.put(KEY_DESC, landmark.description)
        values.put(KEY_CITY, landmark.city)
        db.insert(TABLE_LANDMARK, null, values)
    }

    @SuppressLint("Range")
    fun getLandmarks(city: String): MutableList<Landmark> {
        val list: MutableList<Landmark> = ArrayList()
        val selection = "$KEY_CITY = ?"
        val selectionArgs = arrayOf(city)
        val database = this.readableDatabase
        @SuppressLint("Recycle") val cursor = database.query(
            TABLE_LANDMARK,
            columnsLandmark,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            list.add(
                Landmark(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_DESC)),
                    cursor.getString(cursor.getColumnIndex(KEY_CITY))
                )
            )
        }
        return list
    }

    @SuppressLint("Recycle")
    fun checkIfLandmarkExist(city: String): Long {
        val selection = "$KEY_CITY = ?"
        val selectionArgs = arrayOf(city)
        val database = this.readableDatabase
        @SuppressLint("Recycle") val c = database.query(
            TABLE_LANDMARK,
            columnsLandmark,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return c.count.toLong()
    }

    fun deleteLandmark(id: String) {
        val db = this.writableDatabase
        val selection = "$KEY_ID = ?"
        val selectionArgs = arrayOf(id)
        db.delete(TABLE_LANDMARK, selection, selectionArgs)
        db.close()
    }

    companion object {
        private val LOG = DBHelper::class.java.name
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "travelapp"
        private const val TABLE_CITY = "cities"
        private const val TABLE_LANDMARK = "landmarks"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_DESC = "desc"
        private const val KEY_CITY = "city"

        private const val CREATE_TABLE_CITY = ("CREATE TABLE " + TABLE_CITY
                + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT" + ")")

        private const val CREATE_TABLE_LANDMARK = ("CREATE TABLE " + TABLE_LANDMARK
                + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT,"
                + KEY_CITY + " TEXT" + ")")
    }
}