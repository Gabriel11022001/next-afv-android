package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class ConsultaUsuarioLogadoModelServico(
    @SerializedName("id_usuario_logado")
    var idUsuarioLogado: Int = 0
)