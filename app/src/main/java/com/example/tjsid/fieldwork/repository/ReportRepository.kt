package com.example.tjsid.fieldwork.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.tjsid.fieldwork.constants.DataBaseConstants
import com.example.tjsid.fieldwork.entities.ReportEntity
import java.lang.Exception

class ReportRepository private constructor(context: Context) {

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

    fun get(publicador: String): ReportEntity? {

        var reportEntity: ReportEntity? = null

        try {
            val cursor: Cursor
            val db = mFieldWorkDataBaseHelper.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.REPORT.COLUMNS.ID,
                DataBaseConstants.REPORT.COLUMNS.DIA,
                DataBaseConstants.REPORT.COLUMNS.MES,
                DataBaseConstants.REPORT.COLUMNS.ANO,
                DataBaseConstants.REPORT.COLUMNS.PUBLICADOR,
                DataBaseConstants.REPORT.COLUMNS.PUBLICACOES,
                DataBaseConstants.REPORT.COLUMNS.VIDEOS,
                DataBaseConstants.REPORT.COLUMNS.HORAS,
                DataBaseConstants.REPORT.COLUMNS.REVISITAS,
                DataBaseConstants.REPORT.COLUMNS.ESTUDOS,
                DataBaseConstants.REPORT.COLUMNS.NOTAS
            )

            val selection = "${DataBaseConstants.REPORT.COLUMNS.PUBLICADOR} = ?"

            val selectionArgs = arrayOf(publicador)

            cursor = db.query(
                DataBaseConstants.REPORT.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            if (cursor.count > 0) {
                cursor.moveToFirst()
                val reportId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ID))
                val reportDia = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.DIA))
                val reportMes = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.MES))
                val reportAno = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ANO))
                val reportPublicador =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.PUBLICADOR))
                val reportPublicacoes =
                    cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES))
                val reportVideos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.VIDEOS))
                val reportHoras = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.HORAS))
                val reportRevisitas = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.REVISITAS))
                val reportEstudos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ESTUDOS))
                val reportNotas = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.NOTAS))

                reportEntity = ReportEntity(
                    reportId,
                    reportDia,
                    reportMes,
                    reportAno,
                    reportPublicador,
                    reportPublicacoes,
                    reportVideos,
                    reportHoras,
                    reportRevisitas,
                    reportEstudos,
                    reportNotas
                )
            }

            cursor.close()

        } catch (e: Exception) {
            return reportEntity
        }
        return reportEntity

    }

    fun getToSum(report: ReportEntity): ReportEntity {
        var reportEntity: ReportEntity? = null

        try {

            val cursor: Cursor
            val db = mFieldWorkDataBaseHelper.readableDatabase
            cursor = db.rawQuery(
                "SELECT * FROM report WHERE ${DataBaseConstants.REPORT.COLUMNS.DIA} = ${report.dia} " +
                        "AND ${DataBaseConstants.REPORT.COLUMNS.MES} = ${report.mes} " +
                        "AND ${DataBaseConstants.REPORT.COLUMNS.ANO} = ${report.ano} " +
                        "AND ${DataBaseConstants.REPORT.COLUMNS.PUBLICADOR} = '${report.publicador}';", null
            )

            cursor.moveToFirst()
            val reportId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ID))
            val reportDia = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.DIA))
            val reportMes = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.MES))
            val reportAno = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ANO))
            val reportPublicador = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.PUBLICADOR))
            val reportPublicacoes = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES))
            val reportVideos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.VIDEOS))
            val reportHoras = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.HORAS))
            val reportRevisitas = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.REVISITAS))
            val reportEstudos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ESTUDOS))
            val reportNotas = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.NOTAS))

            reportEntity = ReportEntity(
                reportId,
                reportDia,
                reportMes,
                reportAno,
                reportPublicador,
                reportPublicacoes,
                reportVideos,
                reportHoras,
                reportRevisitas,
                reportEstudos,
                reportNotas
            )

            return reportEntity

        } catch (e: Exception) {
            throw e
        }

    }

//    private fun getStartScreen(): ReportEntity{
//        var reportEntity: ReportEntity? = null
//
//        try{
//            var cursor: Cursor
//            val db = mFieldWorkDataBaseHelper.readableDatabase
//            cursor = db.rawQuery()
//
//
//        }catch (e: Exception){
//            throw e
//        }
//    }

    fun getBeforeInsert(report: ReportEntity): Boolean {

        val cursor: Cursor
        val db = mFieldWorkDataBaseHelper.readableDatabase

        cursor = db.rawQuery(
            "SELECT * FROM report WHERE ${DataBaseConstants.REPORT.COLUMNS.DIA} = ${report.dia} " +
                    "AND ${DataBaseConstants.REPORT.COLUMNS.MES} = ${report.mes} " +
                    "AND ${DataBaseConstants.REPORT.COLUMNS.ANO} = ${report.ano} " +
                    "AND ${DataBaseConstants.REPORT.COLUMNS.PUBLICADOR} = '${report.publicador}';", null
        )

        return cursor.count > 0
    }

    fun insert(report: ReportEntity) {

        try {
            val db = mFieldWorkDataBaseHelper.writableDatabase
            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.DIA, report.dia)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.MES, report.mes)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.ANO, report.ano)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.PUBLICADOR, report.publicador)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES, report.publicacoes)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.VIDEOS, report.videos)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.HORAS, report.horas)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.REVISITAS, report.revisitas)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.ESTUDOS, 0)
            insertValues.put(DataBaseConstants.REPORT.COLUMNS.NOTAS, report.notas)

            db.insert(DataBaseConstants.REPORT.TABLE_NAME, null, insertValues)

        } catch (e: Exception) {
            throw e
        }

    }

    fun update(reportEntity: ReportEntity) {
        try {
            val db = mFieldWorkDataBaseHelper.writableDatabase

            val sql = "UPDATE ${DataBaseConstants.REPORT.TABLE_NAME} SET " +
                    "${DataBaseConstants.REPORT.COLUMNS.PUBLICACOES} = ${reportEntity.publicacoes}, " +
                    "${DataBaseConstants.REPORT.COLUMNS.VIDEOS} = ${reportEntity.videos}," +
                    "${DataBaseConstants.REPORT.COLUMNS.HORAS} = ${reportEntity.horas}, " +
                    "${DataBaseConstants.REPORT.COLUMNS.REVISITAS} = ${reportEntity.revisitas}, " +
                    "${DataBaseConstants.REPORT.COLUMNS.ESTUDOS} = ${reportEntity.estudos}, " +
                    "${DataBaseConstants.REPORT.COLUMNS.NOTAS} = '${reportEntity.notas}' " +
                    "WHERE ${DataBaseConstants.REPORT.COLUMNS.DIA} = ${reportEntity.dia} AND " +
                    "${DataBaseConstants.REPORT.COLUMNS.MES} = ${reportEntity.mes} AND " +
                    "${DataBaseConstants.REPORT.COLUMNS.ANO} = ${reportEntity.ano} AND " +
                    "${DataBaseConstants.REPORT.COLUMNS.PUBLICADOR} = '${reportEntity.publicador}';"

            db.execSQL(sql)
        } catch (e: Exception) {
            throw e
        }
    }

    fun consult(reportEntity: ReportEntity): ReportEntity {

        try {
            val db = mFieldWorkDataBaseHelper.readableDatabase
            val cursor: Cursor
            var report = ReportEntity(0, "-", "-", "-", "-", 0, 0, 0, 0, 0, "")

            cursor = db.rawQuery(
                "SELECT * FROM report WHERE DIA = ${reportEntity.dia} AND MES = ${reportEntity.mes} AND ANO = ${reportEntity.ano}",
                null
            )

            if (cursor.count > 0) {
                cursor.moveToFirst()

                val reportId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ID))
                val reportDia = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.DIA))
                val reportMes = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.MES))
                val reportAno = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ANO))
                val reportPublicador =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.PUBLICADOR))
                val reportPublicacoes =
                    cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES))
                val reportVideos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.VIDEOS))
                val reportHoras = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.HORAS))
                val reportRevisitas = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.REVISITAS))
                val reportEstudos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ESTUDOS))
                val reportNotas = cursor.getString(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.NOTAS))

                report = ReportEntity(
                    reportId,
                    reportDia,
                    reportMes,
                    reportAno,
                    reportPublicador,
                    reportPublicacoes,
                    reportVideos,
                    reportHoras,
                    reportRevisitas,
                    reportEstudos,
                    reportNotas
                )

                return report
            } else {
                return report
            }

        } catch (e: Exception) {
            throw e
        }
    }

    fun mainConsult(name: String): ReportEntity {

        try {
            val db = mFieldWorkDataBaseHelper.readableDatabase
            val cursor: Cursor
            var report = ReportEntity(0, "nulo", "-", "-", "-", 0, 0, 0, 0, 0, "")

            cursor = db.rawQuery(
                "SELECT SUM(${DataBaseConstants.REPORT.COLUMNS.PUBLICACOES}) AS ${DataBaseConstants.REPORT.COLUMNS.PUBLICACOES}, " +
                        "SUM(${DataBaseConstants.REPORT.COLUMNS.VIDEOS}) AS ${DataBaseConstants.REPORT.COLUMNS.VIDEOS}, " +
                        "SUM(${DataBaseConstants.REPORT.COLUMNS.HORAS}) AS ${DataBaseConstants.REPORT.COLUMNS.HORAS}, " +
                        "SUM(${DataBaseConstants.REPORT.COLUMNS.REVISITAS}) AS ${DataBaseConstants.REPORT.COLUMNS.REVISITAS}, " +
                        "SUM(${DataBaseConstants.REPORT.COLUMNS.ESTUDOS}) AS ${DataBaseConstants.REPORT.COLUMNS.ESTUDOS} " +
                        "from ${DataBaseConstants.REPORT.TABLE_NAME} where ${DataBaseConstants.REPORT.COLUMNS.PUBLICADOR} = '${name}'",
                null
            )

            if (cursor.count > 0) {
                cursor.moveToFirst()

                val reportPublicacoes =
                    cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES))
                val reportVideos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.VIDEOS))
                val reportHoras = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.HORAS))
                val reportRevisitas = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.REVISITAS))
                val reportEstudos = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.REPORT.COLUMNS.ESTUDOS))

                report = ReportEntity(
                    0,
                    "",
                    "",
                    "",
                    "",
                    reportPublicacoes,
                    reportVideos,
                    reportHoras,
                    reportRevisitas,
                    reportEstudos,
                    ""
                )

                return report
            } else {
                return report
            }

        } catch (e: Exception) {
            throw e
        }
    }

    fun insertDefaultReport() {
        val db = mFieldWorkDataBaseHelper.writableDatabase
        val insertValues = ContentValues()
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.DIA, "2")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.MES, "12")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.ANO, "2018")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES, 7)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.PUBLICADOR, "Sidnei Silva")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.VIDEOS, 3)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.HORAS, 10)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.REVISITAS, 5)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.ESTUDOS, 1)
        insertValues.put(
            DataBaseConstants.REPORT.COLUMNS.NOTAS, """Publicaodr
            |Sidnei Justino da Silva
            |Morador ficou com sentinela sobre Se Deus se importa com as pessoas
        """.trimMargin()
        )
        db.insert(DataBaseConstants.REPORT.TABLE_NAME, null, insertValues)
    }

    fun insertDefaultReport2() {
        val db = mFieldWorkDataBaseHelper.writableDatabase
        val insertValues = ContentValues()
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.DIA, "18")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.MES, "11")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.ANO, "2018")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.PUBLICACOES, 2)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.PUBLICADOR, "Thiago Cerqueira")
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.VIDEOS, 0)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.HORAS, 2)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.REVISITAS, 2)
        insertValues.put(DataBaseConstants.REPORT.COLUMNS.ESTUDOS, 0)
        db.insert(DataBaseConstants.REPORT.TABLE_NAME, null, insertValues)
    }

}