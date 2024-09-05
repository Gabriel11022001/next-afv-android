package com.example.next_vendas.dao

import android.content.ContentValues
import android.content.Context
import com.example.next_vendas.model.Endereco
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.model.PessoaFisica
import com.example.next_vendas.model.PessoaJuridica
import com.example.next_vendas.utils.Constantes
import java.util.ArrayList

class ClienteDAO(contexto: Context): BaseDAO(contexto) {

    fun cadastrar(cliente: Pessoa): Int {
        var idCliente: Int = 0
        val tipoPessoa: String = cliente.tipoPessoa
        val valuesCliente: ContentValues = ContentValues()

        if (cliente.id != 0) {
            valuesCliente.put("id", cliente.id)
        }

        valuesCliente.put("telefone_celular", cliente.telefoneCelular)
        valuesCliente.put("telefone_fixo", cliente.telefoneFixo)
        valuesCliente.put("telefone_complementar", cliente.telefoneComplementar)
        valuesCliente.put("data_cadastro", cliente.dataCadastro)
        valuesCliente.put("email", cliente.email)

        if (tipoPessoa == Constantes.FISICA) {
            // o cliente é uma pf
            val clientePessoaFisica: PessoaFisica = cliente as PessoaFisica
        } else {
            // o cliente é uma pj
            val clientePessoaJuridica: PessoaJuridica = cliente as PessoaJuridica
        }

        // registrar endereço do cliente
        this.cadastrarEnderecoCliente(cliente.endereco, idCliente)

        return idCliente
    }

    fun cadastrarEnderecoCliente(endereco: Endereco, idCliente: Int): Int {
        var idEndereco: Int = 0

        return idEndereco
    }

    fun listarClientes(): ArrayList<Pessoa> {
        val clientes: ArrayList<Pessoa> = ArrayList()

        return clientes
    }

    fun buscarEnderecoCliente(idCliente: Int): Endereco? {

        return null
    }

}