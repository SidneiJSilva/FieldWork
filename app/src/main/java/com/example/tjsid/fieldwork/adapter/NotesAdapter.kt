package com.example.tjsid.fieldwork.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.entities.NoteEntity
import com.example.tjsid.fieldwork.viewholder.NotesViewHolder

class NotesAdapter(val noteList: List<NoteEntity>): RecyclerView.Adapter<NotesViewHolder>() {

    //cria as linhas
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_notas, parent, false)

        return NotesViewHolder(view)
    }

    //numero de linhas
    override fun getItemCount(): Int {
        return noteList.count()
    }

    //atribui valor para as linhas
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        var note = noteList[position]
        holder.bindData(note)
    }
}