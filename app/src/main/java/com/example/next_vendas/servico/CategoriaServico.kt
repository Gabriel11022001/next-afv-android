package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.CategoriaProdutoModelServico
import com.example.next_vendas.model_servico.PaginaAtualModelServico
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CategoriaServico {

    // método para sincronizar as categorias de produtos do servidor
    @POST("sinc_categorias_produtos.php")
    fun sincronizarCategoriasProdutos(@Body paginaAtualModelServico: PaginaAtualModelServico): Call<RespostaBase<ArrayList<CategoriaProdutoModelServico>>>

}