package com.example.next_vendas.dao

import android.content.Context
import android.database.Cursor
import com.example.next_vendas.model.CategoriaProduto
import com.example.next_vendas.model.Produto
import java.util.ArrayList

class ProdutoDAO(contexto: Context): BaseDAO(contexto) {

    fun listarProdutos(): ArrayList<Produto> {
        val produtos: ArrayList<Produto> = arrayListOf()
        val query: String = "SELECT p.id, p.nome_produto, p.preco_venda, p.unidades_estoque, p.foto, p.codigo, p.codigo_barras, p.preco_compra, p.status," +
                " c.descricao AS categoria_produto, c.id AS id_categoria" +
                " FROM tb_produtos AS p, tb_categorias_produtos AS c" +
                " WHERE p.categoria_id = c.id" +
                " ORDER BY p.nome_produto ASC"
        val cursor: Cursor = super.bancoDados.rawQuery(query, null)

        if (cursor != null) {

            while (cursor.moveToNext()) {
                val produto: Produto = Produto()
                produto.id = cursor.getInt(cursor.getColumnIndex("id"))
                produto.nome = cursor.getString(cursor.getColumnIndex("nome_produto"))
                produto.precoVenda = cursor.getDouble(cursor.getColumnIndex("preco_venda"))
                produto.precoCompra = cursor.getDouble(cursor.getColumnIndex("preco_compra"))
                produto.status = if (cursor.getInt(cursor.getColumnIndex("status")) == 1) true else false
                produto.unidadesEstoque = cursor.getInt(cursor.getColumnIndex("unidades_estoque"))
                produto.fotoProduto = cursor.getString(cursor.getColumnIndex("foto"))
                produto.codigo = cursor.getString(cursor.getColumnIndex("codigo"))
                produto.codigoBarras = cursor.getString(cursor.getColumnIndex("codigo_barras"))

                val categoria = CategoriaProduto(
                    id = cursor.getInt(cursor.getColumnIndex("id_categoria")),
                    descricao = cursor.getString(cursor.getColumnIndex("categoria_produto"))
                )

                produto.categoria = categoria

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