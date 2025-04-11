package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.UnidadesProdutoCarrinhoModelServico
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface VendaServico {

    @PUT("atualizar_unidades_produto_carrinho.php")
    fun atualizarUnidadesProdutoCarrinhoVenda(@Body unidadesProdutoCarrinhoModelServico: UnidadesProdutoCarrinhoModelServico): Call<RespostaBase<UnidadesProdutoCarrinhoModelServico>>

}