package com.example.next_vendas.model_servico

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class ConfiguracaoModelServico {

    @SerializedName("ambiente")
    var ambiente: String = ""
    @SerializedName("campos_obrigatorios")
    var camposObrigatorios: JsonObject? = null
    @SerializedName("permitir_vendas_offiline")
    var permitirVendasOffiline: Boolean = true

}