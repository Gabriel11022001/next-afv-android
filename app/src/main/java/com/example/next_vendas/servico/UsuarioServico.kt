package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.ConsultaUsuarioLogadoModelServico
import com.example.next_vendas.model_servico.UsuarioModelServico
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioServico {

    @POST("buscar_dados_usuario_logado.php")
    fun buscarDadosUsuarioLogado(@Body consultaUsuarioLogadoModelServico: ConsultaUsuarioLogadoModelServico): Call<RespostaBase<UsuarioModelServico>>

}