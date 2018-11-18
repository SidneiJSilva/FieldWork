package com.example.tjsid.fieldwork.business

import android.content.Context
import com.example.tjsid.fieldwork.repository.ReportRepository

class ReportBusiness(context: Context) {

    private val mReportRepository: ReportRepository = ReportRepository.getInstance(context)

    fun get(publicador: String) = mReportRepository.get(publicador)

}