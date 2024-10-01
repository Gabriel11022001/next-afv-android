package com.example.next_vendas

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.api.ConfiguracaoApi
import com.example.next_vendas.api.IOnSincronizar

class SincronizacaoActivity : AppCompatActivity(), OnClickListener {

    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var btnSincronizar: AppCompatButton

    // controlar total de cada entidade
    private var totalClientes: Int = 0
    private var totalProdutos: Int = 0
    private var totalCategoriasProdutos: Int = 0
    private var totalVendas: Int = 0
    private var totalListasPreco: Int = 0

    private lateinit var sharedPreferencesConfiguracoes: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sincronizacao)

        // mapear elementos de interface
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnSincronizar = findViewById(R.id.btn_sincronizar)

        // mapear eventos
        this.btnSincronizar.setOnClickListener(this)

        this.txtTitulo.text = "Sincronização"
        this.btnAdicionar.visibility = View.GONE
        this.btnFiltro.visibility = View.GONE

        this.sharedPreferencesConfiguracoes = getSharedPreferences("CONFIGURACOES", MODE_PRIVATE)
    }

    private fun buscarTotal(entidade: String) {

        if (entidade == "clientes") {

        } else if (entidade == "produtos") {

        } else if (entidade == "vendas") {

        } else if (entidade == "listas_preco") {

        } else if (entidade == "categorias_produtos") {

        }

    }

    private fun sincronizar() {

        try {
            // iniciar sincronizando as configurações
            this.sincronizarConfiguracoes()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } catch (e: Exception) {
            Log.e("erro_sincronizacao", "Erro na sincronização: ${ e.message.toString() }")
        }

    }

    // de configurações -> para clientes
    private fun sincronizarConfiguracoes() {
        val configuracaoApi: ConfiguracaoApi = ConfiguracaoApi(
            this.sharedPreferencesConfiguracoes
        )
        configuracaoApi.sincronizarConfiguracoes(object : IOnSincronizar {

            override fun sincronizando() {
                Log.d("sincronizar_configuracoes", "Sincronizando configurações...")
            }

            override fun terminouSincronizar() {
                Log.d("sincronizar_configuracoes", "Terminou de sincronizar configurações...")
            }

            override fun erro(mensagemErro: String) {
                Log.e("sincronizar_configuracoes", mensagemErro)
                Toast.makeText(applicationContext, "Erro: $mensagemErro", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    // de clientes -> para categorias de produtos
    private fun sincronizarClientes() {

    }

    // de categorias de produtos -> para produtos
    private fun sincronizarCategoriasProdutos() {

    }

    // de produtos -> para listas de preço
    private fun sincronizarProdutos() {

    }

    // de listas de preço -> para vendas
    private fun sincronizarListasPreco() {

    }

    // terminou a sincronização na sinc de vendas
    private fun sincronizarVendas() {

    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_sincronizar) {
            // startActivity(Intent(this, HomeActivity::class.java))
            // finish()
            this.sincronizar()
        }

    }

}