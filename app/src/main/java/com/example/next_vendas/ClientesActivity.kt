package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.next_vendas.adapter.ClienteAdapter
import com.example.next_vendas.dao.ClienteDAO
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.model.PessoaFisica
import java.util.ArrayList

class ClientesActivity : AppCompatActivity(), OnClickListener {

    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var btnRetornar: ImageButton
    private lateinit var txtTitulo: TextView
    private lateinit var edtPesquisarClientes: EditText
    private lateinit var btnPesquisarClientes: ImageButton
    private lateinit var recyclerClientes: RecyclerView
    private lateinit var clienteAdapter: ClienteAdapter
    private lateinit var clienteDAO: ClienteDAO
    private var clientes: ArrayList<Pessoa> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        this.clienteDAO = ClienteDAO(this)

        // mapear elementos de interface
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.recyclerClientes = findViewById(R.id.recycler_clientes)
        this.edtPesquisarClientes = findViewById(R.id.edt_pesquisar_clientes)
        this.btnPesquisarClientes = findViewById(R.id.btn_pesquisar_clientes)
        this.btnRetornar = findViewById(R.id.btn_retornar)

        // mapear eventos
        this.btnRetornar.setOnClickListener(this)
        this.btnAdicionar.setOnClickListener(this)
        this.btnPesquisarClientes.setOnClickListener(this)

        // configurar adapter / RecyclerView
        this.clienteAdapter = ClienteAdapter(this)
        this.recyclerClientes.layoutManager = LinearLayoutManager(this)
        this.recyclerClientes.adapter = this.clienteAdapter

        this.edtPesquisarClientes.requestFocus()
        this.txtTitulo.text = "Clientes"
        this.btnFiltro.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        // consultar clientes cadastrados na base de dados
        this.listarClientes()
    }

    private fun listarClientes() {

        try {
            val primeiroCliente = PessoaFisica()
            primeiroCliente.id = 1
            primeiroCliente.nome = "Gabriel Rodrigues dos Santos"
            primeiroCliente.cpf = "123.456.789-00"

            for (i in 1..100) {
                this.clientes.add(primeiroCliente)
            }

            this.clienteAdapter.setClientes(this.clientes)
        } catch (e: Exception) {

        }

    }

    private fun pesquisar() {

        try {

        } catch (e: Exception) {

        }

    }

    private fun voltar() {

    }

    private fun redirecionar(tela: String) {

        if (tela == "cadastro_cliente") {
            startActivity(Intent(this, CadastroClienteActivity::class.java))
        }

    }

    override fun onBackPressed() {
        this.voltar()
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_adicionar_menu) {
            // adicionar um cliente
            this.redirecionar("cadastro_cliente")
        } else if (p0!!.id == R.id.btn_retornar) {
            this.voltar()
        }

    }

}