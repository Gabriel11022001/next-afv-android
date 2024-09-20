package com.example.next_vendas.dao

import android.content.Context
import android.database.Cursor
import com.example.next_vendas.model.Produto
import java.util.ArrayList

class ProdutoDAO(contexto: Context): BaseDAO(contexto) {

    fun listarProdutos(): ArrayList<Produto> {
        val produtos: ArrayList<Produto> = arrayListOf()
        val query: String = "SELECT p.id, p.nome_produto, p.preco, p.unidades_estoque, p.foto, p.codigo," +
                " c.descricao" +
                " FROM tb_produtos AS p, tb_categorias_produtos AS c" +
                " WHERE p.categoria_id = c.id" +
                " ORDER BY p.nome_produto ASC"
        val cursor: Cursor = super.bancoDados.rawQuery(query, null)

        if (cursor != null) {

            while (cursor.moveToNext()) {
                val produto: Produto = Produto()

                produtos.add(produto)
            }

            cursor.close()
        }

        return produtos
    }

    fun buscarProdutoPeloId(idProduto: Int): Produto? {

        return null
    }

    fun filtrarProdutosPeloNome(nome: String): ArrayList<Produto> {

        return arrayListOf()
    }

}