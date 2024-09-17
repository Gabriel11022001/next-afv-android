package com.example.next_vendas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.next_vendas.R
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.utils.Constantes
import com.example.next_vendas.view_holder.ClienteViewHolder
import java.util.ArrayList

class ClienteAdapter(
    val contexto: Context
): Adapter<ClienteViewHolder>() {

    private var clientes: ArrayList<Pessoa> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(this.contexto)
        val view = layoutInflater.inflate(R.layout.cliente_adapter, parent, false)

        return ClienteViewHolder(view)
    }

    override fun getItemCount(): Int {

        return this.clientes.size
    }

    override fun onBindViewHolder(clienteViewHolder: ClienteViewHolder, position: Int) {
        val cliente: Pessoa = this.clientes[ position ]

        if (cliente.tipoPessoa == Constantes.FISICA) {
            // o cliente é uma pf, apresentar o cpf e o nome
            clienteViewHolder.txtClienteDocumento.text = cliente.cpf.uppercase()
            clienteViewHolder.txtClienteNome.text = cliente.nome
        } else {
            // o cliente é uma pj, apresentar o cnpj e a razão social
            clienteViewHolder.txtClienteDocumento.text = cliente.cnpj.uppercase()
            clienteViewHolder.txtClienteNome.text = cliente.razaoSocial
        }

    }

    fun setClientes(clientes: ArrayList<Pessoa>) {
        this.clientes = clientes
        super.notifyDataSetChanged()
    }

}