package com.example.tjsid.fieldwork.dates

import android.content.Context
import com.example.tjsid.fieldwork.R
import java.util.*

class Date private constructor(context: Context) {

    val c = Calendar.getInstance()
    val days =
        arrayOf("Domingo", "Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sábado")
    val months = arrayOf(
        "Janeiro",
        "Fevereiro",
        "Março",
        "Abril",
        "Maio",
        "Junho",
        "Julho",
        "Agosto",
        "Setembro",
        "Outubro",
        "Novembro",
        "Dezembro"
    )

    companion object {
        fun getInstance(context: Context): Date {
            if (INSTANCE == null) {
                INSTANCE = Date(context)
            }
            return INSTANCE as Date
        }

        private var INSTANCE: Date? = null
    }

    fun today(): String {
        val str = "${days[c.get(Calendar.DAY_OF_WEEK) - 1]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
        return str
    }

    fun getAllDate(): String {
        var date: Calendar = GregorianCalendar()
        return date.time.toString()
    }

    fun getMonth(context: Context): String {

        var month: Calendar = GregorianCalendar()

        when (month.get(Calendar.MONTH)) {
            0 -> return context.getString(R.string.month_01).toString()
            1 -> return context.getString(R.string.month_02).toString()
            2 -> return context.getString(R.string.month_03).toString()
            3 -> return context.getString(R.string.month_04).toString()
            4 -> return context.getString(R.string.month_05).toString()
            5 -> return context.getString(R.string.month_06).toString()
            6 -> return context.getString(R.string.month_07).toString()
            7 -> return context.getString(R.string.month_08).toString()
            8 -> return context.getString(R.string.month_09).toString()
            9 -> return context.getString(R.string.month_10).toString()
            10 -> return context.getString(R.string.month_11).toString()
            11 -> return context.getString(R.string.month_12).toString()
            else -> return context.getString(R.string.vazio).toString()
        }
    }

    fun getNumberMonth(): Int {

        var month: Calendar = GregorianCalendar()

        return month.get(Calendar.MONTH)+1
    }

    fun getYear(): String {
        var year: Calendar = GregorianCalendar()
        return year.get(Calendar.YEAR).toString()
    }



}