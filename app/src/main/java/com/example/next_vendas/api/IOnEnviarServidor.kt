package com.example.next_vendas.api

interface IOnEnviarServidor {

    fun sucesso(mensagemSucesso: String)

    fun erro(mensagemErro: String)

}