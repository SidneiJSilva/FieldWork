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
import com.example.tjsid.fieldwork.R

import com.example.tjsid.fieldwork.business.UserBusiness
import com.example.tjsid.fieldwork.constants.ReportConstants
import com.example.tjsid.fieldwork.entities.UserEntity
import com.example.tjsid.fieldwork.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_cad_user.*
import kotlinx.android.synthetic.main.app_bar_cad_user.*
import kotlinx.android.synthetic.main.content_cad_user.*

class CadUserActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cad_user)
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
        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        //clicar no botão salvar
        btnSave.setOnClickListener() {
            saveUser()
        }

        //verificando se existem usuários cadastrados no banco
        verifiyUser()

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
        menuInflater.inflate(R.menu.cad_user, menu)
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
            R.id.resumoAnual -> {
                startActivity(Intent(this, ResumoAnualActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun saveUser() {
        try {
            var nome = nameUser.text.toString()
            var email = mailUser.text.toString()
            var userEntity = UserEntity(0, nome, email)

            if (mUserBusiness.get(userEntity)) {
                Toast.makeText(this, "E-mail já cadastrado", Toast.LENGTH_LONG).show()
            } else {
                mUserBusiness.insert(userEntity)
                Toast.makeText(this, "Usuário inserido com sucesso!", Toast.LENGTH_LONG).show()

                mSecurityPreferences.storeString(ReportConstants.KEY.USER_NAME, nome)
                mSecurityPreferences.storeString(ReportConstants.KEY.USER_EMAIL, email)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun verifiyUser() {

        var ref = 0

        ref = intent.getIntExtra("ref", 0)

        if (ref == 0) {
            if (mUserBusiness.getToVerify()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
