package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.next_vendas.adapter.OpcaoPerfilClienteAdapter
import com.example.next_vendas.dao.ClienteDAO
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.utils.Constantes
import com.example.next_vendas.utils.obterIniciaisNomeSobrenome

class ClienteDetalhesActivity : AppCompatActivity(), OnClickListener {

    private lateinit var txtTitulo: TextView
    private lateinit var btnFiltro: ImageButton
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnRetornar: ImageButton

    private lateinit var txtIniciaisCliente: TextView
    private lateinit var txtDocumentoCliente: TextView
    private lateinit var txtCliente: TextView
    private lateinit var txtEmailCliente: TextView
    private lateinit var txtTelefoneCelularCliente: TextView
    private lateinit var txtTelefoneComplementarCliente: TextView
    private lateinit var txtTelefoneFixoCliente: TextView

    // campos da pessoa fisica
    private lateinit var txtLabelGeneroCliente: TextView
    private lateinit var txtLabelDataNascimentoCliente: TextView
    private lateinit var txtGeneroCliente: TextView
    private lateinit var txtDataNascimentoCliente: TextView

    private lateinit var cliente: Pessoa
    private lateinit var clienteDAO: ClienteDAO

    private lateinit var recyclerOpcoesPerfilCliente: RecyclerView
    private val opcoesPerfilCliente: ArrayList<String> = arrayListOf(
        "Editar",
        "Deletar",
        "Vendas",
        "Realizar venda"
    )
    private lateinit var opcaoPerfilClienteAdapter: OpcaoPerfilClienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_detalhes)

        this.clienteDAO = ClienteDAO(this)

        // mapear elementos de interface
        this.mapearElementosInterface()

        val idCliente: Int = intent.getIntExtra("cliente_id", 0)

        if (idCliente != 0) {
            // buscar o cliente pelo id
            this.buscarCliente(idCliente = idCliente)
        }

        this.txtTitulo.text = "Detalhes do cliente"
        this.btnFiltro.visibility = View.GONE
        this.btnAdicionar.visibility = View.GONE

        this.btnRetornar.setOnClickListener(this)

        // configurar RecyclerView com opções do perfil do cliente
        this.recyclerOpcoesPerfilCliente.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // configurar evento de selecionar opção do perfil
        val iOnRealizarOperacaoPerfilCliente: (String) -> Unit = { opcao ->

            if (opcao == "Editar") {
                editarCliente()
            } else if (opcao == "Deletar") {
                deletarCliente()
            } else if (opcao == "Vendas") {
                listarComprasCliente()
            } else {
                realizarVendaCliente()
            }

        }

        this.opcaoPerfilClienteAdapter = OpcaoPerfilClienteAdapter(
            this,
            this.opcoesPerfilCliente,
            iOnRealizarOperacaoPerfilCliente = iOnRealizarOperacaoPerfilCliente
        )

        this.recyclerOpcoesPerfilCliente.adapter = this.opcaoPerfilClienteAdapter
    }

    private fun mapearElementosInterface() {
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnRetornar = findViewById(R.id.btn_retornar)
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)

        this.recyclerOpcoesPerfilCliente = findViewById(R.id.recycler_opcoes_perfil_cliente)

        this.txtIniciaisCliente = findViewById(R.id.txt_iniciais_cliente)
        this.txtCliente = findViewById(R.id.txt_nome_razao_social_cliente)
        this.txtDocumentoCliente = findViewById(R.id.txt_documento_cliente)
        this.txtEmailCliente = findViewById(R.id.txt_email_cliente)
        this.txtTelefoneCelularCliente = findViewById(R.id.txt_telefone_celular_cliente)
        this.txtTelefoneFixoCliente = findViewById(R.id.txt_telefone_fixo_cliente)
        this.txtTelefoneComplementarCliente = findViewById(R.id.txt_telefone_complementar_cliente)

        this.txtLabelGeneroCliente = findViewById(R.id.txt_label_genero_cliente)
        this.txtLabelDataNascimentoCliente = findViewById(R.id.txt_label_data_nascimento_cliente)
        this.txtGeneroCliente = findViewById(R.id.txt_genero_cliente)
        this.txtDataNascimentoCliente = findViewById(R.id.txt_data_nascimento_cliente)
    }

    private fun buscarCliente(idCliente: Int) {

        try {
            val clienteEncontrado = this.clienteDAO.buscarClientePeloId(idCliente)

            if (clienteEncontrado == null) {
                // não foi encontrado o cliente com o id informado
            } else {
                // foi encontrado o cliente com sucesso
                this.cliente = clienteEncontrado
                this.apresentarCliente()
            }

        } catch (e: Exception) {
            Log.e("erro_buscar_cliente", e.message.toString())
        }

    }

    private fun apresentarCliente() {
        this.txtLabelGeneroCliente.visibility = GONE
        this.txtLabelDataNascimentoCliente.visibility = GONE
        this.txtGeneroCliente.visibility = GONE
        this.txtDataNascimentoCliente.visibility = GONE

        if (this.cliente.tipoPessoa == Constantes.FISICA) {
            this.txtLabelGeneroCliente.visibility = VISIBLE
            this.txtLabelDataNascimentoCliente.visibility = VISIBLE
            this.txtGeneroCliente.visibility = VISIBLE
            this.txtDataNascimentoCliente.visibility = VISIBLE

            // apresentar dados da pessoa fisica
            this.txtIniciaisCliente.text = this.cliente.nome.obterIniciaisNomeSobrenome()
            this.txtCliente.text = this.cliente.nome.uppercase()
            this.txtDocumentoCliente.text = this.cliente.cpf
            this.txtGeneroCliente.text = this.cliente.sexo.uppercase()
            this.txtDataNascimentoCliente.text = this.cliente.dataNascimento
        } else {

        }

        this.txtEmailCliente.text = this.cliente.email
        this.txtTelefoneCelularCliente.text = this.cliente.telefoneCelular

        if (this.cliente.telefoneFixo.isNotBlank()) {
            this.txtTelefoneFixoCliente.text = this.cliente.telefoneFixo
        } else {
            this.txtTelefoneFixoCliente.text = getString(R.string.txt_nao_informado)
        }

        if (this.cliente.telefoneComplementar.isNotBlank()) {
            this.txtTelefoneComplementarCliente.text = this.cliente.telefoneComplementar
        } else {
            this.txtTelefoneComplementarCliente.text = getString(R.string.txt_nao_informado)
        }

    }

    private fun retornar() {

    }

    private fun editarCliente() {
        val intentEditarCliente = Intent(this, CadastroClienteActivity::class.java)
        intentEditarCliente.putExtra("id_cliente", this.cliente.id)
        startActivity(intentEditarCliente)
    }

    private fun deletarCliente() {

    }

    private fun listarComprasCliente() {

    }

    private fun realizarVendaCliente() {

    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_retornar) {
            this.retornar()
        }

    }

}