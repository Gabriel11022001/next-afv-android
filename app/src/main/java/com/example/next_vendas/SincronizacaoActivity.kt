package com.example.next_vendas

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.api.ClienteApi
import com.example.next_vendas.api.ConfiguracaoApi
import com.example.next_vendas.api.IOnObterTotais
import com.example.next_vendas.api.IOnSincronizar
import com.example.next_vendas.api.TotalEntidadesApi
import com.example.next_vendas.dao.ClienteDAO
import com.example.next_vendas.model_servico.TotalModelServico

class SincronizacaoActivity : AppCompatActivity(), OnClickListener {

    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var btnSincronizar: AppCompatButton
    private lateinit var linearContainerProgresso: LinearLayout
    private lateinit var txtProgressoSincronizacao: TextView
    private lateinit var progressBarSincronizacao: ProgressBar

    // controlar total de cada entidade
    private var totalClientesSincronizar: Int = 0
    private var totalProdutosSincronizar: Int = 0
    private var totalCategoriasProdutosSincronizar: Int = 0
    private var totalVendasSincronizar: Int = 0

    private lateinit var sharedPreferencesConfiguracoes: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sincronizacao)

        // mapear elementos de interface
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnSincronizar = findViewById(R.id.btn_sincronizar)
        this.linearContainerProgresso = findViewById(R.id.linear_container_loader)
        this.txtProgressoSincronizacao = findViewById(R.id.txt_progresso_sincronizacao)
        this.progressBarSincronizacao = findViewById(R.id.progress_bar_sincronizacao)

        // mapear eventos
        this.btnSincronizar.setOnClickListener(this)

        this.txtTitulo.text = "Sincronização"
        this.btnAdicionar.visibility = View.GONE
        this.btnFiltro.visibility = View.GONE

        this.sharedPreferencesConfiguracoes = getSharedPreferences("CONFIGURACOES", MODE_PRIVATE)

        this.linearContainerProgresso.visibility = GONE
        this.txtProgressoSincronizacao.text = ""
        this.progressBarSincronizacao.max = 100
    }

    private fun buscarTotais() {
        val totalEntidadesApi = TotalEntidadesApi()

        totalEntidadesApi.getTotais(object : IOnObterTotais {

            override fun sincronizando() {
                txtProgressoSincronizacao.text = "Obtendo totais..."
                progressBarSincronizacao.progress = 0
            }

            override fun terminouObterTotais(
                totalClientes: Int,
                totalCategorias: Int,
                totalProdutos: Int,
                totalVendas: Int
            ) {
                txtProgressoSincronizacao.text = ""
                progressBarSincronizacao.progress = 100

                totalClientesSincronizar = totalClientes
                totalCategoriasProdutosSincronizar = totalCategorias
                totalProdutosSincronizar = totalProdutos
                totalVendasSincronizar = totalVendas

                Log.d("total_clientes", totalClientes.toString())

                // sincronizar os clientes
                sincronizarClientes()
            }

            override fun erro(mensagem: String) {
                linearContainerProgresso.visibility = GONE
                txtProgressoSincronizacao.text = ""
                progressBarSincronizacao.progress = 0
            }

        })
    }

    private fun sincronizar() {

        try {
            // iniciar sincronizando as configurações
            this.sincronizarConfiguracoes()
        } catch (e: Exception) {
            Log.e("erro_sincronizacao", "Erro na sincronização: ${ e.message.toString() }")
        }

    }

    // de configurações -> buscar os totais
    private fun sincronizarConfiguracoes() {
        val configuracaoApi: ConfiguracaoApi = ConfiguracaoApi(
            this.sharedPreferencesConfiguracoes
        )
        configuracaoApi.sincronizarConfiguracoes(object : IOnSincronizar {

            override fun sincronizando() {
                linearContainerProgresso.visibility = VISIBLE
                txtProgressoSincronizacao.text = "Sincronizando as configurações..."
                progressBarSincronizacao.progress = 0
            }

            override fun terminouSincronizar() {
                progressBarSincronizacao.progress = 100

                buscarTotais()
            }

            override fun erro(mensagemErro: String) {
                progressBarSincronizacao.progress = 0
                txtProgressoSincronizacao.text = ""
                linearContainerProgresso.visibility = GONE

                Toast.makeText(applicationContext, "Erro: $mensagemErro", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    // de clientes -> para categorias de produtos
    private fun sincronizarClientes() {

        try {
            progressBarSincronizacao.progress = 0
            txtProgressoSincronizacao.text = "Sincronizando os clientes..."

            val clienteDAO = ClienteDAO(this)

            ClienteApi.sincronizarClientes(this.totalClientesSincronizar, clienteDAO, object : IOnSincronizar {

                override fun sincronizando() {
                    val progressoAtual = progressBarSincronizacao.progress

                    if (progressoAtual < 95) {
                        progressBarSincronizacao.progress = progressoAtual + 1
                    }

                }

                override fun terminouSincronizar() {
                    txtProgressoSincronizacao.text = ""
                    progressBarSincronizacao.progress = 100
                }

                override fun erro(mensagemErro: String) {
                    txtProgressoSincronizacao.text = ""
                    progressBarSincronizacao.progress = 0
                    linearContainerProgresso.visibility = GONE
                }

            })

        } catch (e: Exception) {
            Toast.makeText(this, "Erro sincronizar clientes: ${e.message.toString()}", Toast.LENGTH_LONG)
                .show()
        }

    }

    // de categorias de produtos -> para produtos
    private fun sincronizarCategoriasProdutos() {

    }

    // de produtos -> para vendas
    private fun sincronizarProdutos() {

    }

    // terminou a sincronização na sinc de vendas
    private fun sincronizarVendas() {

    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_sincronizar) {
            this.sincronizar()
        }

    }

}