package com.example.next_vendas.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.next_vendas.R

class OpcaoPerfilClienteViewHolder(view: View): ViewHolder(view) {

    val txtDescricaoOpcao: TextView = view.findViewById(R.id.txt_opcao_perfil_cliente)
    val imgOpcaoPerfilCliente: ImageView = view.findViewById(R.id.img_opcao_perfil_cliente)

}