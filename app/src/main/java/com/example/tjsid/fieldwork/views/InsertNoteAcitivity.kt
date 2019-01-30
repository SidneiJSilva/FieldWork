package com.example.tjsid.fieldwork.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tjsid.fieldwork.R
import kotlinx.android.synthetic.main.activity_insert_note_acitivity.*

class InsertNoteAcitivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_note_acitivity)

        setListeners()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.confirmNote -> endAll()
        }
    }

    private fun endAll(){
        val nota = contentNote.text.toString()

        val i = Intent(this, InsertActivity::class.java)
        i.putExtra("hora", intent.getStringExtra("hora"))
        i.putExtra("minuto", intent.getStringExtra("minuto"))
        i.putExtra("publicacao", intent.getStringExtra("publicacao"))
        i.putExtra("video", intent.getStringExtra("video"))
        i.putExtra("revisita", intent.getStringExtra("revisita"))
        i.putExtra("nota", nota)
        i.putExtra("ref", 1)

        finish()

        startActivity(i)

    }

    private fun setListeners(){
        confirmNote.setOnClickListener(this)
    }

}
