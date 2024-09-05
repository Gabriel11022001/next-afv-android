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

}