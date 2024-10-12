package com.example.next_vendas.api

import android.util.Log
import com.example.next_vendas.model_servico.CategoriaProdutoModelServico
import com.example.next_vendas.model_servico.PaginaAtualModelServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class CategoriaProdutoApi {

    fun sincronizarCategoriasProdutos(totalCategoriasProdutos: Int, iOnSincronizar: IOnSincronizar) {
        var totalRequisicoes: Double = 0.0
        var totalCategoriasPorPagina: Double = 10.0

        if (totalCategoriasProdutos <= totalCategoriasPorPagina.toInt()) {
            totalRequisicoes = 1.0
        } else {
            totalRequisicoes = totalCategoriasProdutos / totalCategoriasPorPagina
        }

        var paginaAtual = 1

        // sincronizar página por página as categorias de produto
        sincronizarCategoriaProdutoPagina(paginaAtual, totalRequisicoes.roundToInt(), iOnSincronizar)
    }

    private fun sincronizarCategoriaProdutoPagina(paginaAtual: Int, totalRequisicoes: Int, iOnSincronizar: IOnSincronizar) {
        val categoriaProdutoServico = Servico().getCategoriaProdutoServico()

        val paginaAtualModelServico = PaginaAtualModelServico(
            paginaAtual
        )

        categoriaProdutoServico.sincronizarCategoriasProdutos(paginaAtualModelServico)
            .enqueue(object : Callback<RespostaBase<ArrayList<CategoriaProdutoModelServico>>> {

                override fun onResponse(call: Call<RespostaBase<ArrayList<CategoriaProdutoModelServico>>>, response: Response<RespostaBase<ArrayList<CategoriaProdutoModelServico>>>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.corpo.isEmpty() || paginaAtual == totalRequisicoes) {
                            // terminou de sincronizar
                            Log.d("sinc_categorias", "Terminou de sincronizar as categorias de produtos.")

                            iOnSincronizar.terminouSincronizar()
                        } else {
                            // salvar as categorias de produtos na base local do app

                            Log.d("sinc_categorias", "Sincronizando as categorias de produtos, pagina atual: $paginaAtual")

                            val novaPagina: Int = paginaAtual + 1

                            iOnSincronizar.sincronizando()

                            sincronizarCategoriaProdutoPagina(
                                novaPagina,
                                totalRequisicoes,
                                iOnSincronizar
                            )
                        }

                    } else {
                        Log.e("erro_sinc_categoria_produtos", "Ocorreu um erro ao tentar-se sincronizar as categorias.")

                        iOnSincronizar.erro("Ocorreu um erro ao tentar-se sincronizar as categorias.")
                    }

                }

                override fun onFailure(call: Call<RespostaBase<ArrayList<CategoriaProdutoModelServico>>>, t: Throwable) {
                    Log.e("erro_sinc_categoria_produtos", t.message.toString())

                    iOnSincronizar.erro("Ocorreu um erro ao tentar-se sincronizar as categorias.")
                }

            })

    }

}