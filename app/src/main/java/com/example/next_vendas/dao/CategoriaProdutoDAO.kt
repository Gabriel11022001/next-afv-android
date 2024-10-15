package com.example.next_vendas.dao

import android.content.ContentValues
import android.content.Context
import com.example.next_vendas.model.CategoriaProduto

class CategoriaProdutoDAO(
    contexto: Context
): BaseDAO(contexto) {

    fun getIdCategoriaPeloIdCategoriaApi(idCategoriaApi: Int): Int {
        var idCategoria = 0
        val cursor = super.bancoDados.rawQuery("SELECT id FROM tb_categorias_produtos WHERE id_categoria_api = ?", arrayOf(idCategoriaApi.toString()))

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                idCategoria = cursor.getInt(cursor.getColumnIndex("id"))
            }

            cursor.close()
        }

        return idCategoria
    }

    fun cadastrarCategoriaProduto(categoriaProduto: CategoriaProduto): Int {
        var idCategoria = this.getIdCategoriaPelaDescricao(categoriaProduto.descricao)
        val contentValues = ContentValues()

        if (idCategoria != 0) {
            contentValues.put("id", idCategoria)
        }

        contentValues.put("descricao", categoriaProduto.descricao)
        contentValues.put("id_categoria_api", categoriaProduto.idCategoriaApi)
        contentValues.put("status", categoriaProduto.status)

        if (idCategoria != 0) {
            // atualizar a categoria
            super.bancoDados.update("tb_categorias_produtos", contentValues, "id = ?", arrayOf(idCategoria.toString()))
        } else {
            // cadastrar a categoria na base de dados
            idCategoria = super.bancoDados.insertOrThrow("tb_categorias_produtos", null, contentValues).toInt()
        }

        return idCategoria
    }

    private fun getIdCategoriaPelaDescricao(descricao: String): Int {
        var idCategoria = 0

        val cursor = super.bancoDados.rawQuery("SELECT id FROM tb_categorias_produtos WHERE descricao = ?", arrayOf(descricao))

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                idCategoria = cursor.getInt(cursor.getColumnIndex("id"))
            }

            cursor.close()
        }

        return idCategoria
    }

}