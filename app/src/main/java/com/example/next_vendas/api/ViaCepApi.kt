package com.example.next_vendas.api

import android.util.Log
import com.example.next_vendas.model.Endereco
import com.example.next_vendas.model_servico.EnderecoViaCepModelServico
import com.example.next_vendas.servico.Servico
import com.example.next_vendas.servico.ViaCEPServico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViaCepApi {

    companion object {

        // buscar endereço pelo cep consumindo a api do ViaCEP
        fun buscarEnderecoPeloCep(
            cep: String,
            onRealizandoRequisicao: () -> Unit,
            onErroRequisicao: (String) -> Unit,
            onSucessoRequisicao: (Endereco) -> Unit
        ) {
            val servico: Servico = Servico()
            val viaCepServico: ViaCEPServico = servico.getViaCepServico()

            onRealizandoRequisicao()

            viaCepServico.buscarEnderecoPeloCep(cep)
                .enqueue(object : Callback<EnderecoViaCepModelServico> {

                    override fun onResponse(call: Call<EnderecoViaCepModelServico>, response: Response<EnderecoViaCepModelServico>) {

                        if (response.isSuccessful) {
                            val enderecoViaCepModelServico = response.body()!!

                            if (enderecoViaCepModelServico.erro) {
                                onErroRequisicao("Não foi encontrado um endereço para esse cep.")
                            } else {
                                val endereco: Endereco = Endereco()
                                endereco.cep = enderecoViaCepModelServico.cep
                                endereco.complemento = enderecoViaCepModelServico.complemento
                                endereco.bairro = enderecoViaCepModelServico.bairro
                                endereco.cidade = enderecoViaCepModelServico.localidade
                                endereco.endereco = enderecoViaCepModelServico.logradouro
                                endereco.uf = enderecoViaCepModelServico.estado

                                onSucessoRequisicao(endereco)
                            }

                        } else {
                            // ocorreu um erro
                            onErroRequisicao("Ocorreu um erro ao tentar-se consultar o endereço pelo cep.")
                        }

                    }

                    override fun onFailure(call: Call<EnderecoViaCepModelServico>, t: Throwable) {
                        Log.e("erro_consultar_endereco_cep", t.message.toString())

                        onErroRequisicao("Ocorreu o seguinte erro ao tentar-se consultar o endereço pelo cep: ${ t.message.toString() }")
                    }

                })

        }

    }

}