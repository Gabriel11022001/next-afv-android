package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.EnderecoViaCepModelServico
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCEPServico {

    @GET("{cep}/json")
    fun buscarEnderecoPeloCep(@Path("cep") cep: String): Call<EnderecoViaCepModelServico>

}