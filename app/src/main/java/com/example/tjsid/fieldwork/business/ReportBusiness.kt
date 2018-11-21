package com.example.tjsid.fieldwork.business

import android.content.Context
import com.example.tjsid.fieldwork.entities.ReportEntity
import com.example.tjsid.fieldwork.repository.ReportRepository

class ReportBusiness(context: Context) {

    private val mReportRepository: ReportRepository = ReportRepository.getInstance(context)

    fun get(publicador: String) = mReportRepository.get(publicador)

    fun insert(reportEntity: ReportEntity) = mReportRepository.insert(reportEntity)

    fun getBeforeInsert(reportEntity: ReportEntity) = mReportRepository.getBeforeInsert(reportEntity)

    fun getToSum(reportEntity: ReportEntity) = mReportRepository.getToSum(reportEntity)

    fun update(reportEntity: ReportEntity) = mReportRepository.update(reportEntity)

    fun consult(reportEntity: ReportEntity) = mReportRepository.consult(reportEntity)

}