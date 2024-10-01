package com.example.next_vendas.servico

data class RespostaBase<T>(
    var mensagem: String = "",
    var ok: Boolean = true,
    var corpo: T
)