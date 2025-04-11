package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.next_vendas.listener.IOnConfirmarListener
import com.example.next_vendas.utils.AlertaConfirmarVoltar
import com.example.next_vendas.utils.MenuOpcoesCabecalho

class HomeActivity : AppCompatActivity(), OnClickListener {

    private lateinit var menuOpcoesCabecalho: MenuOpcoesCabecalho
    private lateinit var alertaRetornar: AlertaConfirmarVoltar
    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var btnVoltar: ImageButton
    private lateinit var btnMenuCabecalho: ImageButton
    private lateinit var cardGestaoClientes: ConstraintLayout
    private lateinit var cardGestaoProdutos: ConstraintLayout
    private lateinit var cardRealizarVenda: ConstraintLayout
    private lateinit var carPerfil: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // mapear elementos de interface
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnVoltar = findViewById(R.id.btn_retornar)
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnMenuCabecalho = findViewById(R.id.btn_menu_opcoes)
        this.cardGestaoClientes = findViewById(R.id.constraint_card_gestao_clientes)
        this.cardGestaoProdutos = findViewById(R.id.constraint_card_gestao_produtos)
        this.cardRealizarVenda = findViewById(R.id.constraint_card_realizar_venda)
        this.carPerfil = findViewById(R.id.constraint_card_perfil)

        // mapear eventos
        this.btnVoltar.setOnClickListener(this)
        this.cardGestaoClientes.setOnClickListener(this)
        this.cardGestaoProdutos.setOnClickListener(this)
        this.cardRealizarVenda.setOnClickListener(this)
        this.carPerfil.setOnClickListener(this)
        this.btnMenuCabecalho.setOnClickListener(this)

        this.btnFiltro.visibility = View.GONE
        this.btnAdicionar.visibility = View.GONE

        this.txtTitulo.text = "Home"

        // configurar listener de confirmar para sair do app
        val confirmarListenerSairApp = object : IOnConfirmarListener {

            override fun confirmar() {
                finish()
            }

        }

        this.alertaRetornar = AlertaConfirmarVoltar(this, "Deseja sair do aplicativo?", confirmarListenerSairApp)

        // listener para realizar logout
        val onLogout: () -> Unit = {
            // implementar lógica do logout
            val intentLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intentLogin)
            finish()
        }

        // listener para realizar redirecionamento para tela "sobre"
        val onRedirecionarSobre: () -> Unit = {
            val intentRedirecionarSobre = Intent(this, SobreActivity::class.java)
            startActivity(intentRedirecionarSobre)
            finish()
        }

        // callback que redirecionar o usuário para a tela com a listagem das notificações
        val onRedirecionarNotificacoes: () -> Unit = {
            val intentRedirecionarNotificacoes: Intent = Intent(this, NotificacoesActivity::class.java)
            startActivity(intentRedirecionarNotificacoes)
            finish()
        }

        this.menuOpcoesCabecalho = MenuOpcoesCabecalho(
            this,
            onLogout,
            onRedirecionarSobre,
            onRedirecionarNotificacoes
        )
    }

    private fun voltar() {

        try {
            this.alertaRetornar.apresentar()
        } catch (e: Exception) {
            Log.e("erro_sair", e.message.toString())
        }

    }

    override fun onBackPressed() {
        this.voltar()
    }

    private fun redirecionar(tela: String) {

        if (tela == "gestao_clientes") {
            startActivity(Intent(this, ClientesActivity::class.java))
        } else if (tela == "gestao_produtos") {
            startActivity(Intent(this, ProdutosActivity::class.java))
        } else if (tela == "realizar_venda") {
            startActivity(Intent(this, VendaActivity::class.java))
        } else if (tela == "perfil") {
            startActivity(Intent(this, PerfilActivity::class.java))
        }

        finish()
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_retornar) {
            this.voltar()
        } else if (p0!!.id == R.id.constraint_card_gestao_clientes) {
            this.redirecionar("gestao_clientes")
        } else if (p0!!.id == R.id.constraint_card_gestao_produtos) {
            this.redirecionar("gestao_produtos")
        } else if (p0!!.id == R.id.constraint_card_realizar_venda) {
            this.redirecionar("realizar_venda")
        } else if (p0!!.id == R.id.btn_menu_opcoes) {
            // abrir menu com opções do cabeçalho
            this.menuOpcoesCabecalho.apresentarMenu(p0!!)
        } else if (p0!!.id == R.id.constraint_card_perfil) {
            this.redirecionar("perfil")
        }

    }

}