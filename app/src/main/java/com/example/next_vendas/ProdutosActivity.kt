package com.example.next_vendas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.next_vendas.adapter.ProdutoAdapter
import com.example.next_vendas.dao.ProdutoDAO
import com.example.next_vendas.dialog.DialogFiltro
import com.example.next_vendas.listener.IOnVisualizar
import com.example.next_vendas.model.Filtro
import com.example.next_vendas.model.Produto
import java.util.ArrayList

class ProdutosActivity : AppCompatActivity(), OnClickListener {

    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnRetornar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var recyclerProdutos: RecyclerView
    private lateinit var produtoAdapter: ProdutoAdapter
    private lateinit var produtoDAO: ProdutoDAO
    private lateinit var dialogFiltroProdutos: DialogFiltro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)

        this.produtoDAO = ProdutoDAO(this)

        // mapear elementos de interface
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnRetornar = findViewById(R.id.btn_retornar)
        this.recyclerProdutos = findViewById(R.id.recycler_produtos)

        this.txtTitulo.text = "Produtos"
        this.btnFiltro.visibility = View.VISIBLE
        this.btnAdicionar.visibility = View.GONE

        // mapear eventos
        this.btnRetornar.setOnClickListener(this)
        this.btnFiltro.setOnClickListener(this)

        // configurar listener do evento de visualizar produto
        val iOnVisualizarProduto: IOnVisualizar = object : IOnVisualizar {

            override fun visualizar(idEntidade: Int) {

            }

        }

        // configurar adapter de produtos e RecyclerView
        this.produtoAdapter = ProdutoAdapter(this, iOnVisualizarProduto)
        this.recyclerProdutos.layoutManager = LinearLayoutManager(this)
        this.recyclerProdutos.adapter = this.produtoAdapter

        val validarFormFiltroProdutos: (Double, Double) -> Boolean = { precoDe, precoAte ->
            var valido = true

            if (precoDe < 0) {
                valido = false
                Toast.makeText(this, "Preço inicial do filtro inválido.", Toast.LENGTH_LONG)
                    .show()
            } else if (precoAte < 0) {
                valido = false
                Toast.makeText(this, "Preço final do filtro inválido.", Toast.LENGTH_LONG)
                    .show()
            } else if (precoDe > 0 && precoAte == 0.0) {
                valido = false
                Toast.makeText(this, "Informe o preço final.", Toast.LENGTH_LONG)
                    .show()
            } else if (precoAte < precoDe) {
                valido = false
                Toast.makeText(this, "O preço final deve ser maior que o preço inicial.", Toast.LENGTH_LONG)
                    .show()
            }

            valido
        }

        val onFiltrarProdutos: (Filtro) -> Unit = { filtro ->

            try {
                val precoDe = filtro.argumentosFiltro[ 1 ]
                val precoAte = filtro.argumentosFiltro[ 2 ]

                if (validarFormFiltroProdutos(
                        if (precoDe.isBlank()) { 0.0 } else { precoDe.toDouble() },
                        if (precoAte.isBlank()) { 0.0 } else { precoAte.toDouble() }
                    )) {
                    // filtrar os produtos
                    val produtos = produtoDAO.filtrarProdutos(filtro.where, filtro.argumentosFiltro)

                    if (produtos.size > 0) {
                        Log.d("filtro_produtos", "Encontrou ${ produtos.size } produtos com os parâmetros passados no filtro.")

                        this.produtoAdapter.setProdutos(produtos)
                    } else {
                        Log.d("filtro_produtos", "[]")
                    }

                }

            } catch (e: Exception) {
                Log.e("erro_filtro_produtos", e.message.toString())

                Toast.makeText(this, "Erro no filtro de produtos.", Toast.LENGTH_LONG)
                    .show()
            }

        }

        this.dialogFiltroProdutos = DialogFiltro(
            this,
            "produtos",
            "Filtrar produtos",
            onFiltrarProdutos
        )
    }

    override fun onStart() {
        super.onStart()
        // listar produtos cadastrados na base de dados
        this.listarProdutos()
    }

    private fun listarProdutos() {

        try {
            val produtos = this.produtoDAO.listarProdutos()

            if (produtos.size > 0) {
                this.produtoAdapter.setProdutos(produtos)
            } else {
                
            }

        } catch (e: Exception) {

        }

    }

    fun retornar() {

    }

    private fun filtrarProdustos() {
        this.dialogFiltroProdutos.apresentarFiltro()
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_retornar) {
            // retornar
            this.retornar()
        } else if (p0!!.id == R.id.btn_filtro_menu) {
            // filtrar produtos
            this.filtrarProdustos()
        }

    }

}