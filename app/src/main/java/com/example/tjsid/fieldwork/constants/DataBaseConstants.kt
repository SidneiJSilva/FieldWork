package com.example.tjsid.fieldwork.constants

class DataBaseConstants {

    object REPORT {
        val TABLE_NAME = "report"

        object COLUMNS {
            val ID = "id"
            val PUBLICADOR = "publicador"
            val DIA = "dia"
            val MES = "mes"
            val ANO = "ano"
            val PUBLICACOES = "publicacoes"
            val VIDEOS = "videos"
            val HORAS = "horas"
            val REVISITAS = "revisitas"
            val ESTUDOS = "estudos"
            val NOTAS = "notas"
        }
    }

    object USER {
        val TABLE_NAME = "user"

        object COLUMNS {
            val ID = "id"
            val NOME = "nome"
            val EMAIL = "email"
        }
    }

}