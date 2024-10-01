package com.example.next_vendas.model

import com.example.next_vendas.utils.Constantes

open class Pessoa {

    var id: Int = 0
    var telefoneFixo: String = ""
    var telefoneCelular: String = ""
    var telefoneComplementar: String = ""
    var email: String = ""
    var dataCadastro: String = ""
    var endereco: Endereco = Endereco()
    var tipoPessoa: String = Constantes.FISICA

    // pessoa física
    var nome: String = ""
    var cpf: String = ""
    var dataNascimento: String = ""
    var sexo: String = ""

    // pessoa juridica
    var razaoSocial: String = ""
    var cnpj: String = ""
    var inscricaoEstadual: String = ""
    var site: String = ""

}