package com.example.next_vendas.api

import android.util.Log
import com.example.next_vendas.model_servico.ConsultaUsuarioLogadoModelServico
import com.example.next_vendas.model_servico.UsuarioModelServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import com.example.next_vendas.servico.UsuarioServico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioApi {

    fun buscarDadosUsuarioLogado(
        idUsuarioLogado: Int,
        onProcessandoRequisicao: () -> Unit,
        onErroRequisicao: (String) -> Unit,
        onSucessoRequisicao: (UsuarioModelServico) -> Unit
    ) {
        val servico: Servico = Servico()
        val usuarioServico: UsuarioServico = servico.getUsuarioServico()

        val consultaUsuarioLogadoModelServico: ConsultaUsuarioLogadoModelServico = ConsultaUsuarioLogadoModelServico(idUsuarioLogado)

        onProcessandoRequisicao()

        usuarioServico.buscarDadosUsuarioLogado(consultaUsuarioLogadoModelServico)
            .enqueue(object : Callback<RespostaBase<UsuarioModelServico>> {

                override fun onResponse(call: Call<RespostaBase<UsuarioModelServico>>, response: Response<RespostaBase<UsuarioModelServico>>) {

                    if (response.isSuccessful) {
                        val msg: String = response.body()!!.mensagem

                        if (msg == "Usuário encontrado com sucesso.") {
                            val usuario: UsuarioModelServico = response.body()!!.corpo

                            onSucessoRequisicao(usuario)
                        } else {
                            Log.e("erro_consultar_dados_perfil", msg)

                            onErroRequisicao(msg)
                        }

                    } else {
                        onErroRequisicao("Ocorreu um erro ao tentar-se consultar os dados do seu perfil.")
                    }

                }

                override fun onFailure(call: Call<RespostaBase<UsuarioModelServico>>, t: Throwable) {
                    Log.e("erro_consultar_dados_perfil", t.message.toString())
                    onErroRequisicao("Ocorreu um erro ao tentar-se consultar os dados do seu perfil.")
                }

            })

    }

}