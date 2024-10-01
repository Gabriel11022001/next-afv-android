package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

class UsuarioModelServico {

    @SerializedName("email")
    var email: String = ""
    @SerializedName("senha")
    var senha: String = ""
    @SerializedName("id")
    var idUsuarioLogado: Int = 0
    @SerializedName("nome_completo")
    var nomeUsuarioLogado: String = ""
    @SerializedName("nivel_acesso")
    var nivelAcessoUsuarioLogado: String = ""
    @SerializedName("ambiente")
    var ambiente: String = ""
    @SerializedName("ativo")
    var ativo: Boolean = true
    @SerializedName("telefone_celular")
    var telefoneCelular: String = ""

}