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
import android.widget.Toast
import com.example.tjsid.fieldwork.dates.Date
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.business.ReportBusiness
import com.example.tjsid.fieldwork.constants.ReportConstants
import com.example.tjsid.fieldwork.entities.ReportEntity
import com.example.tjsid.fieldwork.repository.ReportRepository
import com.example.tjsid.fieldwork.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.sql.SQLException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val date: Date = Date.getInstance(this)
    private lateinit var mReportBusiness: ReportBusiness
    private lateinit var mReportRepository: ReportRepository
    private lateinit var mSecurityPreferences: SecurityPreferences

    private var reportId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        //instanciando variáveis
        mReportRepository = ReportRepository.getInstance(this)
        mReportBusiness = ReportBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        //show date in mainActivity (Sidnei)
        showDate()

//        mReportRepository.insertDefaultReport()

        //populando tela inicial
        startAll()

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
        menuInflater.inflate(R.menu.main, menu)
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

    private fun startAll() {

        try {
            val reportEntityInsert = ReportEntity(reportId, "20", "11", "2018", "Simone Andrade", 2, 0, 2, 0, 0)
            mReportBusiness.insert(reportEntityInsert)

            mReportRepository.insertDefaultReport2()

            val nome = mSecurityPreferences.getStoredString(ReportConstants.KEY.USER_NAME)

            var reportEntity: ReportEntity = mReportBusiness.get(nome)!!
            mainPublicador.text = reportEntity.publicador
            publicacoes.text = reportEntity.publicacoes.toString()
            videos.text = reportEntity.videos.toString()
            horas.text = reportEntity.horas.toString()
            revisitas.text = reportEntity.revisitas.toString()
            estudos.text = reportEntity.estudos.toString()

            var data = reportEntity.dia + "/" + reportEntity.mes + "/" + reportEntity.ano
            dataTest.text = data
        } catch (e: SQLException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDate() {
        val mDate: String = date.getMonth(this) + " de " + date.getYear()
        mainDate.text = mDate
        today.text = date.today()
    }

}
