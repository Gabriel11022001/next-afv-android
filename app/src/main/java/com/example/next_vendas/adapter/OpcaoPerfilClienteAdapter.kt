package com.example.next_vendas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.next_vendas.R
import com.example.next_vendas.view_holder.OpcaoPerfilClienteViewHolder

class OpcaoPerfilClienteAdapter(
    private val contexto: Context,
    private val opcoesPerfilCliente: ArrayList<String>,
    private val iOnRealizarOperacaoPerfilCliente: (String) -> Unit
): Adapter<OpcaoPerfilClienteViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OpcaoPerfilClienteViewHolder {
        val layoutInflater = LayoutInflater.from(this.contexto)
        val view = layoutInflater.inflate(R.layout.opcao_perfil_cliente_adapter, null, false)

        return OpcaoPerfilClienteViewHolder(view)
    }

    override fun getItemCount(): Int {

        return this.opcoesPerfilCliente.size
    }

    override fun onBindViewHolder(holder: OpcaoPerfilClienteViewHolder, position: Int) {
        val opcao = this.opcoesPerfilCliente[ position ]

        holder.txtDescricaoOpcao.text = opcao

        if (opcao == "Editar") {
            holder.imgOpcaoPerfilCliente.setImageResource(R.drawable.ic_editar_azul)
        } else if (opcao == "Deletar") {
            holder.imgOpcaoPerfilCliente.setImageResource(R.drawable.ic_deletar_cliente_perfil)
        } else if (opcao == "Vendas") {
            holder.imgOpcaoPerfilCliente.setImageResource(R.drawable.ic_vendas_cliente_perfil)
        } else {
            holder.imgOpcaoPerfilCliente.setImageResource(R.drawable.ic_realizar_venda_cliente_perfil)
        }

        holder.itemView.setOnClickListener {
            iOnRealizarOperacaoPerfilCliente(opcao)
        }
    }

}