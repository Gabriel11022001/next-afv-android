package com.example.next_vendas.api

import android.util.Log
import com.example.next_vendas.dao.ProdutoDAO
import com.example.next_vendas.model.CategoriaProduto
import com.example.next_vendas.model.Produto
import com.example.next_vendas.model_servico.PaginaAtualModelServico
import com.example.next_vendas.model_servico.ProdutoModelServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class ProdutoApi {

    fun sincronizarProdutos(totalProdutos: Int, produtoDAO: ProdutoDAO, iOnSincronizar: IOnSincronizar) {
        val produtosPorPagina = 10.0
        var totalRequisoes: Double = 0.0

        if (totalProdutos <= produtosPorPagina) {
            totalRequisoes = 1.0
        } else {
            totalRequisoes = totalProdutos / produtosPorPagina
        }

        // sincronizar produtos página por página
        sincronizarPaginaProdutos(1, totalRequisoes.roundToInt(), produtoDAO, iOnSincronizar)
    }

    private fun sincronizarPaginaProdutos(paginaAtual: Int, totalRequisicoes: Int, produtoDAO: ProdutoDAO, iOnSincronizar: IOnSincronizar) {
        val produtoServico = Servico().getProdutoServico()

        var paginaAtualModelServico = PaginaAtualModelServico(paginaAtual)

        produtoServico.sincronizarProdutos(paginaAtualModelServico)
            .enqueue(object : Callback<RespostaBase<ArrayList<ProdutoModelServico>>> {

                override fun onResponse(call: Call<RespostaBase<ArrayList<ProdutoModelServico>>>, response: Response<RespostaBase<ArrayList<ProdutoModelServico>>>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.corpo.isEmpty() || paginaAtual == totalRequisicoes) {
                            // terminou de sincronizar
                            Log.d("sinc_produtos", "Terminou de sincronizar os produtos.")

                            iOnSincronizar.terminouSincronizar()
                        } else {
                            Log.d("sinc_produtos", "sincronizando os produtos, página atual: $paginaAtual")

                            // salvar os produtos na base local do app
                            val produtosSinc = response.body()!!.corpo

                            produtosSinc.forEach { produtoModelServico ->
                                val produtoSalvar = Produto()
                                produtoSalvar.nome = produtoModelServico.nomeProduto
                                produtoSalvar.fotoProduto = produtoModelServico.urlFoto
                                produtoSalvar.codigo = produtoModelServico.codigo
                                produtoSalvar.codigoBarras = produtoModelServico.codigoBarras
                                produtoSalvar.precoVenda = produtoModelServico.precoVenda
                                produtoSalvar.precoCompra = produtoModelServico.precoCompra
                                produtoSalvar.status = produtoModelServico.status
                                produtoSalvar.unidadesEstoque = produtoModelServico.unidadesEstoque.toInt()
                                produtoSalvar.categoria = CategoriaProduto(
                                    idCategoriaApi = produtoModelServico.categoriaId
                                )

                                val idProduto = produtoDAO.cadastrarProduto(produtoSalvar)
                                Log.d("produto_cadastrado", "produto $idProduto cadastrado com sucesso na base local do app.")
                            }

                            iOnSincronizar.sincronizando()

                            sincronizarPaginaProdutos(
                                paginaAtual + 1,
                                totalRequisicoes,
                                produtoDAO,
                                iOnSincronizar
                            )
                        }

                    } else {
                        Log.e("erro_sinc_produtos", "Ocorreu um erro ao tentar-se sincronizar os produtos.")

                        iOnSincronizar.erro("Ocorreu um erro ao tentar-se sincronizar os produtos.")
                    }

                }

                override fun onFailure(call: Call<RespostaBase<ArrayList<ProdutoModelServico>>>, t: Throwable) {
                    Log.e("erro_sinc_produtos", t.message.toString())

                    iOnSincronizar.erro("Ocorreu um erro ao tentar-se sincronizar os produtos.")
                }

            })

    }

}