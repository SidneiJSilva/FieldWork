package com.example.tjsid.fieldwork.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.tjsid.fieldwork.constants.DataBaseConstants
import com.example.tjsid.fieldwork.entities.ReportEntity
import com.example.tjsid.fieldwork.entities.UserEntity
import java.lang.Exception
import java.sql.SQLException

class UserRepository private constructor(context: Context) {

    private var mFieldWorkDataBaseHelper: FieldWorkDataBaseHelper = FieldWorkDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }

        private var INSTANCE: UserRepository? = null
    }

    fun insert(user: UserEntity) {

        try {
            val db = mFieldWorkDataBaseHelper.writableDatabase
            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.USER.COLUMNS.NOME, user.nome)
            insertValues.put(DataBaseConstants.USER.COLUMNS.EMAIL, user.email)

            db.insert(DataBaseConstants.USER.TABLE_NAME, null, insertValues)

        } catch (e: Exception) {
            throw e
        }

    }

    fun get(user: UserEntity): Boolean {

        try {

            val cursor: Cursor
            val c: Cursor
            val db = mFieldWorkDataBaseHelper.readableDatabase

            c = db.rawQuery("SELECT * FROM ${DataBaseConstants.USER.TABLE_NAME}", null)

            if (c.count == 0) {
                return false
            } else {
                cursor = db.rawQuery(
                    "SELECT * FROM ${DataBaseConstants.USER.TABLE_NAME}" +
                            " WHERE ${DataBaseConstants.USER.COLUMNS.EMAIL} = '${user.email}'", null
                )

                return cursor.count > 0
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun getToVerify(): Boolean {
        val cursor: Cursor
        val db = mFieldWorkDataBaseHelper.readableDatabase

        try {

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.USER.TABLE_NAME}", null)

            return cursor.count > 0

        } catch (e: Exception) {
            throw e
        }
    }

    fun getUserList(): List<String>{

        val cursor: Cursor
        val db = mFieldWorkDataBaseHelper.readableDatabase
        var userList = mutableListOf<String>()
        val sql = "SELECT ${DataBaseConstants.USER.COLUMNS.NOME} FROM ${DataBaseConstants.USER.TABLE_NAME}"

        try{
            cursor = db.rawQuery(sql, null)

            if(cursor.count > 0){
                cursor.moveToFirst()
                userList.add(cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NOME)))
                while(cursor.moveToNext()){
                    userList.add(cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NOME)))
                }
            }
            return userList
        }catch (e: Exception){
            throw e
        }
    }
}