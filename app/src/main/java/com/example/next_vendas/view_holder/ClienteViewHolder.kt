package com.example.next_vendas.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.next_vendas.R

class ClienteViewHolder(view: View): ViewHolder(view) {

    val txtClienteNome: TextView = view.findViewById(R.id.txt_nome_cliente)
    val txtClienteDocumento: TextView = view.findViewById(R.id.txt_documento_cliente)
    val imgFotoCliente: ImageView =  view.findViewById(R.id.img_foto_cliente)

}