package com.example.next_vendas.api

interface IOnSincronizar {

    fun sincronizando()

    fun terminouSincronizar()

    fun erro(mensagemErro: String)

}