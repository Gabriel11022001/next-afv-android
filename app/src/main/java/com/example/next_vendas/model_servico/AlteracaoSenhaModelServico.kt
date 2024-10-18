package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class AlteracaoSenhaModelServico(
    @SerializedName("email")
    val emailUsuarioLogado: String,
    @SerializedName("senha_anterior")
    val senhaAtualUsuarioLogado: String,
    @SerializedName("nova_senha")
    val novaSenha: String
)