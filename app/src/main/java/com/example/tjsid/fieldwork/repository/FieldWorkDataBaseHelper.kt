package com.example.tjsid.fieldwork.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tjsid.fieldwork.constants.DataBaseConstants

class FieldWorkDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME: String = "fieldwork.db"
        private val DATABASE_VERSION: Int = 1
    }

    private val createTableReport = """
        CREATE TABLE ${DataBaseConstants.REPORT.TABLE_NAME} (
        ${DataBaseConstants.REPORT.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.REPORT.COLUMNS.DATA} TEXT,
        ${DataBaseConstants.REPORT.COLUMNS.PUBLICACOES} INTEGER,
        ${DataBaseConstants.REPORT.COLUMNS.VIDEOS} INTEGER,
        ${DataBaseConstants.REPORT.COLUMNS.HORAS} INTEGER,
        ${DataBaseConstants.REPORT.COLUMNS.REVISITAS} INTEGER,
        ${DataBaseConstants.REPORT.COLUMNS.ESTUDOS} INTEGER
        );
    """

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableReport)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}