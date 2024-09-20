package com.example.next_vendas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.next_vendas.R
import com.example.next_vendas.listener.IOnVisualizar
import com.example.next_vendas.model.Produto
import com.example.next_vendas.view_holder.ProdutoViewHolder
import java.util.ArrayList

class ProdutoAdapter(
    private val contexto: Context,
    private val iOnVisualizarProduto: IOnVisualizar
): Adapter<ProdutoViewHolder>() {

    private var produtos: ArrayList<Produto> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(this.contexto)
        val view: View = layoutInflater.inflate(R.layout.produto_adapter, parent, false)

        return ProdutoViewHolder(view)
    }

    override fun getItemCount(): Int {

        return this.produtos.size
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto: Produto = this.produtos[ position ]
        holder.txtNomeProduto.text = produto.nome.uppercase()
        holder.txtCodigoProduto.text = produto.codigo
        holder.txtPrecoProduto.text = "R$${ produto.preco }"

        if (produto.fotoProduto != "") {
            // o produto possui foto, apresentar a foto
        }

        // evento de visualizar os dados do produto
        holder.itemView.setOnClickListener {
            this.iOnVisualizarProduto.visualizar(produto.id)
        }
    }

    fun setProdutos(produtos: ArrayList<Produto>) {
        this.produtos = produtos
        super.notifyDataSetChanged()
    }

}