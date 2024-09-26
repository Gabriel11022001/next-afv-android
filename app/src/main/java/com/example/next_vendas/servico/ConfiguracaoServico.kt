package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.ConfiguracaoModelServico
import retrofit2.Call
import retrofit2.http.GET

interface ConfiguracaoServico {

    // método para sincronizar as configurações do servidor
    @GET("sinc_configuracoes.php")
    fun sincronizarConfiguracoes(): Call<RespostaBase<ConfiguracaoModelServico>>

}