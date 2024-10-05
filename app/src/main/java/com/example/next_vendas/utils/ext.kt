package com.example.next_vendas.utils

fun String.obterIniciaisNomeSobrenome(): String {
    val nome: String = this
    val nomePalavras = nome.split(" ")
    val primeiroNome = nomePalavras[ 0 ]
    val sobrenome = nomePalavras[ 1 ]
    val primeiroCaracterNome = primeiroNome.first().uppercase()
    val primeiroCaracterSobrenome = sobrenome.first().uppercase()

    return primeiroCaracterNome + primeiroCaracterSobrenome
}