package com.example.next_vendas.model_servico

data class EnderecoViaCepModelServico(
    var cep: String = "",
    var logradouro: String = "",
    var complemento: String = "",
    var bairro: String = "",
    var localidade: String = "",
    var estado: String = "",
    var erro: Boolean = false
)