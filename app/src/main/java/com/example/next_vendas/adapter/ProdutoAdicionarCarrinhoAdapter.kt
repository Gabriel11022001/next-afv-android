package com.example.next_vendas.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.next_vendas.R
import com.example.next_vendas.model.ProdutoCarrinho
import com.example.next_vendas.view_holder.ProdutoAdicionarCarrinhoViewHolder
import java.util.ArrayList

class ProdutoAdicionarCarrinhoAdapter(
    private val contexto: Context,
    // listener para incrementar a quantidade de unidades do produto no carrinho
    private val onIncrementarUnidadesCarrinho: (ProdutoCarrinho) -> Unit,
    // listener para decrementar a quantidade de unidades do produto no carrinho
    private val onDecrementarUnidadesCarrinho: (ProdutoCarrinho) -> Unit
): Adapter<ProdutoAdicionarCarrinhoViewHolder>() {

    private var produtosCarrinho: ArrayList<ProdutoCarrinho> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoAdicionarCarrinhoViewHolder {
        val layoutInflater = LayoutInflater.from(this.contexto)
        val view = layoutInflater.inflate(R.layout.produto_adicionar_carrinho_adapter, parent, false)

        return ProdutoAdicionarCarrinhoViewHolder(view)
    }

    override fun getItemCount(): Int {

        return this.produtosCarrinho.size
    }

    override fun onBindViewHolder(holder: ProdutoAdicionarCarrinhoViewHolder, position: Int) {
        val produtoCarrinho = this.produtosCarrinho[ position ]

        holder.txtNomeProdutoAdicionarCarrinho.text = produtoCarrinho.nomeProdutoCarrinho
        holder.txtPrecoUnitarioProdutoAdicionarCarrinho.text = "Preço unitário: R$${ produtoCarrinho.precoUnitarioProdutoCarrinho }"
        holder.txtQuantidadeUnidadesProdutoNoCarrinho.text = "unidades carrinho: ${ produtoCarrinho.quantidadeUnidadesProdutoCarrinho }"

        holder.btnIncrementarUnidadesCarrinho.setOnClickListener { this.onIncrementarUnidadesCarrinho(produtoCarrinho) }

        holder.btnDecrementarUnidadesCarrinho.setOnClickListener { this.onDecrementarUnidadesCarrinho(produtoCarrinho) }
    }

    fun setProdutosAdicionarCarrinho(produtosAdicionarCarrinho: ArrayList<ProdutoCarrinho>) {
        this.produtosCarrinho = produtosAdicionarCarrinho
    }

}