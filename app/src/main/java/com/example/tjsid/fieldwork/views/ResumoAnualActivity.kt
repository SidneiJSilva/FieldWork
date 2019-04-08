package com.example.tjsid.fieldwork.views

import android.content.Intent
import android.graphics.Color
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
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.business.ReportBusiness
import com.example.tjsid.fieldwork.business.UserBusiness
import com.example.tjsid.fieldwork.dates.Date
import com.example.tjsid.fieldwork.entities.ReportEntity
import kotlinx.android.synthetic.main.activity_resumo_anual.*
import kotlinx.android.synthetic.main.app_bar_resumo_anual.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_resumo_anual.*
import java.lang.Integer.parseInt

class ResumoAnualActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {


    private lateinit var mReportBusiness: ReportBusiness
    private lateinit var mUserBusiness: UserBusiness
    private val date: Date = Date.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo_anual)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //instanciando variáveis
        mReportBusiness = ReportBusiness(this)
        mUserBusiness = UserBusiness(this)

        //populando spinner
        loadSpinner()

        //popula tela
        screenStart()

        //escuta as mudanças nos spinners
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
        menuInflater.inflate(R.menu.resumo_anual, menu)
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
            R.id.telaInicial -> {
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
            R.id.resumoAnual -> {
                startActivity(Intent(this, ResumoAnualActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun screenStart(){
        val nome = spinnerResumnoAnual.selectedItem.toString()
        val year = spinnerResumnoAnualAno.selectedItem.toString()

        //janeiro
        var reportJan: ReportEntity = mReportBusiness.mainConsult(nome, 1.toString(), year)
        janPub.text = reportJan.publicacoes.toString()
        janVid.text = reportJan.videos.toString()
        var janHour: String
        janHour = if (reportJan.horas % 60 == 0) {
            (reportJan.horas / 60).toString() + ":00"
        } else {
            (reportJan.horas / 60).toString() + ":" + (reportJan.horas % 60)
        }
        janHor.text = janHour
        janRev.text = reportJan.revisitas.toString()
        janEst.text = reportJan.estudos.toString()

        //fevereiro
        var reportFev: ReportEntity = mReportBusiness.mainConsult(nome, 2.toString(), year)
        fevPub.text = reportFev.publicacoes.toString()
        fevVid.text = reportFev.videos.toString()
        var fevHour: String
        fevHour = if (reportFev.horas % 60 == 0) {
            (reportFev.horas / 60).toString() + ":00"
        } else {
            (reportFev.horas / 60).toString() + ":" + (reportFev.horas % 60)
        }
        fevHor.text = fevHour
        fevRev.text = reportFev.revisitas.toString()
        fevEst.text = reportFev.estudos.toString()

        //marco
        var reportMar: ReportEntity = mReportBusiness.mainConsult(nome, 3.toString(), year)
        marPub.text = reportMar.publicacoes.toString()
        marVid.text = reportMar.videos.toString()
        var marHour: String
        marHour = if (reportMar.horas % 60 == 0) {
            (reportMar.horas / 60).toString() + ":00"
        } else {
            (reportMar.horas / 60).toString() + ":" + (reportMar.horas % 60)
        }
        marHor.text = marHour
        marRev.text = reportMar.revisitas.toString()
        marEst.text = reportMar.estudos.toString()

        //abril
        var reportAbr: ReportEntity = mReportBusiness.mainConsult(nome, 4.toString(), year)
        abrPub.text = reportAbr.publicacoes.toString()
        abrVid.text = reportAbr.videos.toString()
        var abrHour: String
        abrHour = if (reportAbr.horas % 60 == 0) {
            (reportAbr.horas / 60).toString() + ":00"
        } else {
            (reportAbr.horas / 60).toString() + ":" + (reportAbr.horas % 60)
        }
        abrHor.text = abrHour
        abrRev.text = reportAbr.revisitas.toString()
        abrEst.text = reportAbr.estudos.toString()

        //maio
        var reportMai: ReportEntity = mReportBusiness.mainConsult(nome, 5.toString(), year)
        maiPub.text = reportMai.publicacoes.toString()
        maiVid.text = reportMai.videos.toString()
        var maiHour: String
        maiHour = if (reportMai.horas % 60 == 0) {
            (reportMai.horas / 60).toString() + ":00"
        } else {
            (reportMai.horas / 60).toString() + ":" + (reportMai.horas % 60)
        }
        maiHor.text = maiHour
        maiRev.text = reportMai.revisitas.toString()
        maiEst.text = reportMai.estudos.toString()

        //junho
        var reportJun: ReportEntity = mReportBusiness.mainConsult(nome, 6.toString(), year)
        junPub.text = reportJun.publicacoes.toString()
        junVid.text = reportJun.videos.toString()
        var junHour: String
        junHour = if (reportJun.horas % 60 == 0) {
            (reportJun.horas / 60).toString() + ":00"
        } else {
            (reportJun.horas / 60).toString() + ":" + (reportJun.horas % 60)
        }
        junHor.text = junHour
        junRev.text = reportJun.revisitas.toString()
        junEst.text = reportJun.estudos.toString()

        //julho
        var reportJul: ReportEntity = mReportBusiness.mainConsult(nome, 7.toString(), year)
        julPub.text = reportJul.publicacoes.toString()
        julVid.text = reportJul.videos.toString()
        var julHour: String
        julHour = if (reportJul.horas % 60 == 0) {
            (reportJul.horas / 60).toString() + ":00"
        } else {
            (reportJul.horas / 60).toString() + ":" + (reportJul.horas % 60)
        }
        julHor.text = julHour
        julRev.text = reportJul.revisitas.toString()
        julEst.text = reportJul.estudos.toString()

        //agosto
        var reportAgo: ReportEntity = mReportBusiness.mainConsult(nome, 8.toString(), year)
        agoPub.text = reportAgo.publicacoes.toString()
        agoVid.text = reportAgo.videos.toString()
        var agoHour: String
        agoHour = if (reportAgo.horas % 60 == 0) {
            (reportAgo.horas / 60).toString() + ":00"
        } else {
            (reportAgo.horas / 60).toString() + ":" + (reportAgo.horas % 60)
        }
        agoHor.text = agoHour
        agoRev.text = reportAgo.revisitas.toString()
        agoEst.text = reportAgo.estudos.toString()

        //setembro
        var reportSet: ReportEntity = mReportBusiness.mainConsult(nome, 9.toString(), year)
        setPub.text = reportSet.publicacoes.toString()
        setVid.text = reportSet.videos.toString()
        var setHour: String
        setHour = if (reportSet.horas % 60 == 0) {
            (reportSet.horas / 60).toString() + ":00"
        } else {
            (reportSet.horas / 60).toString() + ":" + (reportSet.horas % 60)
        }
        setHor.text = setHour
        setRev.text = reportSet.revisitas.toString()
        setEst.text = reportSet.estudos.toString()

        //outubro
        var reportOut: ReportEntity = mReportBusiness.mainConsult(nome, 10.toString(), year)
        outPub.text = reportOut.publicacoes.toString()
        outVid.text = reportOut.videos.toString()
        var outHour: String
        outHour = if (reportOut.horas % 60 == 0) {
            (reportOut.horas / 60).toString() + ":00"
        } else {
            (reportOut.horas / 60).toString() + ":" + (reportOut.horas % 60)
        }
        outHor.text = outHour
        outRev.text = reportOut.revisitas.toString()
        outEst.text = reportOut.estudos.toString()

        //novembro
        var reportNov: ReportEntity = mReportBusiness.mainConsult(nome, 11.toString(), year)
        novPub.text = reportNov.publicacoes.toString()
        novVid.text = reportNov.videos.toString()
        var novHour: String
        novHour = if (reportNov.horas % 60 == 0) {
            (reportNov.horas / 60).toString() + ":00"
        } else {
            (reportNov.horas / 60).toString() + ":" + (reportNov.horas % 60)
        }
        novHor.text = novHour
        novRev.text = reportNov.revisitas.toString()
        novEst.text = reportNov.estudos.toString()

        //dezembro
        var reportDez: ReportEntity = mReportBusiness.mainConsult(nome, 12.toString(), year)
        dezPub.text = reportDez.publicacoes.toString()
        dezVid.text = reportDez.videos.toString()
        var dezHour: String
        dezHour = if (reportDez.horas % 60 == 0) {
            (reportDez.horas / 60).toString() + ":00"
        } else {
            (reportDez.horas / 60).toString() + ":" + (reportDez.horas % 60)
        }
        dezHor.text = dezHour
        dezRev.text = reportDez.revisitas.toString()
        dezEst.text = reportDez.estudos.toString()

        //total
        var total: ReportEntity = mReportBusiness.consultTotal(nome, year)
        totPub.text = total.publicacoes.toString()
        totVid.text = total.videos.toString()
        var totHour: String
        totHour = if (total.horas % 60 == 0) {
            (total.horas / 60).toString() + ":00"
        } else {
            (total.horas / 60).toString() + ":" + (total.horas % 60)
        }
        totHor.text = totHour
        totRev.text = total.revisitas.toString()
        totEst.text = total.estudos.toString()

    }

    private fun loadSpinner() {
        val list = mUserBusiness.getUserList()

        val nameListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)

        spinnerResumnoAnual.adapter = nameListAdapter

        val year = parseInt(date.getYear())

        val yearList = listOf((year) ,(year-1), (year-2), (year-3), (year-4), (year-5))

        val yearListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yearList)

        spinnerResumnoAnualAno.adapter = yearListAdapter

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        screenStart()
    }

    private fun setListeners(){
        spinnerResumnoAnual.onItemSelectedListener = this
        spinnerResumnoAnualAno.onItemSelectedListener = this
    }

}
