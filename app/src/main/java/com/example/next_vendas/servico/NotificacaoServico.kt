package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.NotificacaoModelServico
import retrofit2.Call
import retrofit2.http.GET

interface NotificacaoServico {

    @GET("get_notificacoes.php")
    fun buscarNotificacoes(): Call<RespostaBase<ArrayList<NotificacaoModelServico>>>

}