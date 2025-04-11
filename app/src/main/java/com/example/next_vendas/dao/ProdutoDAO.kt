package com.example.next_vendas.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.next_vendas.model.CategoriaProduto
import com.example.next_vendas.model.Produto
import java.util.ArrayList

class ProdutoDAO(val contexto: Context): BaseDAO(contexto) {

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
        val query = "SELECT * FROM tb_produtos WHERE id = ?"
        val cursor = super.bancoDados.rawQuery(query, arrayOf(idProduto.toString()))
        var produto: Produto? = null

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                produto = Produto()
                produto.id = cursor.getInt(cursor.getColumnIndex("id"))
                produto.nome = cursor.getString(cursor.getColumnIndex("nome_produto"))
                produto.precoVenda = cursor.getDouble(cursor.getColumnIndex("preco_venda"))
                produto.precoCompra = cursor.getDouble(cursor.getColumnIndex("preco_compra"))
                produto.status = if (cursor.getInt(cursor.getColumnIndex("status")) == 1) true else false
                produto.unidadesEstoque = cursor.getInt(cursor.getColumnIndex("unidades_estoque"))
                produto.fotoProduto = cursor.getString(cursor.getColumnIndex("foto"))
                produto.codigo = cursor.getString(cursor.getColumnIndex("codigo"))
                produto.codigoBarras = cursor.getString(cursor.getColumnIndex("codigo_barras"))
            }

            cursor.close()
        }

        return produto
    }

    fun filtrarProdutos(where: String, parametrosFiltro: List<String>): ArrayList<Produto> {

        Log.d("nome_produto_filtro", parametrosFiltro[ 0 ])
        Log.d("preco_de_filtro", parametrosFiltro[ 1 ])
        Log.d("preco_final_filtro", parametrosFiltro[ 2 ])

        var nomeProdFiltro = ""
        var precoDe = 0.0
        var precoAte = 9999999.0

        if (parametrosFiltro[ 0 ].isNotBlank()) {
            nomeProdFiltro = parametrosFiltro[ 0 ]
        }

        if (parametrosFiltro[ 1 ].isNotBlank()) {
            precoDe = parametrosFiltro[ 1 ].toDouble()
        }

        if (parametrosFiltro[ 2 ].isNotBlank()) {
            precoAte = parametrosFiltro[ 2 ].toDouble()
        }

        val whereNovo = """
            p.nome_produto LIKE "%$nomeProdFiltro%" AND p.preco_venda >= $precoDe AND p.preco_venda <= $precoAte
        """.trimIndent()

        val produtosFiltro: ArrayList<Produto> = arrayListOf()

        val query = "SELECT p.id, p.nome_produto, p.preco_venda, p.unidades_estoque, p.foto, p.codigo, p.codigo_barras, p.preco_compra, p.status," +
                " c.descricao AS categoria_produto, c.id AS id_categoria" +
                " FROM tb_produtos AS p, tb_categorias_produtos AS c" +
                " WHERE ${ whereNovo } AND p.categoria_id = c.id" +
                " ORDER BY p.nome_produto ASC"

        Log.d("query_filtro_produtos", query)

        val cursor = super.bancoDados.rawQuery(query, null)

        if (cursor != null) {

            while (cursor.moveToNext()) {
                val produto = Produto()
                
            }

            cursor.close()
        }

        return produtosFiltro
    }

    fun cadastrarProduto(produto: Produto): Int {
        var idProduto = this.getIdProdutoPeloCodigo(produto.codigo)
        val contentValuesProduto = ContentValues()

        if (idProduto != 0) {
            contentValuesProduto.put("id", idProduto)
        }

        contentValuesProduto.put("nome_produto", produto.nome)
        contentValuesProduto.put("preco_venda", produto.precoVenda)
        contentValuesProduto.put("preco_compra", produto.precoCompra)
        contentValuesProduto.put("status", produto.status)
        contentValuesProduto.put("unidades_estoque", produto.unidadesEstoque)
        contentValuesProduto.put("foto", produto.fotoProduto)
        contentValuesProduto.put("codigo", produto.codigo)
        contentValuesProduto.put("codigo_barras", produto.codigoBarras)

        val categoriaProdutoIdApi = produto.categoria!!.idCategoriaApi
        // buscar o id da categoria pelo id_categoria_api
        val categoriaProdutoDAO = CategoriaProdutoDAO(contexto = this.contexto)

        contentValuesProduto.put("categoria_id", categoriaProdutoDAO.getIdCategoriaPeloIdCategoriaApi(categoriaProdutoIdApi))

        if (idProduto != 0) {
            // atualizar o produto
            super.bancoDados.update("tb_produtos", contentValuesProduto, "id = ?", arrayOf(produto.id.toString()))
        } else {
            // cadastrar o produto na base de dados
            idProduto = super.bancoDados.insertOrThrow("tb_produtos", null, contentValuesProduto).toInt()
        }

        return idProduto
    }

    private fun getIdProdutoPeloCodigo(codigoProduto: String): Int {
        var idProduto = 0
        val cursor = super.bancoDados.rawQuery("SELECT id FROM tb_produtos WHERE codigo = ?", arrayOf(codigoProduto))

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                idProduto = cursor.getInt(cursor.getColumnIndex("id"))
            }

            cursor.close()
        }

        return idProduto
    }

}