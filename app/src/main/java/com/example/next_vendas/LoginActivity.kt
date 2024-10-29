package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.api.IOnEnviarServidor
import com.example.next_vendas.api.LoginApi
import com.example.next_vendas.dao.GerenciadorBancoDados
import com.example.next_vendas.utils.Alerta
import com.example.next_vendas.utils.Loader
import com.example.next_vendas.utils.validarEmail
import com.example.next_vendas.utils.validarEstaConectadoInternet
import com.example.next_vendas.utils.validarPrecisaSincronizar

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnRealizarLogin: AppCompatButton
    private lateinit var loginApi: LoginApi
    private lateinit var loader: Loader
    private lateinit var alerta: Alerta

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

        this.loader = Loader(this)
        this.alerta = Alerta(this)

        this.loginApi = LoginApi(getSharedPreferences(
            "PREFERENCIAS_USUARIO_LOGADO",
            MODE_PRIVATE
        ))
    }

    private fun validarFormulario(): String {
        var msgErro: String = ""
        val email: String = this.edtEmail.text.toString().trim()
        val senha: String = this.edtSenha.text.toString().trim()

        if (email == "") {
            msgErro = "Informe o e-mail."
        } else if (senha == "") {
            msgErro = "Informe a senha."
        } else if (!validarEmail(email)) {
            msgErro = "E-mail inválido."
        } else if (senha.length < 5) {
            msgErro = "Senha inválida."
        }

        return msgErro
    }

    private fun realizarLoginServidor() {
        // apresentar loader
        this.loader.iniciarLoader("Carregando, aguarde...")

        val email: String = this.edtEmail.text.toString().trim()
        val senha: String = this.edtSenha.text.toString().trim()
        this.loginApi.login(email, senha, object : IOnEnviarServidor {

            override fun sucesso(mensagemSucesso: String) {
                // esconder o loader
                loader.finalizarLoader()
                // redirecionar usuário para a tela de sincronização
                redirecionarTelaSincronizacao()
            }

            override fun erro(mensagemErro: String) {
                // esconder o loader
                loader.finalizarLoader()
                // apresentar alerta com erro
                alerta.apresentar(mensagemErro, true)
            }

        })
    }

    private fun redirecionarTelaSincronizacao() {
        val sharedPreferencesSincronizacao = getSharedPreferences("PREFS_SINC", MODE_PRIVATE)
        val precisaSincronizar: Boolean = validarPrecisaSincronizar(sharedPreferencesSincronizacao)

        if (precisaSincronizar) {
            /// redirecionar para tela de sincronização
            val intentTelaSincronizacao = Intent(this, SincronizacaoActivity::class.java)
            startActivity(intentTelaSincronizacao)
        } else {
            // redirecionar para tela home(não precisa sincronizar agora)
            val intentTelaHome = Intent(this, HomeActivity::class.java)
            startActivity(intentTelaHome)
        }

        finish()
    }

    private fun login() {

        try {
            val resultValidarFormularioLogin: String = this.validarFormulario()

            if (resultValidarFormularioLogin.isEmpty()) {

                if (!validarEstaConectadoInternet(this)) {
                    this.alerta.apresentar("Você não está conectado a internet.", true)
                } else {
                    this.realizarLoginServidor()
                }

            } else {
                this.alerta.apresentar(resultValidarFormularioLogin, true)
            }

        } catch (e: Exception) {
            this.alerta.apresentar("Ocorreu um erro ao tentar-se realizar login, aguarde um instante e tente novamente.", true)
            Log.e("erro_login", "Ocorreu o seguinte erro ao tentar-se realizar login: ${ e.message.toString() }")
        }

    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_entrar) {
            this.login()
        }

    }

}