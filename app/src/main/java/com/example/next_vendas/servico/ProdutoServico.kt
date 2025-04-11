package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.PaginaAtualModelServico
import com.example.next_vendas.model_servico.ProdutoModelServico
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ProdutoServico {

    // método para sincronizar os produtos do servidor
    @POST("sinc_produtos.php")
    fun sincronizarProdutos(@Body paginaAtualModelServico: PaginaAtualModelServico): Call<RespostaBase<ArrayList<ProdutoModelServico>>>

}