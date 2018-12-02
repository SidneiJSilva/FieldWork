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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tjsid.fieldwork.dates.Date
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.business.ReportBusiness
import com.example.tjsid.fieldwork.business.UserBusiness
import com.example.tjsid.fieldwork.constants.ReportConstants
import com.example.tjsid.fieldwork.entities.ReportEntity
import com.example.tjsid.fieldwork.repository.ReportRepository
import com.example.tjsid.fieldwork.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_insert.*
import kotlinx.android.synthetic.main.content_main.*
import java.sql.SQLException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    AdapterView.OnItemSelectedListener {


    private val date: Date = Date.getInstance(this)
    private lateinit var mReportBusiness: ReportBusiness
    private lateinit var mReportRepository: ReportRepository
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
//            fazendo um snack bar
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            startActivity(Intent(this, InsertActivity::class.java))
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
        mUserBusiness = UserBusiness(this)

        //show date in mainActivity (Sidnei)
        showDate()

        //populando spinner
        loadSpinner()

        //puxando primeiro nome da lista
        val nome = spinnerMain.selectedItem.toString()

        //populando tela inicial
        startAll(nome)

        setListeners()

    }

    override fun onClick(v: View) {
        nameSpinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //popula a tela inicial conforme o nome clicado no spinner
        val nome = spinnerMain.selectedItem.toString()
        startAll(nome)
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

    private fun startAll(nome: String) {

        try {
            var reportEntity: ReportEntity = mReportBusiness.mainConsult(nome)

            publicacoes.text = reportEntity.publicacoes.toString()
            videos.text = reportEntity.videos.toString()
            horas.text = reportEntity.horas.toString()
            revisitas.text = reportEntity.revisitas.toString()
            estudos.text = reportEntity.estudos.toString()

        } catch (e: SQLException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDate() {
        val mDate: String = date.getMonth(this) + " de " + date.getYear()
        mainDate.text = mDate
    }

    private fun loadSpinner() {
        val list = mUserBusiness.getUserList()

        val nameListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)

        spinnerMain.adapter = nameListAdapter
    }

    private fun setListeners() {
        spinnerMain.onItemSelectedListener = this
    }

}
