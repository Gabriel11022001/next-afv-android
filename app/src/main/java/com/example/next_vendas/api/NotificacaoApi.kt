package com.example.next_vendas.api

import android.util.Log
import com.example.next_vendas.model_servico.NotificacaoModelServico
import com.example.next_vendas.servico.NotificacaoServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificacaoApi {

    fun buscarNotificacoes(
        onRealizandoRequisicao: () -> Unit,
        onErroRequisicao: (String) -> Unit,
        onSucessoRequisicao: (ArrayList<NotificacaoModelServico>) -> Unit
    ) {
        val servico: Servico = Servico()
        val notificacaoServico: NotificacaoServico = servico.getNotificacaoServico()

        onRealizandoRequisicao()

        notificacaoServico.buscarNotificacoes()
            .enqueue(object : Callback<RespostaBase<ArrayList<NotificacaoModelServico>>> {

                override fun onResponse(call: Call<RespostaBase<ArrayList<NotificacaoModelServico>>>, response: Response<RespostaBase<ArrayList<NotificacaoModelServico>>>) {

                    if (response.isSuccessful) {
                        val notificacoes = response.body()!!.corpo

                        notificacoes.forEach { notificacao ->
                            Log.d("msg_notificacao", notificacao.mensagem)
                        }

                        onSucessoRequisicao(notificacoes)
                    } else {
                        // erro ao tentar-se consultar as notificações
                        onErroRequisicao("Erro ao tentar-se consultar as notificações.")
                    }

                }

                override fun onFailure(call: Call<RespostaBase<ArrayList<NotificacaoModelServico>>>, t: Throwable) {
                    Log.e("erro_consultar_notificacoes", t.message.toString())

                    onErroRequisicao("Erro ao tentar-se consultar as notificações: ${ t.message.toString() }")
                }

            })

    }

}