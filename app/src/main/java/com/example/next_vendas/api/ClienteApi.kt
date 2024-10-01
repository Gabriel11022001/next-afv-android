package com.example.next_vendas.api

import android.hardware.lights.Light
import android.util.Log
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.model_servico.ClienteModelServico
import com.example.next_vendas.servico.ClienteServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import com.example.next_vendas.utils.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ClienteApi {

    companion object {

        private val TOTAL_CLIENTES_POR_PAGINA: Int = 5

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

        fun sincronizarClientes(totalClientes: Int, iOnSincronizar: IOnSincronizar) {

        }

    }

}