package com.example.next_vendas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.next_vendas.adapter.NotificacaoAdapter
import com.example.next_vendas.api.NotificacaoApi
import com.example.next_vendas.model_servico.NotificacaoModelServico
import com.example.next_vendas.utils.LoaderNextVendas
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotificacoesActivity : AppCompatActivity() {

    private lateinit var txtTituloMenu: TextView
    private lateinit var btnRetornar: ImageButton
    private lateinit var btnAdicionarMenu: ImageButton
    private lateinit var btnFiltroMenu: ImageButton
    private lateinit var opcoesMenu: ImageButton
    private lateinit var recyclerNotificacoes: RecyclerView
    private lateinit var btnFiltroNotificacoes: FloatingActionButton
    private lateinit var notificacaoAdapter: NotificacaoAdapter
    private lateinit var loaderNextVendas: LoaderNextVendas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacoes)

        // mapear elementos de interface
        this.mapearElementosInterface()

        // mapear eventos
        this.mapearEventos()

        this.txtTituloMenu.text = "Notificações"
        this.btnAdicionarMenu.visibility = View.GONE
        this.btnFiltroMenu.visibility = View.GONE

        this.notificacaoAdapter = NotificacaoAdapter(this)
        this.recyclerNotificacoes.layoutManager = LinearLayoutManager(this)
        this.recyclerNotificacoes.adapter = this.notificacaoAdapter

        this.loaderNextVendas = LoaderNextVendas()
    }

    override fun onResume() {
        super.onResume()

        // buscar todas as notificações do servidor
        this.buscarTodasNotificacoes()
    }

    private fun mapearElementosInterface() {
        this.btnFiltroNotificacoes = findViewById(R.id.btn_filtro_notificacoes)
        this.recyclerNotificacoes = findViewById(R.id.recycler_notificacoes)
        this.txtTituloMenu = findViewById(R.id.txt_titulo)
        this.btnRetornar = findViewById(R.id.btn_retornar)
        this.btnAdicionarMenu = findViewById(R.id.btn_adicionar_menu)
        this.btnFiltroMenu = findViewById(R.id.btn_filtro_menu)
        this.opcoesMenu = findViewById(R.id.btn_menu_opcoes)
    }

    private fun mapearEventos() {
        this.btnFiltroNotificacoes.setOnClickListener { this.apresentarFiltroNotificacoes() }
        this.btnRetornar.setOnClickListener { this.retornar() }
        this.opcoesMenu.setOnClickListener { this.apresentarMenuOpcoes() }
    }

    private fun apresentarLoader() {
        this.loaderNextVendas.show(supportFragmentManager,  "LoaderNextVendas")
    }

    private fun esconderLoader() {
        this.loaderNextVendas.dismiss()
    }

    private fun buscarTodasNotificacoes() {

        try {

            val onRealizandoRequisicaoConsultarNotificacoes: () -> Unit = {
                // apresentar loader
                this.apresentarLoader()
            }

            val onErroRequisicaoConsultarNotificacoes: (String) -> Unit = { msgErro ->
                // apresentar alerta de erro
                this.esconderLoader()
            }

            val onSucessoConsultarNotificacoes: (ArrayList<NotificacaoModelServico>) -> Unit = { notificacoes ->
                // apresentar notificações encontradas
                this.esconderLoader()
                this.notificacaoAdapter.setNotificacoes(notificacoes)
                this.notificacaoAdapter.notifyDataSetChanged()
            }

            val notificacaoApi: NotificacaoApi = NotificacaoApi()

            notificacaoApi.buscarNotificacoes(
                onRealizandoRequisicao = onRealizandoRequisicaoConsultarNotificacoes,
                onErroRequisicao = onErroRequisicaoConsultarNotificacoes,
                onSucessoRequisicao = onSucessoConsultarNotificacoes
            )

        } catch (e: Exception) {
            Log.e("erro_listar_notificacoes", e.message.toString())
        }

    }

    private fun apresentarFiltroNotificacoes() {

    }

    private fun filtrarNotificacoes() {

    }

    private fun retornar() {

    }

    private fun apresentarMenuOpcoes() {

    }

    override fun onBackPressed() {
        this.retornar()
    }

}