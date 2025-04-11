package com.example.next_vendas.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.R
import com.example.next_vendas.model.Filtro

class DialogFiltro(
    private val contexto: Context,
    private val tipo: String,
    private val titulo: String = "",
    private val onFiltrar: (Filtro) -> Unit
): DialogBase(contexto = contexto) {

    init {
        super.builderDialog = AlertDialog.Builder(this.contexto)

        val tituloApresentar = if (this.titulo.isBlank()) { "Filtro" } else { this.titulo }

        // super.builderDialog?.setTitle(tituloApresentar)

        var viewDialog: View? = null

        if (this.tipo == "produtos") {
            viewDialog = super.layoutInflater.inflate(R.layout.dialog_filtro_produtos, null, false)
            val edtNomeProdutoFiltro: EditText = viewDialog.findViewById(R.id.edt_nome_produto_filtro)
            val edtPrecoProdutoFiltroDe: EditText = viewDialog.findViewById(R.id.edt_preco_produto_filtro_de)
            val edtPrecoProdutoFiltroAte: EditText = viewDialog.findViewById(R.id.edt_preco_produto_filtro_ate)
            val btnAplicarFiltroProdutos: AppCompatButton = viewDialog.findViewById(R.id.btn_filtrar_produtos)

            // evento de clique no botão para filtrar os produtos
            btnAplicarFiltroProdutos.setOnClickListener {
                val where = ""

                val nomeProdutoInformado = edtNomeProdutoFiltro.text.toString().trim()
                val precoProdutoDe = edtPrecoProdutoFiltroDe.text.toString().trim()
                val precoProdutoAte = edtPrecoProdutoFiltroAte.text.toString().trim()

                val filtro = Filtro(
                    "produtos",
                    where,
                    arrayListOf(
                        nomeProdutoInformado,
                        precoProdutoDe,
                        precoProdutoAte
                    )
                )

                this.onFiltrar(filtro)
            }

        } else if (this.tipo == "vendas") {

        }

        if (viewDialog != null) {
            super.builderDialog?.setView(viewDialog)
            super.dialog = super.builderDialog?.create()
        }

    }

    fun apresentarFiltro() {
        this.dialog?.show()
    }

}