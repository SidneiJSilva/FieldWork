package com.example.tjsid.fieldwork.views

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.business.ReportBusiness
import com.example.tjsid.fieldwork.business.UserBusiness
import com.example.tjsid.fieldwork.dates.Date
import com.example.tjsid.fieldwork.entities.ReportEntity
import kotlinx.android.synthetic.main.activity_estudos.*
import kotlinx.android.synthetic.main.app_bar_estudos.*
import kotlinx.android.synthetic.main.content_estudos.*
import kotlinx.android.synthetic.main.content_insert.*
import java.lang.Integer.parseInt
import java.util.*

class EstudosActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemSelectedListener {



    private val date: Date = Date.getInstance(this)
    private lateinit var mReportBusiness: ReportBusiness
    private lateinit var mUserBusiness: UserBusiness


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudos)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        mReportBusiness = ReportBusiness(this)
        mUserBusiness = UserBusiness(this)

        loadSpinner()

        setListeners()


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.estudos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.telaInicial-> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.incluirRelatorio -> {
                startActivity(Intent(this, InsertActivity::class.java))
            }
            R.id.incluirUsuario -> {
                var intent = Intent(this, CadUserActivity::class.java)
                intent.putExtra("ref", 1)
                startActivity(intent)
            }
            R.id.consultaRelatorio -> {
                startActivity(Intent(this, ConsultActivity::class.java))
            }
            R.id.consultaNotas -> {
                startActivity(Intent(this, ConsultaNotasActivity::class.java))
            }
            R.id.incluirEstudo -> {
                startActivity(Intent(this, EstudosActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadSpinner(){
        spinnerMes.setSelection(date.getNumberMonth()-1)
        val list = mUserBusiness.getUserList()
        val nameListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        spinnerPublicador.adapter = nameListAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    private fun setListeners(){
        btnEstudo.setOnClickListener(this)
        spinnerPublicador.onItemSelectedListener = this

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnEstudo -> saveEstudo()
        }
    }

    private fun saveEstudo(){


        if(txtEstudo.text.toString() == ""){
            Toast.makeText(this, "Número de estudo não preenchido", Toast.LENGTH_LONG).show()
        }else{
            val month = (spinnerMes.selectedItemId+1).toString()
            val year = date.getYear()
            val nome = spinnerPublicador.selectedItem.toString()
            val number = parseInt(txtEstudo.text.toString())
            if(mReportBusiness.getEstudos(month, year, nome)){

                //construindo alertDialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Atenção")
                builder.setMessage("Dados de estudo encontrados para este mês. Substituir?")
                builder.setPositiveButton("Sim") { dialogInterface: DialogInterface, i: Int ->

                    mReportBusiness.updateEstudo(month, year, nome, number)

                    finish()
                    startActivity(Intent(this, EstudosActivity::class.java))
                    Toast.makeText(this, "Estudo incluído com sucesso!", Toast.LENGTH_SHORT).show()

                }
                builder.setNegativeButton("Não") { dialogInterface: DialogInterface, i: Int -> }
                builder.show()
            }else{
                mReportBusiness.insertEstudo(month, year, nome, number)

                finish()
                startActivity(Intent(this, EstudosActivity::class.java))
                Toast.makeText(this, "Estudo salvo com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
