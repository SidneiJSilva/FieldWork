package com.example.tjsid.fieldwork.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.entities.NoteEntity

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val mRecyclerRowData: TextView = itemView.findViewById(R.id.recyclerRowData)
    private val mRecyclerRowNota: TextView = itemView.findViewById(R.id.recyclerRowNota)

    fun bindData(notas: NoteEntity){

        var data = "Notas do dia " +notas.dia + "/" + notas.mes + "/" + notas.ano

        mRecyclerRowData.text = data
        mRecyclerRowNota.text = notas.notas
    }

}