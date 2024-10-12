package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.TotalModelServico
import retrofit2.Call
import retrofit2.http.GET

interface TotalEntidadeServico {

    @GET("get_total.php")
    fun obterTotais(): Call<RespostaBase<TotalModelServico>>

}