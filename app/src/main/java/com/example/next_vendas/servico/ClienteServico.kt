package com.example.next_vendas.servico

import com.example.next_vendas.model.Pessoa
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.ArrayList

interface ClienteServico {

    @POST("/cliente")
    fun cadastrarCliente(cliente: Pessoa): Call<RespostaBase>

    @PUT("/cliente")
    fun editarCliente(cliente: Pessoa): Call<RespostaBase>

    @GET("/cliente/{paginaAtual}")
    fun listarTodosCliente(paginaAtual: Int): Call<ArrayList<Pessoa>>

}