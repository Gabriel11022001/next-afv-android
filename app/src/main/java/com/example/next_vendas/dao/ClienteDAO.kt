package com.example.next_vendas.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.next_vendas.model.Endereco
import com.example.next_vendas.model.Pessoa
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
            valuesCliente.put("cpf", cliente.cpf)
            valuesCliente.put("data_nascimento", cliente.dataNascimento)
            valuesCliente.put("nome_completo", cliente.nome)
            valuesCliente.put("sexo", cliente.sexo)
            valuesCliente.put("tipo_pessoa", cliente.tipoPessoa)
        } else {
            valuesCliente.put("cnpj", cliente.cnpj)
            valuesCliente.put("razao_social", cliente.razaoSocial)
            valuesCliente.put("site", cliente.site)
            valuesCliente.put("inscricao_estadual", cliente.inscricaoEstadual)
            valuesCliente.put("tipo_pessoa", cliente.tipoPessoa)
        }

        if (cliente.id != 0) {
            super.bancoDados.update("tb_clientes", valuesCliente, "id = ?", arrayOf( cliente.id.toString() ))
            idCliente = cliente.id
        } else {
            idCliente = super.bancoDados.insert("tb_clientes", null, valuesCliente).toInt()
        }

        // registrar endereço do cliente
        this.cadastrarEnderecoCliente(cliente.endereco, idCliente)

        return idCliente
    }

    fun cadastrarEnderecoCliente(endereco: Endereco, idCliente: Int): Int {
        var idEndereco: Int = 0

        val contentValuesCadastrarEndereco: ContentValues = ContentValues()
        contentValuesCadastrarEndereco.put("cep", endereco.cep)
        contentValuesCadastrarEndereco.put("endereco", endereco.endereco)
        contentValuesCadastrarEndereco.put("bairro", endereco.bairro)
        contentValuesCadastrarEndereco.put("cidade", endereco.cidade)
        contentValuesCadastrarEndereco.put("complemento", endereco.complemento)
        contentValuesCadastrarEndereco.put("estado", endereco.uf)
        contentValuesCadastrarEndereco.put("numero", endereco.numero)
        contentValuesCadastrarEndereco.put("cliente_id", idCliente)

        if (endereco.id != 0) {
            contentValuesCadastrarEndereco.put("id", endereco.id)
        }

        if (endereco.id != 0) {
            // atualizar endereço
            super.bancoDados.update("tb_enderecos_clientes", contentValuesCadastrarEndereco, "id = ?", arrayOf( endereco.id.toString() ))
            idEndereco = endereco.id
        } else {
            // cadastrar endereço
            idEndereco = super.bancoDados.insert("tb_enderecos_clientes", null, contentValuesCadastrarEndereco).toInt()
        }

        return idEndereco
    }

    fun setClienteEnviadoServidor(idCliente: Int) {

    }

    fun listarClientes(): ArrayList<Pessoa> {
        val clientes: ArrayList<Pessoa> = ArrayList()
        val query: String = "SELECT * FROM tb_clientes"
        val cursor: Cursor = super.bancoDados.rawQuery(query, null)

        if (cursor != null) {

            while (cursor.moveToNext()) {
                val cliente: Pessoa = Pessoa()
                cliente.tipoPessoa = cursor.getString(cursor.getColumnIndex("tipo_pessoa"))
                cliente.email = cursor.getString(cursor.getColumnIndex("email"))

                if (cliente.tipoPessoa == "pf") {
                    cliente.cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                    cliente.nome = cursor.getString(cursor.getColumnIndex("nome_completo"))
                } else {
                    cliente.cnpj = cursor.getString(cursor.getColumnIndex("cnpj"))
                    cliente.razaoSocial = cursor.getString(cursor.getColumnIndex("razao_social"))
                }

                clientes.add(cliente)
            }

            cursor.close()
        }

        return clientes
    }

    fun buscarEnderecoCliente(idCliente: Int): Endereco? {

        return null
    }

}