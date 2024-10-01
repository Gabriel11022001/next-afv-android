package com.example.next_vendas.api

import android.content.SharedPreferences
import android.util.Log
import com.example.next_vendas.model_servico.ConfiguracaoModelServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import com.example.next_vendas.utils.salvarConfiguracoesPreferenciasCompartilhadas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfiguracaoApi(
    val sharedPreferencesConfiguracoes: SharedPreferences
) {

    fun sincronizarConfiguracoes(iOnSincronizar: IOnSincronizar) {
        val configuracaoServico = Servico().getConfiguracaoService()

        iOnSincronizar.sincronizando()

        configuracaoServico.sincronizarConfiguracoes()
            .enqueue(object : Callback<RespostaBase<ConfiguracaoModelServico>> {

                override fun onResponse(
                    call: Call<RespostaBase<ConfiguracaoModelServico>>,
                    response: Response<RespostaBase<ConfiguracaoModelServico>>
                ) {

                    if (response.isSuccessful) {
                        val corpo = response.body()!!

                        if (corpo.mensagem == "Sincronização de configurações realizada com sucesso.") {
                            // salvar as configurações nas preferências compártilhadas
                            val configuracaoModelServico = corpo.corpo
                            salvarConfiguracoesPreferenciasCompartilhadas(configuracaoModelServico, sharedPreferencesConfiguracoes)
                            iOnSincronizar.terminouSincronizar()
                        } else {
                            iOnSincronizar.erro(corpo.mensagem)
                        }

                    } else {
                        iOnSincronizar.erro("Ocorreu um erro ao tentar-se sincronizar as configurações, tente novamente.")
                        Log.e("erro_sinc_configuracoes", response.message())
                    }

                }

                override fun onFailure(
                    call: Call<RespostaBase<ConfiguracaoModelServico>>,
                    t: Throwable
                ) {
                    // erro ao tentar-se sincronizar as configurações
                    iOnSincronizar.erro("Ocorreu um erro ao tentar-se sincronizar as configurações, tente novamente.")
                    Log.e("erro_sinc_configuracoes", t.message.toString())
                }

            })
    }

}