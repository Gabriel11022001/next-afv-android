package com.example.next_vendas.api

import android.content.Context
import android.util.Log
import com.example.next_vendas.dao.ClienteDAO
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.model_servico.ClienteModelServico
import com.example.next_vendas.model_servico.PaginaAtualModelServico
import com.example.next_vendas.servico.ClienteServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import com.example.next_vendas.utils.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class ClienteApi(

) {

    companion object {

        private val TOTAL_CLIENTES_POR_PAGINA: Int = 10

        fun cadastrarCliente(cliente: Pessoa, iOnEnviarServidor: IOnEnviarServidor) {
            val clienteModelServicoCadastrar: ClienteModelServico = ClienteModelServico()

            if (cliente.tipoPessoa == Constantes.FISICA) {
                clienteModelServicoCadastrar.nomeCompleto = cliente.nome
                clienteModelServicoCadastrar.cpf = cliente.cpf
                clienteModelServicoCadastrar.dataNascimento = cliente.dataNascimento
                clienteModelServicoCadastrar.sexo = cliente.sexo
                clienteModelServicoCadastrar.tipoPessoa = Constantes.FISICA
            } else {
                clienteModelServicoCadastrar.razaoSocial = cliente.razaoSocial
                clienteModelServicoCadastrar.cnpj = cliente.cnpj
                clienteModelServicoCadastrar.inscricaoEstadual = cliente.inscricaoEstadual
                clienteModelServicoCadastrar.linkSiteEmpresa = cliente.site
                clienteModelServicoCadastrar.tipoPessoa = Constantes.JURIDICA
            }

            clienteModelServicoCadastrar.email = cliente.email
            clienteModelServicoCadastrar.telefoneCelular = cliente.telefoneCelular
            clienteModelServicoCadastrar.telefoneFixo = cliente.telefoneFixo
            clienteModelServicoCadastrar.telefoneComplementar = cliente.telefoneComplementar
            clienteModelServicoCadastrar.cep = cliente.endereco.cep
            clienteModelServicoCadastrar.complemento = cliente.endereco.complemento
            clienteModelServicoCadastrar.endereco = cliente.endereco.endereco
            clienteModelServicoCadastrar.bairro = cliente.endereco.bairro
            clienteModelServicoCadastrar.cidade = cliente.endereco.cidade
            clienteModelServicoCadastrar.estado = cliente.endereco.uf
            clienteModelServicoCadastrar.numero = cliente.endereco.numero

            val clienteServico: ClienteServico = Servico().getClienteService()

            clienteServico.cadastrarCliente(clienteModelServicoCadastrar)
                .enqueue(object : Callback<RespostaBase<ClienteModelServico>> {

                    override fun onResponse(
                        call: Call<RespostaBase<ClienteModelServico>>,
                        response: Response<RespostaBase<ClienteModelServico>>
                    ) {

                        if (response.isSuccessful) {
                            val corpoResposta = response.body()
                            val msg: String? = corpoResposta?.mensagem

                            if (msg != null) {

                                if (msg == "Cliente cadastrado com sucesso.") {
                                    Log.d("resp_enviar_cli_servidor", "Cliente enviado com sucesso para o servidor".uppercase())
                                    iOnEnviarServidor.sucesso("Cliente enviado com sucesso para o servidor.")
                                } else {
                                    Log.e("erro_enviar_cliente_servidor", msg)
                                    iOnEnviarServidor.erro(mensagemErro = msg)
                                }

                            }

                        } else {
                            // erro ao tentar-se enviar o cliente para o servidor
                            Log.e("erro_enviar_cliente_servidor", "Erro ao tentar-se enviar o cliente para o servidor.")
                            iOnEnviarServidor.erro("Ocorreu um erro ao tentar-se enviar os dados do cliente para o servidor, tente novamente.")
                        }

                    }

                    override fun onFailure(
                        call: Call<RespostaBase<ClienteModelServico>>,
                        t: Throwable
                    ) {
                        Log.e("erro_enviar_cliente_servidor", "Erro: " + t.message.toString())
                        iOnEnviarServidor.erro("Ocorreu um erro ao tentar-se enviar os dados do cliente para o servidor, tente novamente.")
                    }

                })
        }

        fun sincronizarClientes(totalClientes: Int, clienteDAO: ClienteDAO, iOnSincronizar: IOnSincronizar) {
            var totalRequisicoes: Double = 0.0

            if (totalClientes == this.TOTAL_CLIENTES_POR_PAGINA || totalClientes < this.TOTAL_CLIENTES_POR_PAGINA) {
                totalRequisicoes = 1.0
            } else {
                totalRequisicoes = totalClientes.toDouble() / this.TOTAL_CLIENTES_POR_PAGINA.toDouble()
            }

            var paginaAtual = 1

            sincronizarPaginaClientes(paginaAtual, totalRequisicoes.roundToInt(), iOnSincronizar)
        }

        private fun sincronizarPaginaClientes(paginaAtual: Int, totalRequisicoes: Int, iOnSincronizar: IOnSincronizar) {
            val clienteServico = Servico().getClienteService()

            var paginaAtualModelServico = PaginaAtualModelServico(
                paginaAtual = paginaAtual
            )

            clienteServico.listarClientes(paginaAtualModelServico).enqueue(object : Callback<RespostaBase<ArrayList<ClienteModelServico>>> {

                override fun onResponse(
                    call: Call<RespostaBase<ArrayList<ClienteModelServico>>>,
                    response: Response<RespostaBase<ArrayList<ClienteModelServico>>>
                ) {
                    Log.d("sinc_clientes", "Sincronizando clientes, pagina: $paginaAtual")

                    if (response.isSuccessful) {

                        if (response.body()!!.corpo.isEmpty()) {
                            iOnSincronizar.terminouSincronizar()
                        } else {
                            sincronizarPaginaClientes(paginaAtual + 1, totalRequisicoes, iOnSincronizar)
                            iOnSincronizar.sincronizando()
                        }

                    } else {
                        Log.e("erro_sinc_clientes", "Ocorreu um erro na sinc de clientes.")

                        iOnSincronizar.erro("Ocorreu um erro na sinc de clientes.")
                    }

                }

                override fun onFailure(
                    call: Call<RespostaBase<ArrayList<ClienteModelServico>>>,
                    t: Throwable
                ) {
                    Log.e("erro_sinc_clientes", t.message.toString())

                    iOnSincronizar.erro(t.message.toString())
                }

            })
        }

    }

}