package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.UsuarioModelServico
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServico {

    // realizar login no servidor
    @POST("login.php")
    fun login(@Body usuarioModelServico: UsuarioModelServico): Call<RespostaBase<UsuarioModelServico>>

}