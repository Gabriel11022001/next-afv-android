package com.example.next_vendas.servico

import com.example.next_vendas.model_servico.ClienteModelServico
import com.example.next_vendas.model_servico.TotalEntidadeModelServico
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteServico {

    // enviar cliente para o servidor para ser cadastrado na base final
    @POST("cadastrar_cliente.php")
    fun cadastrarCliente(@Body clienteModelServico: ClienteModelServico): Call<RespostaBase<ClienteModelServico>>

    // enviar cliente para o servidor para ser editado na base final
    @PUT("editar_cliente.php")
    fun editarCliente(@Body clienteModelServico: ClienteModelServico): Call<RespostaBase<ClienteModelServico>>

    // consultar clientes do servidor com paginação
    @GET("listar_clientes.php/{paginaAtual}")
    fun listarTodosCliente(@Path("paginaAtual") paginaAtual: Int): Call<ArrayList<ClienteModelServico>>

    // buscar do servidor total de clientes cadastrados na base final
    @GET("buscar_total_clientes.php")
    fun buscarTotalClientes(): Call<RespostaBase<TotalEntidadeModelServico>>

}