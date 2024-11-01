package com.example.next_vendas.view_holder

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.next_vendas.R

class ProdutoAdicionarCarrinhoViewHolder(view: View): ViewHolder(view) {

    var txtNomeProdutoAdicionarCarrinho: TextView = view.findViewById(R.id.txt_nome_produto_adicionar_carrinho)
    var txtPrecoUnitarioProdutoAdicionarCarrinho: TextView = view.findViewById(R.id.txt_preco_unitario)
    var txtQuantidadeUnidadesProdutoNoCarrinho: TextView = view.findViewById(R.id.txt_quantidade_unidades_carrinho)
    var btnIncrementarUnidadesCarrinho: ImageButton = view.findViewById(R.id.btn_incrementar_unidades_produto_carrinho)
    var btnDecrementarUnidadesCarrinho: ImageButton = view.findViewById(R.id.btn_decrementar_unidades_produto_carrinho)

}