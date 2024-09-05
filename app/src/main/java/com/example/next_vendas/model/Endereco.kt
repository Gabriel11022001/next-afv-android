package com.example.next_vendas.model

data class Endereco(
    var id: Int = 0,
    var endereco: String = "",
    var bairro: String = "",
    var cep: String = "",
    var numero: String = "",
    var complemento: String = "",
    var uf: String = "",
    var cidade: String = ""
)