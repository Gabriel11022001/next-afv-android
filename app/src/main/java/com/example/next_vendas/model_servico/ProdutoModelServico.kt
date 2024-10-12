package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class ProdutoModelServico(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("nome_produto")
    var nomeProduto: String = "",
    @SerializedName("status")
    var status: Boolean = true,
    @SerializedName("codigo")
    var codigo: String = "",
    @SerializedName("codigo_barras")
    var codigoBarras: String = "",
    @SerializedName("preco_compra")
    var precoCompra: Double = 0.0,
    @SerializedName("preco_venda")
    var precoVenda: Double = 0.0,
    @SerializedName("url_foto")
    var urlFoto: String = "",
    @SerializedName("unidades_estoque")
    var unidadesEstoque: String = "",
    @SerializedName("categoria_id")
    var categoriaId: Int = 0
)