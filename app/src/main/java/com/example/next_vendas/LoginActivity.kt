package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.api.IOnEnviarServidor
import com.example.next_vendas.api.LoginApi
import com.example.next_vendas.dao.GerenciadorBancoDados

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnRealizarLogin: AppCompatButton
    private val loginApi: LoginApi = LoginApi()

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

    private fun validarFormulario(): Boolean {

        return true
    }

    private fun relizarLoginServidor() {
        val email: String = this.edtEmail.text.toString().trim()
        val senha: String = this.edtSenha.text.toString().trim()
        this.loginApi.login(email, senha, object : IOnEnviarServidor {

            override fun sucesso(mensagemSucesso: String) {
                Log.d("sucesso", mensagemSucesso)
            }

            override fun erro(mensagemErro: String) {
                Log.d("erro", mensagemErro)
            }

        })
    }

    private fun login() {

        try {

            if (this.validarFormulario()) {
                this.relizarLoginServidor()
            } else {

            }

        } catch (e: Exception) {
            Log.e("erro_login", "Ocorreu o seguinte erro ao tentar-se realizar login: ${ e.message.toString() }")
        }

    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_entrar) {
            this.login()
        }

    }

}