package com.example.tjsid.fieldwork.business

import android.content.Context
import com.example.tjsid.fieldwork.entities.ReportEntity
import com.example.tjsid.fieldwork.repository.ReportRepository

class ReportBusiness(context: Context) {

    private val mReportRepository: ReportRepository = ReportRepository.getInstance(context)

    fun get(publicador: String): ReportEntity {
        val report: ReportEntity? = mReportRepository.get(publicador)
        if(report != null){
            return report
        }else{
            var reportNull: ReportEntity = ReportEntity(0,"nulo","","","",0,0,0,0,0, "")
            return reportNull
        }
    }

    fun insert(reportEntity: ReportEntity) = mReportRepository.insert(reportEntity)

    fun getBeforeInsert(reportEntity: ReportEntity) = mReportRepository.getBeforeInsert(reportEntity)

    fun getToSum(reportEntity: ReportEntity) = mReportRepository.getToSum(reportEntity)

    fun update(reportEntity: ReportEntity) = mReportRepository.update(reportEntity)

    fun consult(reportEntity: ReportEntity) = mReportRepository.consult(reportEntity)

    fun mainConsult(name: String) = mReportRepository.mainConsult(name)

    fun delete(reportEntity: ReportEntity) = mReportRepository.delete(reportEntity)

    fun getNotes(nome: String, mes: Int) = mReportRepository.getNotes(nome, mes)

    fun getNotes(nome: String, dia: String, mes: String, ano: String) = mReportRepository.getNotes(nome, dia, mes, ano)

    fun getNotes(nome: String, ano: String) = mReportRepository.getNotes(nome, ano)

    fun getEstudos(mes: String, ano: String, nome: String) = mReportRepository.getEstudo(mes, ano, nome)

    fun insertEstudo(mes: String, ano: String, nome: String, estudo: Int) = mReportRepository.insertEstudo(mes, ano, nome, estudo)

    fun updateEstudo(mes: String, ano: String, nome: String, estudo: Int) = mReportRepository.updateEstudo(mes, ano, nome, estudo)

}