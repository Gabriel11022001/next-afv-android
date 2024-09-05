package com.example.next_vendas.model

data class PessoaFisica(
    var nome: String = "",
    var cpf: String = "",
    var dataNascimento: String = "",
    var sexo: String = ""
): Pessoa()