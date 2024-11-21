package com.example.next_vendas

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.next_vendas.api.UsuarioApi
import com.example.next_vendas.model_servico.UsuarioModelServico
import com.example.next_vendas.utils.Loader
import com.example.next_vendas.utils.validarEmail
import com.google.android.material.snackbar.Snackbar

class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var btnEfetivarAlteracaoDadosCadastrais: AppCompatButton
    private lateinit var notificacaoEditarPerfil: ConstraintLayout
    private lateinit var btnFecharNotificacaoEditarPerfil: ImageButton
    private lateinit var txtTitulo: TextView
    private lateinit var btnRetornar: ImageButton
    private lateinit var btnAdicionarMenu: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var btnApresentarSubMenu: ImageButton
    private lateinit var loader: Loader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        this.mapearElementosInterface()

        this.txtTitulo.text = "Editar perfil"
        this.btnFiltro.visibility = View.GONE
        this.btnAdicionarMenu.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()

        this.buscarDadosUsuarioLogado()
    }

    private fun mapearElementosInterface() {
        this.edtNome = findViewById(R.id.edt_nome)
        this.edtEmail = findViewById(R.id.edt_email)
        this.edtTelefone = findViewById(R.id.edt_telefone)
        this.btnEfetivarAlteracaoDadosCadastrais = findViewById(R.id.btn_alterar_perfil)
        this.notificacaoEditarPerfil = findViewById(R.id.notificacao_editar_perfil)
        this.btnFecharNotificacaoEditarPerfil = findViewById(R.id.btn_fechar_notificacao_edicao_perfil)
        this.btnRetornar = findViewById(R.id.btn_retornar)
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.btnAdicionarMenu = findViewById(R.id.btn_adicionar_menu)
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnApresentarSubMenu = findViewById(R.id.btn_menu_opcoes)

        this.loader = Loader(this)

        this.btnEfetivarAlteracaoDadosCadastrais.setOnClickListener { this.alterarPerfil() }
        this.btnFecharNotificacaoEditarPerfil.setOnClickListener { this.fecharNotificacaoEditarPerfil() }
        this.btnRetornar.setOnClickListener { this.retornar() }
        this.btnApresentarSubMenu.setOnClickListener { this.apresentarSubMenu() }
    }

    /**
     * buscar dados do usuário logado no servidor
     */
    private fun buscarDadosUsuarioLogado() {
        Log.d("buscar_usuario_logado", "Consultando dados do perfil do usuário logado...")

        try {
            val preferenciasCompartilhadasUsuarioLogado: SharedPreferences = getSharedPreferences("PREFERENCIAS_USUARIO_LOGADO", MODE_PRIVATE)
            val idUsuarioLogado: Int = preferenciasCompartilhadasUsuarioLogado.getInt("id_usuario_logado", 0)

            // callback executado enquanto a requisição para buscar os dados do usuário logado está sendo realizado
            val onRealizandoRequisicaoConsultaClienteLogado: () -> Unit = {
                // apresentar o loader
                this.loader.iniciarLoader("Consultando dados do perfil no servidor, aguarde...")
            }

            // callback executado quando ocorre um erro na requisição para buscar os dados do usuário logado
            val onErroRequisicaoConsultaClienteLogado: (String) -> Unit = { msgErro ->
                // esconder loader
                this.loader.finalizarLoader()
                this.apresentarNotificacaoErro(msgErro)
            }

            // callback executado quando a requisição para buscar os dados do usuário logado é finalizada com sucesso
            val onSucessoRequisicaoConsultaClienteLogado: (UsuarioModelServico) -> Unit = { usuarioLogado ->
                // esconder loader
                this.loader.finalizarLoader()
                // preencher campos com dados do usuário logado
                this.preencherCamposDadosEncontradosPerfil(
                    nome = usuarioLogado.nomeUsuarioLogado,
                    telefone = usuarioLogado.telefoneCelular,
                    email = usuarioLogado.email
                )
            }

            val usuarioApi: UsuarioApi = UsuarioApi()
            usuarioApi.buscarDadosUsuarioLogado(
                idUsuarioLogado,
                onProcessandoRequisicao = onRealizandoRequisicaoConsultaClienteLogado,
                onErroRequisicao = onErroRequisicaoConsultaClienteLogado,
                onSucessoRequisicao = onSucessoRequisicaoConsultaClienteLogado
            )
        } catch (e: Exception) {
            this.loader.finalizarLoader()

            Log.e("erro_buscar_dados_usuario_logado", e.message.toString())
        }

    }

    private fun preencherCamposDadosEncontradosPerfil(nome: String, email: String, telefone: String) {
        this.edtNome.setText(nome)
        this.edtTelefone.setText(telefone)
        this.edtEmail.setText(email)
    }

    private fun apresentarNotificacaoErro(msg: String) {
        val snackBarErro: Snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)

        snackBarErro.setBackgroundTint(getColor(android.R.color.holo_red_dark))
        snackBarErro.show()
    }

    private fun validarCampos(): Boolean {
        val nome: String = this.edtNome.text.toString().trim()
        val email: String = this.edtEmail.text.toString().trim()
        val telefone: String = this.edtTelefone.text.toString().trim()

        val ok: Boolean = if (nome.isEmpty()) {
            this.apresentarNotificacaoErro("Informe seu nome.")

            false
        } else if (nome.length < 3) {
            this.apresentarNotificacaoErro("O nome deve possuir no mínimo três caracteres.")

            false
        } else if (email.isEmpty()) {
            this.apresentarNotificacaoErro("Informe seu e-mail.")

            false
        } else if (!validarEmail(email)) {
            this.apresentarNotificacaoErro("E-mail inválido.")

            false
        } else if (telefone.isEmpty()) {
            this.apresentarNotificacaoErro("Informe o telefone.")

            false
        } else {

            true
        }

        return ok
    }

    private fun alterarPerfil() {

        try {

            if (this.validarCampos()) {
                val novoNome: String = this.edtNome.text.toString().trim()
                val novoEmail: String = this.edtEmail.text.toString().trim()
                val novoTelefone: String = this.edtTelefone.text.toString().trim()

                this.alterarPerfilServidor(novoNome, novoEmail, novoTelefone)
            }

        } catch (e: Exception) {
            this.apresentarNotificacaoErro("Ocorreu o seguinte erro: ${ e.message.toString() }")
        }

    }

    private fun alterarPerfilServidor(novoNome: String, novoEmail: String, novoTelefone: String) {

    }

    private fun fecharNotificacaoEditarPerfil() {
        this.notificacaoEditarPerfil.visibility = View.GONE
    }

    private fun retornar() {

    }

    private fun apresentarSubMenu() {

    }

}