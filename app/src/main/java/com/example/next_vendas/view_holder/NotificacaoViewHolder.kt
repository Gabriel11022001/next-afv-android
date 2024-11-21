package com.example.next_vendas.view_holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.next_vendas.R

class NotificacaoViewHolder(view: View): ViewHolder(view) {

    var txtMensagemNotificacao: TextView = view.findViewById(R.id.txt_mensagem_notificacao)
    var txtDataCadastroNotificacao: TextView = view.findViewById(R.id.txt_data_cadastro_notificacao)

}