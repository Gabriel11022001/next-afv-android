package com.example.next_vendas

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.api.IOnEnviarServidor
import com.example.next_vendas.api.LoginApi
import com.example.next_vendas.utils.Alerta

class PerfilActivity : AppCompatActivity(), OnClickListener {

    private lateinit var alerta: Alerta
    private lateinit var txtTituloMenu: TextView
    private lateinit var btnRetornar: ImageButton
    private lateinit var btnFiltrar: ImageButton
    private lateinit var btnAdicionar: ImageButton
    private lateinit var txtNomeCompletoUsuarioLogado: TextView
    private lateinit var txtEmailClienteLogado: TextView
    private lateinit var txtTelefoneCelularUsuarioLogado: TextView
    private lateinit var txtNivelAcessoUsuarioLogado: TextView
    private lateinit var btnEditarPerfil: ImageButton
    private lateinit var btnAlterarSenhaUsuario: AppCompatButton
    private lateinit var builderDialogAlterarSenha: AlertDialog.Builder
    private lateinit var dialogAlterarSenha: AlertDialog
    private lateinit var sharedPreferencesUsuarioLogado: SharedPreferences
    private lateinit var loaderAlterarSenha: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // mapear elementos de interface
        this.btnFiltrar = findViewById(R.id.btn_filtro_menu)
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnRetornar = findViewById(R.id.btn_retornar)
        this.txtTituloMenu = findViewById(R.id.txt_titulo)
        this.btnEditarPerfil = findViewById(R.id.btn_alterar_perfil_usuario)
        this.txtNomeCompletoUsuarioLogado = findViewById(R.id.txt_nome_completo_usuario_logado)
        this.txtEmailClienteLogado = findViewById(R.id.txt_email_usuario_logado)
        this.txtTelefoneCelularUsuarioLogado = findViewById(R.id.txt_telefone_celular_usuario_logado)
        this.txtNivelAcessoUsuarioLogado = findViewById(R.id.txt_nivel_acesso_usuario_logado)
        this.btnAlterarSenhaUsuario = findViewById(R.id.btn_alterar_senha_usuario)

        // mapear elementos de interface
        this.btnRetornar.setOnClickListener(this)
        this.btnEditarPerfil.setOnClickListener(this)
        this.btnAlterarSenhaUsuario.setOnClickListener(this)

        this.btnAdicionar.visibility = View.GONE
        this.btnFiltrar.visibility = View.GONE

        this.txtTituloMenu.text = "Perfil"
        // obter preferências compartilhadas do usuário logado
        this.sharedPreferencesUsuarioLogado = getSharedPreferences("PREFERENCIAS_USUARIO_LOGADO", MODE_PRIVATE)

        this.configurarDialogAlterarSenha()

        this.loaderAlterarSenha.visibility = GONE
    }

    private fun configurarDialogAlterarSenha() {
        this.builderDialogAlterarSenha = AlertDialog.Builder(this)

        val viewDialogAlterarSenha = layoutInflater.inflate(R.layout.dialog_alterar_senha, null)
        val edtNovaSenha: EditText = viewDialogAlterarSenha.findViewById(R.id.edt_alterar_senha_usuario)
        val btnEfetivarAlteracaoSenha: AppCompatButton = viewDialogAlterarSenha.findViewById(R.id.btn_efetivar_alterar_senha)
        val edtSenhaAtual: EditText = viewDialogAlterarSenha.findViewById(R.id.edt_senha_atual)
        val btnOkAlterouSenha: AppCompatButton = viewDialogAlterarSenha.findViewById(R.id.btn_ok_efetivou_alteracao_senha)
        this.loaderAlterarSenha = viewDialogAlterarSenha.findViewById(R.id.loader_alterar_senha)

        btnEfetivarAlteracaoSenha.setOnClickListener {
            efetivarAlteracaoSenha(
                edtNovaSenha.text.toString().trim(),
                edtSenhaAtual.text.toString().trim(),
                listOf(
                    edtNovaSenha,
                    edtSenhaAtual
                ),
                btnEfetivarAlteracaoSenha,
                btnOkAlterouSenha
            )
        }

        this.builderDialogAlterarSenha.setView(viewDialogAlterarSenha)
        this.dialogAlterarSenha = this.builderDialogAlterarSenha.create()
    }

    private fun efetivarAlteracaoSenha(novaSenha: String, senhaAtual: String, camposAlterarSenha: List<EditText>, btnEfetivarAlterarSenha: AppCompatButton, btnOkAlterouSenha: AppCompatButton) {
        this.dialogAlterarSenha.setCancelable(false)
        this.btnRetornar.isEnabled = false
        btnEfetivarAlterarSenha.isEnabled = false
        btnOkAlterouSenha.visibility = GONE

        camposAlterarSenha.forEach { campoAlterarSenha ->
            campoAlterarSenha.isEnabled = false
        }

        // obter o e-mail do usuário logado
        val emailUsuarioLogado = this.sharedPreferencesUsuarioLogado.getString("email_usuario_logado", "").toString()

        val loginApi = LoginApi(this.sharedPreferencesUsuarioLogado)

        this.loaderAlterarSenha.visibility = VISIBLE

        loginApi.alterarSenhaUsuario(emailUsuarioLogado, senhaAtual, novaSenha, object : IOnEnviarServidor {

            override fun sucesso(mensagemSucesso: String) {
                loaderAlterarSenha.visibility = GONE
                btnOkAlterouSenha.visibility = VISIBLE
                btnEfetivarAlterarSenha.visibility = GONE

                btnOkAlterouSenha.setOnClickListener {
                    // redirecionar o usuário para a tela de login
                    redirecionarUsuarioTelaLogin()
                }

                Toast.makeText(applicationContext, "Senha alterada com sucesso, realize login novamente.", Toast.LENGTH_LONG).show()
            }

            override fun erro(mensagemErro: String) {
                loaderAlterarSenha.visibility = GONE
                dialogAlterarSenha.setCancelable(true)
                btnRetornar.isEnabled = true
                btnEfetivarAlterarSenha.isEnabled = true

                camposAlterarSenha.forEach { campoAlterarSenha ->
                    campoAlterarSenha.isEnabled = true
                }

                Toast.makeText(applicationContext, mensagemErro, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun redirecionarUsuarioTelaLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun retornar() {

    }

    private fun alterarPerfil() {

    }

    override fun onBackPressed() {
        this.retornar()
    }

    private fun alterarSenha() {
        this.dialogAlterarSenha.show()
    }

    override fun onClick(p0: View?) {

        if (p0?.id == R.id.btn_retornar) {
            this.retornar()
        } else if (p0?.id == R.id.btn_alterar_perfil_usuario) {
            this.alterarPerfil()
        } else if (p0!!.id == R.id.btn_alterar_senha_usuario) {
            this.alterarSenha()
        }

    }

}