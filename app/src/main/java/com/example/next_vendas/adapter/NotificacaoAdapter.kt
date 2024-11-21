package com.example.next_vendas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.next_vendas.R
import com.example.next_vendas.model_servico.NotificacaoModelServico
import com.example.next_vendas.view_holder.NotificacaoViewHolder

class NotificacaoAdapter(private val contexto: Context): Adapter<NotificacaoViewHolder>() {

    private var notificacoes: ArrayList<NotificacaoModelServico> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacaoViewHolder {
        val layoutInflater = LayoutInflater.from(this.contexto)
        val view: View = layoutInflater.inflate(R.layout.notificacao_adapter, parent, false)

        return NotificacaoViewHolder(view)
    }

    override fun getItemCount(): Int {

        return this.notificacoes.size
    }

    override fun onBindViewHolder(holder: NotificacaoViewHolder, position: Int) {
        val notificacao = this.notificacoes[ position ]

        holder.txtMensagemNotificacao.text = notificacao.mensagem
        holder.txtDataCadastroNotificacao.text = notificacao.dataCadastro
    }

    fun setNotificacoes(notificacoes: ArrayList<NotificacaoModelServico>) {
        this.notificacoes = notificacoes
    }

}