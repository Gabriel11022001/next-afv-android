package com.example.next_vendas.api

import android.content.SharedPreferences
import android.util.Log
import com.example.next_vendas.model_servico.AlteracaoSenhaModelServico
import com.example.next_vendas.model_servico.UsuarioModelServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import com.example.next_vendas.utils.salvarDadosUsuarioLogadoPreferenciasCompartilhadas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginApi(
    private val sharedPreferencesUsuarioLogado: SharedPreferences
) {

    fun login(email: String, senha: String, iOnEnviarServidor: IOnEnviarServidor) {
        Log.e("login", "Realizando requisição de login...")
        val servicoLogin = Servico()
            .getLoginService()

        val usuarioModelServico: UsuarioModelServico = UsuarioModelServico()
        usuarioModelServico.email = email
        usuarioModelServico.senha = senha

        servicoLogin.login(usuarioModelServico)
            .enqueue(object : Callback<RespostaBase<UsuarioModelServico>> {

                override fun onResponse(call: Call<RespostaBase<UsuarioModelServico>>, response: Response<RespostaBase<UsuarioModelServico>>) {
                    // requisição foi processada corretamente

                    if (response.isSuccessful) {
                        Log.d("teste", "Realizou requisição com sucesso!")
                        val corpoResposta: RespostaBase<UsuarioModelServico> = response.body()!!

                        if (corpoResposta.mensagem == "Login efetuado com sucesso.") {
                            // validar se o usuário está com perfil ativo

                            if (!corpoResposta.corpo.ativo) {
                                // usuário não está ativo
                                iOnEnviarServidor.erro("Seu perfil está inativo, entre em contato com o administrados do sistema e solicite o desbloqueio.")
                            } else {
                                val usuarioLogado: UsuarioModelServico = corpoResposta.corpo
                                // salvar os dados do usuário logado nas preferências compartilhadas
                                salvarDadosUsuarioLogadoPreferenciasCompartilhadas(usuarioLogado, sharedPreferencesUsuarioLogado)
                                iOnEnviarServidor.sucesso("Login efetuado com sucesso.")
                            }

                        } else {
                            iOnEnviarServidor.erro(corpoResposta.mensagem)
                        }

                    } else {
                        iOnEnviarServidor.erro("Ocorreu um erro ao tentar-se realizar login.")
                    }

                }

                override fun onFailure(call: Call<RespostaBase<UsuarioModelServico>>, t: Throwable) {
                    // erro ao tentar-se consumir o endpoint do login
                    iOnEnviarServidor.erro("Ocorreu o seguinte erro ao tentar-se realizar login: ${ t.message.toString() }.")
                }

            })
    }

    fun alterarSenhaUsuario(
        emailUsuarioLogado: String,
        senhaAtualUsuarioLogado: String,
        novaSenha: String,
        iOnEnviarServidor: IOnEnviarServidor
    ) {
        val loginServico = Servico().getLoginService()

        val alteracaoSenhaModelServico = AlteracaoSenhaModelServico(
            emailUsuarioLogado = emailUsuarioLogado,
            senhaAtualUsuarioLogado = senhaAtualUsuarioLogado,
            novaSenha = novaSenha
        )

        loginServico.alterarSenhaUsuarioLogado(alteracaoSenhaModelServico)
            .enqueue(object : Callback<RespostaBase<String>> {

                override fun onResponse(call: Call<RespostaBase<String>>, response: Response<RespostaBase<String>>) {

                    if (response.isSuccessful) {
                        val respostaMensagem = response.body()!!.mensagem

                        if (respostaMensagem == "Senha alterada com sucesso.") {
                            Log.d("alterar_senha", "Senha alterada com sucesso.")

                            iOnEnviarServidor.sucesso("Senha alterada com sucesso, você será redirecionado a tela de login.")
                        } else {
                            Log.d("erro_alterar_senha", respostaMensagem)

                            iOnEnviarServidor.erro(respostaMensagem)
                        }

                    } else {
                        // erro ao tentar-se alterar a senha
                        Log.d("erro_alterar_senha", "Ocorreu um erro ao tentar-se alterar a senha.")

                        iOnEnviarServidor.erro("Ocorreu um erro ao tentar-se alterar a senha, tente novamente.")
                    }

                }

                override fun onFailure(call: Call<RespostaBase<String>>, t: Throwable) {
                    Log.e("erro_alterar_senha", t.message.toString())

                    iOnEnviarServidor.erro("Ocorreu um erro ao tentar-se alterar a senha, tente novamente.")
                }

            })
    }

}