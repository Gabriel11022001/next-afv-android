package com.example.next_vendas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.next_vendas.model.Produto

class VendaCarrinhoFragment : Fragment(), OnClickListener {

    private var idVenda: Int = 0
    private var produtosCarrinho: List<Produto> = listOf()
    private var valorTotalCarrinho: Double = 0.0
    private var quantidadeTotalUnidadesCarrinho: Int = 0

    private lateinit var btnRetornar: ImageButton
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionarProdutoCarrinho: AppCompatButton
    private lateinit var recyclerCarrinho: RecyclerView
    private lateinit var txtNaoExistemProdutosCarrinho: TextView
    private lateinit var txtValorTotal: TextView
    private lateinit var txtUnidadesCarrinho: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // obter o id_venda passado como argumento
        if (arguments != null) {
            this.idVenda = requireArguments().getInt("id_venda", 0)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_venda_carrinho, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // mapear elementos de interface
        this.txtTitulo = view.findViewById(R.id.txt_titulo)
        this.btnRetornar = view.findViewById(R.id.btn_retornar)
        this.btnAdicionar = view.findViewById(R.id.btn_adicionar_menu)
        this.btnFiltro = view.findViewById(R.id.btn_filtro_menu)
        this.btnAdicionarProdutoCarrinho = view.findViewById(R.id.btn_adicionar_produto_carrinho)
        this.recyclerCarrinho = view.findViewById(R.id.recycler_items_carrinho)
        this.txtNaoExistemProdutosCarrinho = view.findViewById(R.id.txt_nenhum_produto_carrinho)
        this.txtValorTotal = view.findViewById(R.id.txt_valor_total_carrinho)
        this.txtUnidadesCarrinho = view.findViewById(R.id.txt_qtd_unidades_carrinho)

        // mapear eventos
        this.btnAdicionarProdutoCarrinho.setOnClickListener(this)
        this.btnRetornar.setOnClickListener(this)

        this.txtTitulo.text = "Venda"
        this.btnAdicionar.visibility = GONE
        this.btnFiltro.visibility = GONE

        this.txtNaoExistemProdutosCarrinho.visibility = GONE
        this.recyclerCarrinho.visibility = GONE
    }

    override fun onStart() {
        super.onStart()

        // consultar produtos que estão no carrinho
        this.buscarProdutosCarrinho()
        // calcular valor total do carrinho
        this.calcularValorTotalCarrinho()
        // contabilizar unidades no carrinho
        this.contabilizarUnidadesCarrinho()
    }

    private fun buscarProdutosCarrinho() {

    }

    private fun calcularValorTotalCarrinho() {
        this.valorTotalCarrinho = 0.0
        this.txtValorTotal.text = "R$0,00"
    }

    private fun contabilizarUnidadesCarrinho() {
        this.quantidadeTotalUnidadesCarrinho = 0
        this.txtUnidadesCarrinho.text = "0 unidades"
    }

    private fun retornar() {

    }

    private fun adicionarProdutoCarrinho() {
        val bundleContendoIdVenda = Bundle()
        bundleContendoIdVenda.putInt("id_venda", this.idVenda)

        val fragmentoAdicionarProdutosCarrinho: Fragment = AdicionarProdutoCarrinhoFragment()
        fragmentoAdicionarProdutosCarrinho.arguments = bundleContendoIdVenda

        val transacaoFragmentAdicionarProdutosCarrinho = requireActivity().supportFragmentManager.beginTransaction()
        transacaoFragmentAdicionarProdutosCarrinho.replace(R.id.fragment_container_view_realizar_venda, fragmentoAdicionarProdutosCarrinho)
        transacaoFragmentAdicionarProdutosCarrinho.addToBackStack(null)
        transacaoFragmentAdicionarProdutosCarrinho.commit()
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_retornar) {
            this.retornar()
        } else if (p0!!.id == R.id.btn_adicionar_produto_carrinho) {
            // adicionar produto no carrinho
            this.adicionarProdutoCarrinho()
        } else {
            // prosseguir para segunda etapa
        }

    }

}