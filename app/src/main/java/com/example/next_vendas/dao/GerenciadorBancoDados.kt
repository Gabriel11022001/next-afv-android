package com.example.next_vendas.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.next_vendas.utils.Constantes

open class GerenciadorBancoDados(contexto: Context): SQLiteOpenHelper(
    contexto,
    Constantes.NM_BANCO,
    null,
    Constantes.VERSAO_BANCO
) {

    protected val bancoDados: SQLiteDatabase = writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        this.gerarTabelas(p0!!)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    private fun gerarTabelas(bancoDados: SQLiteDatabase) {
        // criar tabela de clientes
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tb_clientes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo_pessoa TEXT NOT NULL," +
                "nome_completo TEXT," +
                "cpf TEXT," +
                "data_nascimento TEXT," +
                "telefone_celular TEXT," +
                "telefone_fixo TEXT," +
                "telefone_complementar TEXT," +
                "email TEXT," +
                "data_cadastro TEXT," +
                "sexo TEXT," +
                "cnpj TEXT," +
                "inscricao_estadual TEXT," +
                "site TEXT," +
                "enviado BOOLEAN DEFAULT(false))")
        // criar tabela de endereços dos clientes
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tb_enderecos_clientes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cep TEXT," +
                "endereco TEXT," +
                "complemento TEXT," +
                "cidade TEXT," +
                "bairro TEXT," +
                "estado TEXT," +
                "numero TEXT," +
                "cliente_id INT," +
                "FOREIGN KEY(cliente_id) REFERENCES tb_clientes(id))")
        // criar tabela de categoria de produtos
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tb_categorias_produtos(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT NOT NULL," +
                "status TEXT NOT NULL," +
                "id_categoria_api INTEGER NOT NULL)")
        // criar tabela de produtos
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tb_produtos(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome_produto TEXT NOT NULL," +
                "preco_venda DECIMAL NOT NULL," +
                "preco_compra DECIMAL NOT NULL," +
                "unidades_estoque INTEGER NOT NULL," +
                "foto TEXT," +
                "codigo TEXT NOT NULL," +
                "codigo_barras TEXT NOT NULL," +
                "categoria_id INTEGER NOT NULL," +
                "status BOOLEAN," +
                "FOREIGN KEY(categoria_id) REFERENCES tb_categorias_produtos(id))")
    }

}