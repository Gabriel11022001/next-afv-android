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
            valuesCliente.put("tipo_pessoa", Constantes.FISICA)
        } else {
            valuesCliente.put("cnpj", cliente.cnpj)
            valuesCliente.put("razao_social", cliente.razaoSocial)
            valuesCliente.put("site", cliente.site)
            valuesCliente.put("inscricao_estadual", cliente.inscricaoEstadual)
            valuesCliente.put("tipo_pessoa", Constantes.JURIDICA)
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
        val contentValuesSetarClienteEnviadoServidor = ContentValues()
        contentValuesSetarClienteEnviadoServidor.put("enviado", true)

        super.bancoDados.update("tb_clientes", contentValuesSetarClienteEnviadoServidor, "id = ?", arrayOf(
            idCliente.toString()
        ))
    }

    fun listarClientes(): ArrayList<Pessoa> {
        val clientes: ArrayList<Pessoa> = ArrayList()
        val query: String = "SELECT * FROM tb_clientes"
        val cursor: Cursor = super.bancoDados.rawQuery(query, null)

        if (cursor != null) {

            while (cursor.moveToNext()) {
                val cliente: Pessoa = Pessoa()
                cliente.id = cursor.getInt(cursor.getColumnIndex("id"))
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

    fun buscarClientePeloCpf(cpf: String): Pessoa? {
        var clientePessoaFisica: Pessoa? = null
        val query: String = "SELECT cli.id, cli.nome_completo AS nome, cli.email, cli.cpf, cli.data_nascimento, cli.sexo," +
                " cli.tipo_pessoa, cli.telefone_celular, cli.telefone_complementar, cli.telefone_fixo, cli.data_cadastro," +
                " en.cep, en.complemento, en.endereco, en.bairro, en.cidade, en.estado, en.numero, en.id AS endereco_id FROM tb_clientes AS cli, tb_enderecos_clientes AS en WHERE cli.cpf = ?" +
                " AND cli.tipo_pessoa = 'pf' AND cli.id = en.cliente_id"
        val cursor: Cursor = super.bancoDados.rawQuery(query, arrayOf(
            cpf
        ))

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                clientePessoaFisica = Pessoa()
                clientePessoaFisica.id = cursor.getInt(cursor.getColumnIndex("id"))
                clientePessoaFisica.nome = cursor.getString(cursor.getColumnIndex("nome"))
                clientePessoaFisica.tipoPessoa = Constantes.FISICA
                clientePessoaFisica.telefoneCelular = cursor.getString(cursor.getColumnIndex("telefone_celular"))
                clientePessoaFisica.telefoneFixo = cursor.getString(cursor.getColumnIndex("telefone_fixo"))
                clientePessoaFisica.telefoneComplementar = cursor.getString(cursor.getColumnIndex("telefone_complementar"))
                clientePessoaFisica.cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                clientePessoaFisica.email = cursor.getString(cursor.getColumnIndex("email"))
                clientePessoaFisica.dataNascimento = cursor.getString(cursor.getColumnIndex("data_nascimento"))
                clientePessoaFisica.sexo = cursor.getString(cursor.getColumnIndex("sexo"))
                clientePessoaFisica.dataCadastro = cursor.getString(cursor.getColumnIndex("data_cadastro"))
                // buscar endereço do cliente
                clientePessoaFisica.endereco = Endereco()
                clientePessoaFisica.endereco.id = cursor.getInt(cursor.getColumnIndex("endereco_id"))
                clientePessoaFisica.endereco.cep = cursor.getString(cursor.getColumnIndex("cep"))
                clientePessoaFisica.endereco.complemento = cursor.getString(cursor.getColumnIndex("complemento"))
                clientePessoaFisica.endereco.endereco = cursor.getString(cursor.getColumnIndex("endereco"))
                clientePessoaFisica.endereco.bairro = cursor.getString(cursor.getColumnIndex("bairro"))
                clientePessoaFisica.endereco.cidade = cursor.getString(cursor.getColumnIndex("cidade"))
                clientePessoaFisica.endereco.numero = cursor.getString(cursor.getColumnIndex("numero"))
                clientePessoaFisica.endereco.uf = cursor.getString(cursor.getColumnIndex("estado"))
            }

            cursor.close()
        }

        return clientePessoaFisica
    }

    fun buscarClientePeloId(idCliente: Int): Pessoa? {
        var cliente: Pessoa? = null
        val query: String = "SELECT cliente.*, endereco.cep, endereco.complemento, endereco.endereco, endereco.bairro, endereco.cidade, endereco.numero, endereco.estado" +
                " FROM tb_clientes AS cliente, tb_enderecos_clientes AS endereco" +
                " WHERE cliente.id = endereco.cliente_id AND cliente.id = ?"
        val cursor: Cursor = super.bancoDados.rawQuery(query, arrayOf(
            idCliente.toString()
        ))

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                // encontrou o cliente cadastrado na base local do app com o id informado
                cliente = Pessoa()
                cliente.id = cursor.getInt(cursor.getColumnIndex("id"))
                cliente.email = cursor.getString(cursor.getColumnIndex("email"))
                cliente.telefoneCelular = cursor.getString(cursor.getColumnIndex("telefone_celular"))
                cliente.telefoneComplementar = cursor.getString(cursor.getColumnIndex("telefone_complementar"))
                cliente.telefoneFixo = cursor.getString(cursor.getColumnIndex("telefone_fixo"))
                cliente.endereco = Endereco()
                cliente.endereco.cep = cursor.getString(cursor.getColumnIndex("cep"))
                cliente.endereco.endereco = cursor.getString(cursor.getColumnIndex("endereco"))
                cliente.endereco.bairro = cursor.getString(cursor.getColumnIndex("bairro"))
                cliente.endereco.cidade = cursor.getString(cursor.getColumnIndex("cidade"))
                cliente.endereco.numero = cursor.getString(cursor.getColumnIndex("numero"))
                cliente.endereco.uf = cursor.getString(cursor.getColumnIndex("estado"))
                cliente.endereco.complemento = cursor.getString(cursor.getColumnIndex("complemento"))

                if (cursor.getString(cursor.getColumnIndex("tipo_pessoa")) == Constantes.FISICA) {
                    cliente.tipoPessoa = Constantes.FISICA
                    cliente.nome = cursor.getString(cursor.getColumnIndex("nome_completo"))
                    cliente.dataNascimento = cursor.getString(cursor.getColumnIndex("data_nascimento"))
                    cliente.cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                    cliente.sexo = cursor.getString(cursor.getColumnIndex("sexo"))
                } else {
                    cliente.tipoPessoa = Constantes.JURIDICA
                    cliente.razaoSocial = cursor.getString(cursor.getColumnIndex("razao_social"))
                    cliente.inscricaoEstadual = cursor.getString(cursor.getColumnIndex("inscricao_estadual"))
                    cliente.cnpj = cursor.getString(cursor.getColumnIndex("cnpj"))
                    cliente.site = cursor.getString(cursor.getColumnIndex("site"))
                }

            }

            cursor.close()
        }

        return cliente
    }

    fun deletarCliente(idCliente: Int) {
        super.bancoDados.delete("tb_clientes", "id = ?", arrayOf(idCliente.toString()))
        // deletar o endereço do cliente da base local do app
        this.deletarEnderecoCliente(idCliente)
    }

    private fun deletarEnderecoCliente(idCliente: Int) {
        super.bancoDados.delete("tb_enderecos_clientes", "cliente_id = ?", arrayOf(idCliente.toString()))
    }

}