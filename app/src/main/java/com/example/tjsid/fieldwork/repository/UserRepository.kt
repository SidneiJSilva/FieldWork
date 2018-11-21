package com.example.tjsid.fieldwork.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.tjsid.fieldwork.constants.DataBaseConstants
import com.example.tjsid.fieldwork.entities.ReportEntity
import com.example.tjsid.fieldwork.entities.UserEntity
import java.lang.Exception

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
}