package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class ClienteModelServico(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("tipo_pessoa")
    var tipoPessoa: String = "",
    @SerializedName("nome_completo")
    var nomeCompleto: String = "",
    @SerializedName("razao_social")
    var razaoSocial: String = "",
    @SerializedName("telefone_celular")
    var telefoneCelular: String = "",
    @SerializedName("telefone_complementar")
    var telefoneComplementar: String = "",
    @SerializedName("telefone_fixo")
    var telefoneFixo: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("cpf")
    var cpf: String = "",
    @SerializedName("cnpj")
    var cnpj: String = "",
    @SerializedName("data_nascimento")
    var dataNascimento: String = "",
    @SerializedName("sexo")
    var sexo: String = "",
    @SerializedName("inscricao_estadual")
    var inscricaoEstadual: String = "",
    @SerializedName("link_site_empresa")
    var linkSiteEmpresa: String = "",
    @SerializedName("data_cadastro")
    var dataCadastro: String = "",
    @SerializedName("cep")
    var cep: String = "",
    @SerializedName("complemento")
    var complemento: String = "",
    @SerializedName("endereco")
    var endereco: String = "",
    @SerializedName("bairro")
    var bairro: String = "",
    @SerializedName("cidade")
    var cidade: String = "",
    @SerializedName("estado")
    var estado: String = "",
    @SerializedName("numero")
    var numero: String = ""
)