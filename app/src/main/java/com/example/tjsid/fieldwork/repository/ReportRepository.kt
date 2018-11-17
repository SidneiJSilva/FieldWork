package com.example.tjsid.fieldwork.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.tjsid.fieldwork.constants.DataBaseConstants
import com.example.tjsid.fieldwork.entities.ReportEntity
import java.lang.Exception

class ReportRepository private constructor(context: Context){

    private var mFieldWorkDataBaseHelper: FieldWorkDataBaseHelper = FieldWorkDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): ReportRepository {
            if (INSTANCE == null) {
                INSTANCE = ReportRepository(context)
            }
            return INSTANCE as ReportRepository
        }

        private var INSTANCE: ReportRepository? = null
    }

    fun get(date: String): ReportEntity?{

        var reportEntity: ReportEntity? = null

        try {
            val cursor: Cursor
            val db = mFieldWorkDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.REPORT.COLUMNS.ID,
                DataBaseConstants.REPORT.COLUMNS.DATA,
                DataBaseConstants.REPORT.COLUMNS.PUBLICACOES,
                DataBaseConstants.REPORT.COLUMNS.VIDEOS,
                DataBaseConstants.REPORT.COLUMNS.HORAS,
                DataBaseConstants.REPORT.COLUMNS.REVISITAS,
                DataBaseConstants.REPORT.COLUMNS.ESTUDOS)

            val selection = "${DataBaseConstants.REPORT.COLUMNS.DATA} = ?"

            val selectionArgs = arrayOf(date.toString())

            cursor = db.query(DataBaseConstants.REPORT.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            if (cursor.count > 0){
                cursor.moveToFirst()
                val reportId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ID))
                val reportData = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.DATA))
                val reportPublicacoes = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES))
                val reportVideos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.VIDEOS))
                val reportHoras = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.HORAS))
                val reportRevisitas = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.REVISITAS))
                val reportEstudos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ESTUDOS))

                reportEntity = ReportEntity(reportId, reportData, reportPublicacoes, reportVideos, reportHoras, reportRevisitas, reportEstudos)
            }

            cursor.close()

        }catch (e: Exception){
            return reportEntity
        }
        return reportEntity

    }

    fun insertDefaultReport(){
        val db = mFieldWorkDataBaseHelper.writableDatabase
        val insertValues = ContentValues()
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.DATA, "01/01/2001")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES, 7)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.VIDEOS, 3)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.HORAS, 10)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.REVISITAS, 5)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.ESTUDOS, 1)
        db.insert(DataBaseConstants.REPORT.TABLE_NAME, null, insertValues)
    }

}