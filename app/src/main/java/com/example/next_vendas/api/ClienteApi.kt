package com.example.next_vendas.api

import android.hardware.lights.Light
import android.util.Log
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.servico.ClienteServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ClienteApi {

    companion object {

        private val TOTAL_CLIENTES_POR_PAGINA: Int = 5

        fun cadastrarCliente(cliente: Pessoa, iOnEnviarServidor: IOnEnviarServidor) {
            Log.d("envia_cliente_servidor", "Enviando o cliente para o servidor...")

            val clienteServico: ClienteServico = Servico().getClienteService()
            clienteServico.cadastrarCliente(cliente)
                .enqueue(object : Callback<RespostaBase> {

                    override fun onResponse(
                        call: Call<RespostaBase>,
                        response: Response<RespostaBase>
                    ) {

                        if (response.isSuccessful) {
                            // a requisição foi processada com sucesso
                            val msg: String = response.body()!!.mensagem

                            if (msg == "Cliente cadastrado com sucesso!") {
                                iOnEnviarServidor.sucesso("Cliente cadastrado com sucesso!")
                            } else {
                                iOnEnviarServidor.erro(msg)
                            }

                        } else {
                            // erro ao tentar-se cadastrar o cliente
                            iOnEnviarServidor.erro("Ocorreu um erro ao tentar-se cadastrar o cliente!")
                        }

                    }

                    override fun onFailure(call: Call<RespostaBase>, t: Throwable) {
                        // erro ao tentar-se cadastrar o cliente
                        iOnEnviarServidor.erro("Ocorreu um erro ao tentar-se cadastrar o cliente!")
                    }

                })

        }

        fun sincronizarClientes(totalClientes: Int, iOnSincronizar: IOnSincronizar) {
            Log.d("sincronizacao_debug", "Sincronizando clientes...")

            var totalPaginas: Int = 0
            var paginaAtual: Int = 1

            if (totalClientes <= this.TOTAL_CLIENTES_POR_PAGINA) {
                totalPaginas = 1
            } else {
                totalPaginas = totalClientes / this.TOTAL_CLIENTES_POR_PAGINA
            }

            while (paginaAtual <= totalPaginas) {
                Servico().getClienteService()
                    .listarTodosCliente(paginaAtual)
                    .enqueue(object : Callback<ArrayList<Pessoa>> {

                        override fun onResponse(
                            call: Call<ArrayList<Pessoa>>,
                            response: Response<ArrayList<Pessoa>>
                        ) {

                            if (response.isSuccessful) {
                                val clientes: ArrayList<Pessoa> = response.body()!!

                                if (clientes.size > 0) {
                                    // persistir os clientes na base local do app

                                    iOnSincronizar.sincronizando()
                                } else {
                                    // terminou de sincronizar os clientes
                                    iOnSincronizar.terminouSincronizar()
                                }

                            } else {
                                // erro ao tentar-se sincronizar os clientes
                                iOnSincronizar.erro("Ocorreu um erro ao tentar-se sincronizar os clientes!")
                            }

                            paginaAtual++
                        }

                        override fun onFailure(call: Call<ArrayList<Pessoa>>, t: Throwable) {
                            paginaAtual++
                            iOnSincronizar.erro("Ocorreu um erro ao tentar-se sincronizar os clientes!")
                        }

                    })
            }

        }

    }

}