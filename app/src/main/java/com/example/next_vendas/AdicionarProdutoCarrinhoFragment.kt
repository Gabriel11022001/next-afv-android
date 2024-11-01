package com.example.next_vendas

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.next_vendas.adapter.ProdutoAdicionarCarrinhoAdapter
import com.example.next_vendas.dao.VendaDAO
import com.example.next_vendas.model.ProdutoCarrinho
import com.example.next_vendas.utils.Alerta

class AdicionarProdutoCarrinhoFragment : Fragment() {

    private lateinit var contexto: Context
    private var idVenda: Int = 0
    private lateinit var recyclerProdutosAdicionarCarrinho: RecyclerView
    private lateinit var produtoAdicionarCarrinhoAdapter: ProdutoAdicionarCarrinhoAdapter
    private lateinit var vendaDAO: VendaDAO
    private lateinit var alerta: Alerta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            this.idVenda = requireArguments().getInt("id_venda", 0)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_adicionar_produto_carrinho, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.contexto = view.context
        this.vendaDAO = VendaDAO(this.contexto)
        this.alerta = Alerta(this.contexto)

        // configurar RecyclerView
        this.recyclerProdutosAdicionarCarrinho = view.findViewById(R.id.recycler_produtos_adicionar_carrinho)
        this.recyclerProdutosAdicionarCarrinho.layoutManager = LinearLayoutManager(this.contexto)

        // configurar listener de incrementar unidades do produto no carrinho
        val onIncrementarUnidadesProdutoCarrinho: (ProdutoCarrinho) -> Unit = {

        }

        // configurar listener de decrementar unidades do produto no carrinho
        val onDecrementarUnidadesProdutoCarrinho: (ProdutoCarrinho) -> Unit = {

        }

        this.produtoAdicionarCarrinhoAdapter = ProdutoAdicionarCarrinhoAdapter(
            this.contexto,
            onIncrementarUnidadesProdutoCarrinho,
            onDecrementarUnidadesProdutoCarrinho
        )

        this.recyclerProdutosAdicionarCarrinho.adapter = this.produtoAdicionarCarrinhoAdapter
    }

    override fun onResume() {
        super.onResume()

        // procurar os produtos que serão adicionados no carrinho de compras
        this.listarProdutosAdicionarCarrinho()
    }

    private fun listarProdutosAdicionarCarrinho() {

        try {
            val produtosAdicionarCarrinho = this.vendaDAO.buscarProdutosAdicionarCarrinho(this.idVenda)

            if (produtosAdicionarCarrinho.size == 0) {
                this.alerta.apresentar("Não foram encontrados produtos.", true)
            } else {
                this.produtoAdicionarCarrinhoAdapter.setProdutosAdicionarCarrinho(produtosAdicionarCarrinho)
                this.produtoAdicionarCarrinhoAdapter.notifyDataSetChanged()
            }

        } catch (e: Exception) {
            this.alerta.apresentar("Erro ao tentar-se consultar os produtos.", true)
            Log.e("erro_consultar_produtos_carrinho", e.message.toString(), e)
        }

    }

}