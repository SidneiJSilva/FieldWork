package com.example.tjsid.fieldwork.entities

data class ReportEntity (val id: Int,
                         var dia: String,
                         var mes: String,
                         var ano: String,
                         var publicador: String,
                         var publicacoes: Int,
                         var videos: Int,
                         var horas: Int,
                         var revisitas: Int,
                         var estudos: Int,
                         var notas: String)