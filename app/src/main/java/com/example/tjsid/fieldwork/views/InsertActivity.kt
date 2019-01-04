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
import com.example.tjsid.fieldwork.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.app_bar_insert.*
import kotlinx.android.synthetic.main.content_insert.*
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*

class InsertActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private var dia: String = ""
    private var mes: String = ""
    private var ano: String = ""

    private val mSimpleDateFormat = SimpleDateFormat("d/MM/yyyy")

    private lateinit var mReportBusiness: ReportBusiness
    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mReportRepository: ReportRepository
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        setSupportActionBar(toolbar)

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
        mUserBusiness = UserBusiness(this)

        loadSpinner()

        setListeners()

        insertDate()

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.salvar -> salvar()
            R.id.btnPubMais -> mais("Pub")
            R.id.btnPubMenos -> menos("Pub")
            R.id.btnVidMais -> mais("Vid")
            R.id.btnVidMenos -> menos("Vid")
            R.id.btnRevMais -> mais("Rev")
            R.id.btnRevMenos -> menos("Rev")
            R.id.btnDate -> datePicker()
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//        val value = parent.getItemAtPosition(position).toString()
//        Toast.makeText(this, value, Toast.LENGTH_LONG).show()

    }

    private fun salvar() {

        var horas: Int

        if (insertHour.text.toString() == "" && insertMinute.text.toString() == "") {
            horas = 0
        } else if (insertHour.text.toString() == "" && insertMinute.text.toString() != "") {
            horas = parseInt(insertMinute.text.toString())
        } else if (insertHour.text.toString() != "" && insertMinute.text.toString() == "") {
            horas = parseInt(insertHour.text.toString()) * 60
        } else {
            horas = (parseInt(insertHour.text.toString()) * 60) + parseInt(insertMinute.text.toString())
        }

        var notes = """|${notas.text.toString()}
        """.trimMargin()

        var reportEntity = ReportEntity(
            0,
            dia,
            mes,
            ano,
            nameSpinner.selectedItem.toString(),
            parseInt(viewPub.text.toString()),
            parseInt(viewVid.text.toString()),
            horas,
            parseInt(viewRev.text.toString()),
            0,
            notes
        )

        //verificando se existem dados registrados nesse dia
        if (mReportBusiness.getBeforeInsert(reportEntity)) {

            //construindo alertDialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Atenção")
            builder.setMessage("Relatório encontrado para esta data. Deseja somar?")
            builder.setPositiveButton("Sim") { dialogInterface: DialogInterface, i: Int ->

                var reportEntity2 = mReportBusiness.getToSum(reportEntity)

                reportEntity.publicacoes += reportEntity2.publicacoes
                reportEntity.videos += reportEntity2.videos
                reportEntity.horas += reportEntity2.horas
                reportEntity.revisitas += reportEntity2.revisitas
                reportEntity.estudos += reportEntity2.estudos

                if (reportEntity.notas != "") {
                    reportEntity.notas = reportEntity2.notas + """

                        |(continuação...)
                        |${reportEntity.notas}""".trimMargin()
                } else {
                    reportEntity.notas = reportEntity2.notas
                }

                mReportBusiness.update(reportEntity)
                finish()
                startActivity(Intent(this, InsertActivity::class.java))
                Toast.makeText(this, "Relatório somado com sucesso", Toast.LENGTH_SHORT).show()

            }
            builder.setNegativeButton("Não") { dialogInterface: DialogInterface, i: Int -> }
            builder.show()

        } else {
            mReportBusiness.insert(reportEntity)
            finish()
            startActivity(Intent(this, InsertActivity::class.java))
            Toast.makeText(this, "Relatório incluído com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mais(ref: String) {
        when (ref) {
            "Pub" -> {
                var num: Int = (parseInt(viewPub.text.toString())) + 1
                viewPub.text = num.toString()
            }
            "Vid" -> {
                var num: Int = (parseInt(viewVid.text.toString())) + 1
                viewVid.text = num.toString()
            }
            "Rev" -> {
                var num: Int = (parseInt(viewRev.text.toString())) + 1
                viewRev.text = num.toString()
            }
        }
    }

    private fun menos(ref: String) {
        when (ref) {
            "Pub" -> {
                if ((parseInt(viewPub.text.toString())) > 0) {
                    viewPub.text = ((parseInt(viewPub.text.toString())) - 1).toString()
                }
            }
            "Vid" -> {
                if ((parseInt(viewVid.text.toString())) > 0) {
                    viewVid.text = ((parseInt(viewVid.text.toString())) - 1).toString()
                }
            }
            "Rev" -> {
                if ((parseInt(viewRev.text.toString())) > 0) {
                    viewRev.text = ((parseInt(viewRev.text.toString())) - 1).toString()
                }
            }
        }
    }

    //populando spinner
    private fun loadSpinner() {
        val list = mUserBusiness.getUserList()

        val nameListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)

        nameSpinner.adapter = nameListAdapter
    }

    private fun datePicker() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, day).show()

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {

        val calendar = Calendar.getInstance()

        ano = year.toString()
        mes = (month + 1).toString()
        dia = dayOfMonth.toString()

        calendar.set(year, month, dayOfMonth)
        val value = mSimpleDateFormat.format(calendar.time)
        insertData.text = value
    }

    private fun setListeners() {
        salvar.setOnClickListener(this)
        btnPubMais.setOnClickListener(this)
        btnPubMenos.setOnClickListener(this)
        btnVidMais.setOnClickListener(this)
        btnVidMenos.setOnClickListener(this)
        btnRevMais.setOnClickListener(this)
        btnRevMenos.setOnClickListener(this)
        nameSpinner.onItemSelectedListener = this
        btnDate.setOnClickListener(this)
    }

    private fun insertDate() {

        val calendar = Calendar.getInstance()

        ano = calendar.get(Calendar.YEAR).toString()
        mes = (calendar.get(Calendar.MONTH) + 1).toString()
        dia = calendar.get(Calendar.DAY_OF_MONTH).toString()

        calendar.set(parseInt(ano), calendar.get(Calendar.MONTH), parseInt(dia))
        val value = mSimpleDateFormat.format(calendar.time)
        insertData.text = value
    }

}
