package com.example.tjsid.fieldwork.views

import android.app.AlertDialog
import android.app.DatePickerDialog
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
import android.widget.DatePicker
import android.widget.Toast
import com.example.tjsid.fieldwork.R
import com.example.tjsid.fieldwork.business.ReportBusiness
import com.example.tjsid.fieldwork.business.UserBusiness
import com.example.tjsid.fieldwork.entities.ReportEntity
import com.example.tjsid.fieldwork.repository.ReportRepository
import kotlinx.android.synthetic.main.activity_consult.*
import kotlinx.android.synthetic.main.app_bar_consult.*
import kotlinx.android.synthetic.main.content_consult.*
import kotlinx.android.synthetic.main.content_insert.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*

class ConsultActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private var dia: String = ""
    private var mes: String = ""
    private var ano: String = ""

    private lateinit var mReportBusiness: ReportBusiness
    private lateinit var mUserBusiness: UserBusiness

    private val mSimpleDateFormat = SimpleDateFormat("d/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consult)
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

        loadSpinner()

        setListeners()

        consultDelete.isEnabled = false

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.consultDataBtn -> datePicker()
            R.id.consultBtn -> consultar()
            R.id.consultDelete -> deletar()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()

        ano = year.toString()
        mes = (month + 1).toString()
        dia = dayOfMonth.toString()

        calendar.set(year, month, dayOfMonth)
        val value = mSimpleDateFormat.format(calendar.time)
        consultData.text = value
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
        menuInflater.inflate(R.menu.consult, menu)
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

    private fun loadSpinner() {
        val list = mUserBusiness.getUserList()
        val nameListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        consultSpinner.adapter = nameListAdapter
    }

    private fun datePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, day).show()
    }

    private fun setListeners() {
        consultDataBtn.setOnClickListener(this)
        consultBtn.setOnClickListener(this)
        consultDelete.setOnClickListener(this)
    }

    private fun consultar() {

        val reportEntity = ReportEntity(0, dia, mes, ano, consultSpinner.selectedItem.toString(), 0, 0, 0, 0, 0, "")

        try {
            var report = mReportBusiness.consult(reportEntity)

            if (report.dia == "nulo") {
                consultPub.text = ""
                consultVid.text = ""
                consultRev.text = ""
                consultHoras.text = ""
                consultNotas.text = ""
                Toast.makeText(this, "Relatório não encontrado!", Toast.LENGTH_LONG).show()
            } else {
                consultPub.text = report.publicacoes.toString()
                consultVid.text = report.videos.toString()
                consultRev.text = report.revisitas.toString()

                val hora = (report.horas/60).toString() + ":" + (report.horas%60).toString()

                consultHoras.text = hora
                consultNotas.text = report.notas

                consultDelete.isEnabled = true
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro: " + e.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun deletar() {

        val reportEntity = ReportEntity(0, dia, mes, ano, consultSpinner.selectedItem.toString(), 0, 0, 0, 0, 0, "")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Atenção")
        builder.setMessage("Deseja mesmo excluir o relatório do dia ${reportEntity.dia}/${reportEntity.mes}/${reportEntity.ano} de ${reportEntity.publicador}?")
        builder.setPositiveButton("Sim") { dialogInterface: DialogInterface, i: Int ->

            try {
                mReportBusiness.delete(reportEntity)
                consultPub.text = ""
                consultVid.text = ""
                consultRev.text = ""
                consultHoras.text = ""
                consultNotas.text = ""
                Toast.makeText(this, "Relatório removido!", Toast.LENGTH_LONG).show()
                consultDelete.isEnabled = false
            } catch (e: Exception) {
                Toast.makeText(this, "Erro: " + e.message, Toast.LENGTH_LONG).show()
            }

        }
        builder.setNegativeButton("Não") { dialogInterface: DialogInterface, i: Int -> }
        builder.show()

    }
}
