package com.example.next_vendas.model

data class PessoaJuridica(
    var razaoSocial: String = "",
    var cnpj: String = "",
    var inscricaoEstadual: String = "",
    var site: String = ""
): Pessoa()