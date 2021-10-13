package com.example.uppgifter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


const val DATABASE_NAME = "Student_DB"
const val TABLE_NAME = "Students"
const val COL_STUDENTS_ID = "id"
const val COL_STUDENTS_NAME = "name"
const val COL_STUDENTS_AGE = "age"
const val COL_STUDENTS_GENDER = "gender"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = " CREATE TABLE $TABLE_NAME (" +
                "$COL_STUDENTS_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_STUDENTS_NAME VARCHAR(256), " +
                "$COL_STUDENTS_AGE INTEGER, " +
                "$COL_STUDENTS_GENDER VARCHAR(6))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, db1: Int, db2: Int) {
        TODO("Not yet implemented")
    }


    fun insertData(students: Students) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COL_STUDENTS_NAME, students.name)
        cv.put(COL_STUDENTS_AGE, students.age)
        cv.put(COL_STUDENTS_GENDER, students.gender)

        val result = db.insert(TABLE_NAME, null, cv)

        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("Range")
    fun readData() : MutableList<Students> {
        val list : MutableList<Students> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val students = Students()
                students.id = result.getString(result.getColumnIndex(COL_STUDENTS_ID)).toInt()
                students.name = result.getString(result.getColumnIndex(COL_STUDENTS_NAME)).toString()
                students.age = result.getString(result.getColumnIndex(COL_STUDENTS_AGE)).toInt()
                students.gender = result.getString(result.getColumnIndex(COL_STUDENTS_GENDER)).toString()
                list.add(students)
            }
                while (result.moveToNext())
        }
        return list

    }

}