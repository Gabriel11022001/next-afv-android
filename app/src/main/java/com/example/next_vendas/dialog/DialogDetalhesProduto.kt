package com.example.next_vendas.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import com.example.next_vendas.R
import com.example.next_vendas.model.Produto

class DialogDetalhesProduto(
    private val contexto: Context,
    private val corVermelho: Int,
    private val corVerde: Int
) {

    private lateinit var builderDialogDetalhesProduto: AlertDialog.Builder
    private lateinit var dialogDetalhesProduto: AlertDialog

    // elementos de interface do dialog
    private lateinit var btnFecharDialogDetalhesProduto: ImageButton
    private lateinit var txtNomeProduto: TextView
    private lateinit var txtCodigoProduto: TextView
    private lateinit var txtPrecoProduto: TextView
    private lateinit var txtCodigoBarrasProduto: TextView
    private lateinit var txtPrecoCompraProduto: TextView
    private lateinit var txtUnidadesEstoque: TextView
    private lateinit var txtStatusProduto: TextView
    private lateinit var txtCodigoBarras: TextView

    init {
        this.configurar()
    }

    private fun configurar() {
        val layoutInflater = LayoutInflater.from(this.contexto)
        this.builderDialogDetalhesProduto = AlertDialog.Builder(this.contexto)

        val viewDialogDetalhesProduto = layoutInflater.inflate(R.layout.dialog_detalhes_produto, null)

        // mapear elementos de interface do dialog de detalhes de produto
        this.btnFecharDialogDetalhesProduto = viewDialogDetalhesProduto.findViewById(R.id.btn_fechar_dialog_detalhes_produto)
        this.txtNomeProduto = viewDialogDetalhesProduto.findViewById(R.id.txt_nome_prod)
        this.txtPrecoProduto = viewDialogDetalhesProduto.findViewById(R.id.txt_preco_produto)
        this.txtCodigoProduto = viewDialogDetalhesProduto.findViewById(R.id.txt_codigo_produto)
        this.txtPrecoCompraProduto = viewDialogDetalhesProduto.findViewById(R.id.txt_preco_compra_produto)
        this.txtStatusProduto = viewDialogDetalhesProduto.findViewById(R.id.txt_status_produto)
        this.txtCodigoBarrasProduto = viewDialogDetalhesProduto.findViewById(R.id.txt_codigo_barras_produto)
        this.txtUnidadesEstoque = viewDialogDetalhesProduto.findViewById(R.id.txt_unidades_estoque_produto)
        this.txtCodigoBarras = viewDialogDetalhesProduto.findViewById(R.id.txt_codigo_barras_produto)

        this.btnFecharDialogDetalhesProduto.setOnClickListener {
            this.fechar()
        }

        this.builderDialogDetalhesProduto.setView(viewDialogDetalhesProduto)
        this.dialogDetalhesProduto = this.builderDialogDetalhesProduto.create()
    }

    fun visualizar(produto: Produto) {
        this.txtNomeProduto.text = produto.nome
        this.txtPrecoProduto.text = "R$${ produto.precoVenda }"
        this.txtCodigoProduto.text = produto.codigo
        this.txtCodigoBarras.text = produto.codigoBarras
        this.txtPrecoCompraProduto.text = "$${ produto.precoCompra }"
        this.txtUnidadesEstoque.text = "${ produto.unidadesEstoque } unidades em estoque"

        if (produto.status) {
            this.txtStatusProduto.text = "Ativo"
            this.txtStatusProduto.setTextColor(this.corVerde)
        } else {
            this.txtStatusProduto.text = "Inativo"
            this.txtStatusProduto.setTextColor(this.corVermelho)
        }

        this.dialogDetalhesProduto.show()
    }

    private fun fechar() {
        this.dialogDetalhesProduto.dismiss()
    }

}