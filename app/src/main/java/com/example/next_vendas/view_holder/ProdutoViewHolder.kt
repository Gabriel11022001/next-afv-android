package com.example.next_vendas.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.next_vendas.R

class ProdutoViewHolder(view : View): ViewHolder(view) {

    val imgProduto: ImageView = view.findViewById(R.id.img_produto)
    val txtNomeProduto: TextView = view.findViewById(R.id.txt_nome_produto)
    val txtCodigoProduto: TextView = view.findViewById(R.id.txt_codigo_produto)
    val txtPrecoProduto: TextView = view.findViewById(R.id.txt_preco_produto)

}