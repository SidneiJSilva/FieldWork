package com.example.tjsid.fieldwork.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.adapter.NotesAdapter
import com.example.tjsid.fieldwork.business.ReportBusiness
import com.example.tjsid.fieldwork.business.UserBusiness
import com.example.tjsid.fieldwork.dates.Date
import com.example.tjsid.fieldwork.repository.ReportRepository
import com.example.tjsid.fieldwork.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_consulta_notas.*
import kotlinx.android.synthetic.main.app_bar_consulta_notas.*
import kotlinx.android.synthetic.main.content_consulta_notas.*
import kotlinx.android.synthetic.main.content_insert.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*

class ConsultaNotasActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener,
    AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    val date: Date = Date.getInstance(this)

    private var dia: String = ""
    private var mes: String = ""
    private var ano: String = ""

    private val mSimpleDateFormat = SimpleDateFormat("d/MM/yyyy")

    private lateinit var mReportBusiness: ReportBusiness
    private lateinit var mReportRepository: ReportRepository
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_notas)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        mReportRepository = ReportRepository.getInstance(this)
        mReportBusiness = ReportBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)
        mUserBusiness = UserBusiness(this)

        loadSpinnerNome()

        inputTitles()

        setListeners()

        listMonth()

    }

    private fun listMonth(){
        //1 obtendo elemento da RecyclerView
        recyclerViewNotas
        //2 fazer um adapter
        val noteList = mReportBusiness.getNotes(consultNotasSpinner.selectedItem.toString(), date.getNumberMonth())
        recyclerViewNotas.adapter = NotesAdapter(noteList)
        //3 layout
        recyclerViewNotas.layoutManager = LinearLayoutManager(this)
    }

    private fun listDay(dia: String, mes: String, ano: String){
        //1 obtendo elemento da RecyclerView
        recyclerViewNotas
        //2 fazer um adapter
        val noteList = mReportBusiness.getNotes(consultNotasSpinner.selectedItem.toString(), dia, mes, ano)
        recyclerViewNotas.adapter = NotesAdapter(noteList)
        //3 layout
        recyclerViewNotas.layoutManager = LinearLayoutManager(this)
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
        menuInflater.inflate(R.menu.consulta_notas, menu)
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

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View) {
        consultNotasSpinner.onItemSelectedListener = this

        when (v.id) {
            R.id.consultaNotasData -> datePicker()
            R.id.consultaNotasAno -> disparaConsultaAno()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        listMonth()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()

        ano = year.toString()
        mes = (month + 1).toString()
        dia = dayOfMonth.toString()

        calendar.set(year, month, dayOfMonth)
        val value = mSimpleDateFormat.format(calendar.time)
        consultaNotasData.text = value

        listDay(dia, mes, ano)
    }

    private fun setListeners() {
        consultNotasSpinner.onItemSelectedListener = this
        consultaNotasData.setOnClickListener(this)
        consultaNotasAno.setOnClickListener(this)
    }

    private fun datePicker() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, day).show()
    }

    private fun loadSpinnerNome() {
        val list = mUserBusiness.getUserList()
        val nameListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        consultNotasSpinner.adapter = nameListAdapter
    }

    private fun inputTitles(){
        val year = "Notas de " + date.getYear()
        val month = "Notas de " + date.getMonth(this) + " para"

        txtUser.text = month
        consultaNotasAno.text = year
    }

    private fun disparaConsultaAno() {
        //1 obtendo elemento da RecyclerView
        recyclerViewNotas
        //2 fazer um adapter
        val noteList = mReportBusiness.getNotes(consultNotasSpinner.selectedItem.toString(), date.getYear())
        recyclerViewNotas.adapter = NotesAdapter(noteList)
        //3 layout
        recyclerViewNotas.layoutManager = LinearLayoutManager(this)
    }

}
