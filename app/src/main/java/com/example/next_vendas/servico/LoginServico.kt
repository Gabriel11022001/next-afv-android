package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.AlteracaoSenhaModelServico
import com.example.next_vendas.model_servico.UsuarioModelServico
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface LoginServico {

    // realizar login no servidor
    @POST("login.php")
    fun login(@Body usuarioModelServico: UsuarioModelServico): Call<RespostaBase<UsuarioModelServico>>

    // alterar a senha do usuário logado no servidor
    @PUT("alterar_senha.php")
    fun alterarSenhaUsuarioLogado(@Body alteracaoSenhaModelServico: AlteracaoSenhaModelServico): Call<RespostaBase<String>>

}