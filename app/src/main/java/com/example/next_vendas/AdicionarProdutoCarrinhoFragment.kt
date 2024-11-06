package com.example.next_vendas

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.next_vendas.adapter.ProdutoAdicionarCarrinhoAdapter
import com.example.next_vendas.dao.CategoriaProdutoDAO
import com.example.next_vendas.dao.VendaDAO
import com.example.next_vendas.model.ProdutoCarrinho
import com.example.next_vendas.utils.Alerta
import okhttp3.internal.notifyAll

class AdicionarProdutoCarrinhoFragment : Fragment() {

    private lateinit var contexto: Context
    private var idVenda: Int = 0
    private lateinit var recyclerProdutosAdicionarCarrinho: RecyclerView
    private lateinit var spnCategoriasProdutos: Spinner
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

        this.spnCategoriasProdutos = view.findViewById(R.id.spn_categoria_produto)

        // mapear evento de selecionar o item no spinner
        this.spnCategoriasProdutos.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val categoriaSelecionada = p0!!.selectedItem.toString()

                if (categoriaSelecionada == "Todas".uppercase()) {
                    listarProdutosAdicionarCarrinho()
                } else {
                    filtrar(categoriaSelecionada)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) { }

        }

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

        // buscar categorias dos produtos para aplicar no filtro
        this.buscarCategoriasProdutos()
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

    private fun buscarCategoriasProdutos() {

        try {
            val categoriaProdutoDAO = CategoriaProdutoDAO(this.contexto)
            val categoriasProdutos = categoriaProdutoDAO.buscarCategoriasAtivas()
            val nomesCategorias: ArrayList<String> = ArrayList()

            nomesCategorias.add("Todas".uppercase())

            if (categoriasProdutos.size > 0) {

                for (categoria in categoriasProdutos) {
                    nomesCategorias.add(categoria.descricao.uppercase())
                }

                // configurar o spinner com o adapter
                val spinnerAdapterCategoriasProdutos = ArrayAdapter<String>(this.contexto, android.R.layout.simple_spinner_dropdown_item, nomesCategorias)

                this.spnCategoriasProdutos.adapter = spinnerAdapterCategoriasProdutos
                this.spnCategoriasProdutos.notifyAll()
            }

        } catch (e: Exception) {
            Log.e("erro_buscar_categorias", e.message.toString())
        }

    }

    private fun filtrar(categoriaProduto: String) {

        try {

        } catch (e: Exception) {
            Log.e("erro_filtrar_produtos_categoria", e.message.toString())
        }

    }

}