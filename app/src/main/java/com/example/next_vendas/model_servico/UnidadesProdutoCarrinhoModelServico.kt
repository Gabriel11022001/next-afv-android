package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class UnidadesProdutoCarrinhoModelServico(
    @SerializedName("id_produto_servidor")
    var idProdutoServidor: Int = 0,
    @SerializedName("unidades_carrinho")
    var unidadesCarrinho: Int = 0
)