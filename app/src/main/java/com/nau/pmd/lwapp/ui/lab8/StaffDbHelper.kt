package com.nau.pmd.lwapp.ui.lab8

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StaffDbHelper(context: Context) : SQLiteOpenHelper(context, "staff.db", null, 1) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("""
            CREATE TABLE staff (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                department INTEGER,
                staff_number TEXT,
                name TEXT,
                birthday TEXT,
                position TEXT,
                phone TEXT
            );
        """.trimIndent())
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS staff")
            onCreate(db)
        }

        fun insertSampleData() {
            val db = writableDatabase
            val staffList = listOf(
                arrayOf(1, "1001", "Іваненко І.П.", "1980-03-15", "Інженер", "123-456"),
                arrayOf(2, "1002", "Петренко А.Ю.", "1990-05-22", "Менеджер", "222-333"),
                arrayOf(3, "1003", "Сидоренко В.О.", "1985-10-09", "Бухгалтер", "555-777")
            )
            for (s in staffList) {
                val values = ContentValues().apply {
                    put("department", s[0] as Int)
                    put("staff_number", s[1] as String)
                    put("name", s[2] as String)
                    put("birthday", s[3] as String)
                    put("position", s[4] as String)
                    put("phone", s[5] as String)
                }
                db.insert("staff", null, values)
            }
        }

        fun getByDepartment(department: Int): List<Pair<Int, String>> {
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT id, name, position FROM staff WHERE department = ?", arrayOf(department.toString()))
            val result = mutableListOf<Pair<Int, String>>()
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val pos = cursor.getString(2)
                val display = "$name – $pos"
                result.add(id to display)
            }
            cursor.close()
            return result
        }

        fun getAllStaff(): List<Pair<Int, String>> {
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT id, name, position FROM staff", null)
            val result = mutableListOf<Pair<Int, String>>()
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val pos = cursor.getString(2)
                val display = "$name – $pos"
                result.add(id to display)
            }
            cursor.close()
            return result
        }

        fun deleteById(id: Int) {
            val db = writableDatabase
            db.delete("staff", "id = ?", arrayOf(id.toString()))
        }
}