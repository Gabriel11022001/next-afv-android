package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.dao.GerenciadorBancoDados

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnRealizarLogin: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // mapear elementos de interface
        this.edtEmail = findViewById(R.id.edt_email)
        this.edtSenha = findViewById(R.id.edt_senha)
        this.btnRealizarLogin = findViewById(R.id.btn_entrar)

        this.btnRealizarLogin.setOnClickListener(this)

        // criar base de dados
        GerenciadorBancoDados(this)
    }

    private fun login() {
        startActivity(Intent(this, SincronizacaoActivity::class.java))
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_entrar) {
            this.login()
        }

    }

}