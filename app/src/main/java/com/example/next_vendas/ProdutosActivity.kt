package com.example.next_vendas

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.next_vendas.adapter.ProdutoAdapter
import com.example.next_vendas.dao.ProdutoDAO
import com.example.next_vendas.listener.IOnVisualizar
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
        this.btnFiltro.visibility = View.GONE
        this.btnAdicionar.visibility = View.GONE

        // mapear eventos
        this.btnRetornar.setOnClickListener(this)

        // configurar listener do evento de visualizar produto
        val iOnVisualizarProduto: IOnVisualizar = object : IOnVisualizar {

            override fun visualizar(idEntidade: Int) {

            }

        }

        // configurar adapter de produtos e RecyclerView
        this.produtoAdapter = ProdutoAdapter(this, iOnVisualizarProduto)
        this.recyclerProdutos.layoutManager = LinearLayoutManager(this)
        this.recyclerProdutos.adapter = this.produtoAdapter
    }

    override fun onStart() {
        super.onStart()
        // listar produtos cadastrados na base de dados
        this.listarProdutos()
    }

    fun listarProdutos() {

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

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_retornar) {
            // retornar
            this.retornar()
        }

    }

}