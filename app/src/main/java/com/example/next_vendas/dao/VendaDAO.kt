package com.example.next_vendas.dao

import android.content.ContentValues
import android.content.Context
import com.example.next_vendas.model.ProdutoCarrinho
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.Date

class VendaDAO(contexto: Context): BaseDAO(contexto) {

    fun registrarVendaInicio(): Int {
        var idVendaInicio = 0
        val contentValues = ContentValues()
        contentValues.put("status", "andamento")
        contentValues.put("valor_total", 0.0)

        val dataVenda = Date()
        val dateTimeFormater = SimpleDateFormat("yyyy-MM-dd H:mm:ss")
        contentValues.put("data_venda", dateTimeFormater.format(dataVenda))

        idVendaInicio = super.bancoDados.insertOrThrow("tb_vendas", null, contentValues).toInt()

        return idVendaInicio
    }

    fun buscarProdutosAdicionarCarrinho(idVenda: Int): ArrayList<ProdutoCarrinho> {
        val produtosAdicionarCarrinho = ArrayList<ProdutoCarrinho>()

        // buscar todos os produtos
        var cursor = super.bancoDados.rawQuery("SELECT id, nome_produto, preco_venda FROM tb_produtos WHERE status = true", null)

        if (cursor != null) {

            while (cursor.moveToNext()) {
                val produtoAdicionarCarrinho: ProdutoCarrinho = ProdutoCarrinho()
                produtoAdicionarCarrinho.idProdutoCarrinho = cursor.getInt(cursor.getColumnIndex("id"))
                produtoAdicionarCarrinho.nomeProdutoCarrinho = cursor.getString(cursor.getColumnIndex("nome_produto"))

                // validar se o produto está relacionado a venda em questão, nesse caso, o produto estaria no carrinho de compras do usuário
                val cursorBuscarProdutoVenda = super.bancoDados.rawQuery("SELECT valor_produto_carrinho, quantidade_unidades_produto_carrinho FROM tb_produtos_vendas WHERE venda_id = ? AND produto_id = ?", arrayOf( idVenda.toString(), produtoAdicionarCarrinho.idProdutoCarrinho.toString() ))

                if (cursorBuscarProdutoVenda != null) {

                    if (cursorBuscarProdutoVenda.moveToFirst()) {
                        val quantidadeUnidadesProdutoCarrinho: Int = cursorBuscarProdutoVenda.getInt(cursorBuscarProdutoVenda.getColumnIndex("quantidade_unidades_produto_carrinho"))
                        val valorUnitarioProdutoCarrinho: Double = cursorBuscarProdutoVenda.getDouble(cursorBuscarProdutoVenda.getColumnIndex("valor_produto_carrinho"))

                        produtoAdicionarCarrinho.quantidadeUnidadesProdutoCarrinho = quantidadeUnidadesProdutoCarrinho
                        produtoAdicionarCarrinho.precoUnitarioProdutoCarrinho = valorUnitarioProdutoCarrinho
                        produtoAdicionarCarrinho.idVenda = idVenda
                    } else {
                        produtoAdicionarCarrinho.precoUnitarioProdutoCarrinho = cursor.getDouble(cursor.getColumnIndex("preco_venda"))
                        produtoAdicionarCarrinho.quantidadeUnidadesProdutoCarrinho = 0
                        produtoAdicionarCarrinho.idVenda = 0
                    }

                    cursorBuscarProdutoVenda.close()
                } else {
                    produtoAdicionarCarrinho.precoUnitarioProdutoCarrinho = cursor.getDouble(cursor.getColumnIndex("preco_venda"))
                    produtoAdicionarCarrinho.quantidadeUnidadesProdutoCarrinho = 0
                    produtoAdicionarCarrinho.idVenda = 0
                }

                produtosAdicionarCarrinho.add(produtoAdicionarCarrinho)
            }

            cursor.close()
        }

        return produtosAdicionarCarrinho
    }

}