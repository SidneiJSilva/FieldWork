package com.example.tjsid.fieldwork.views

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
import android.widget.Toast
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.business.ReportBusiness
import com.example.tjsid.fieldwork.entities.ReportEntity
import com.example.tjsid.fieldwork.repository.ReportRepository
import com.example.tjsid.fieldwork.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.app_bar_insert.*
import kotlinx.android.synthetic.main.content_insert.*
import java.lang.Integer.parseInt

class InsertActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mReportBusiness: ReportBusiness
    private lateinit var mReportRepository: ReportRepository
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //instaciando variáveis
        mReportRepository = ReportRepository.getInstance(this)
        mReportBusiness = ReportBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        salvar.setOnClickListener{
            salvar()
        }

        botaoConsulta.setOnClickListener{
            consult()
        }

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
        menuInflater.inflate(R.menu.insert, menu)
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
            R.id.nav_camera -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.nav_gallery -> {
                startActivity(Intent(this, InsertActivity::class.java))
            }
            R.id.nav_slideshow -> {
                startActivity(Intent(this, CadUserActivity::class.java))
            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun salvar(){
        var reportEntity = ReportEntity(0,insertDia.text.toString(),insertMes.text.toString(),insertAno.text.toString(),insertNome.text.toString(),parseInt(insertPublicacoes.text.toString()),parseInt(insertVideos.text.toString()),parseInt(insertHoras.text.toString()),parseInt(insertRevisitas.text.toString()),parseInt(insertEstudos.text.toString()) )

        if(mReportBusiness.getBeforeInsert(reportEntity)){
            var reportEntity2 = mReportBusiness.getToSum(reportEntity)

            reportEntity.publicacoes += reportEntity2.publicacoes
            reportEntity.videos += reportEntity2.videos
            reportEntity.horas += reportEntity2.horas
            reportEntity.revisitas += reportEntity2.revisitas
            reportEntity.estudos += reportEntity2.estudos

            mReportBusiness.update(reportEntity)
            finish()
            startActivity(Intent(this, InsertActivity::class.java))
            Toast.makeText(this, "Relatório somando com sucesso", Toast.LENGTH_SHORT).show()
        }else{
            mReportBusiness.insert(reportEntity)
            finish()
            startActivity(Intent(this, InsertActivity::class.java))
            Toast.makeText(this, "Relatório incluído com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

    private fun consult(){
        var reportEntity = ReportEntity(0,insertDia.text.toString(),insertMes.text.toString(),insertAno.text.toString(),insertNome.text.toString(),parseInt(insertPublicacoes.text.toString()),parseInt(insertVideos.text.toString()),parseInt(insertHoras.text.toString()),parseInt(insertRevisitas.text.toString()),parseInt(insertEstudos.text.toString()) )

        var report = mReportBusiness.consult(reportEntity)

        if(report.id == 0){
            Toast.makeText(this, "Dados não encontrados", Toast.LENGTH_LONG).show()
        }else{
            var texto = report.dia + "/" + report.mes + "/" + report.ano + " - " + report.publicador + ": " + report.publicacoes + " " + report.videos + " " + report.horas + " " + report.revisitas + " " + report.estudos
            textoConsulta.text = texto
        }
    }
}
