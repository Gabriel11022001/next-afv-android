package com.example.next_vendas

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.componentes.EditTextTelefone
import com.example.next_vendas.componentes.MascaraTextWhatcher
import com.example.next_vendas.dao.ClienteDAO
import com.example.next_vendas.utils.GerenciaCampoObrigatorio
import com.example.next_vendas.utils.Loader
import com.example.next_vendas.utils.Mascaras

class CadastroClienteActivity : AppCompatActivity(), OnClickListener {

    private lateinit var loader: Loader
    private var idCliente: Int = 0
    private var editar: Boolean = false
    private var cadastroCompletoExpandido: Boolean = false
    private var enderecoExpandido: Boolean = false
    private lateinit var gerenciaCampoObrigatorio: GerenciaCampoObrigatorio
    private lateinit var clienteDAO: ClienteDAO
    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var edtTelefoneCelular: EditTextTelefone
    private lateinit var edtTelefoneFixo: EditTextTelefone
    private lateinit var edtTelefoneComplementar: EditTextTelefone
    private lateinit var linearContainerExpandirCadastroCompleto: LinearLayout
    private lateinit var linearContainerCadastroCompleto: LinearLayout
    private lateinit var linearContainerExpandirEnderecoCliente: LinearLayout
    private lateinit var linearContainerCadastroClienteEndereco: LinearLayout
    private lateinit var btnSalvarCliente: AppCompatButton

    // selecionar tipo de pessoa do cliente
    private lateinit var rbTipoPessoaFisica: RadioButton
    private lateinit var rbTipoPessoaJuridica: RadioButton

    // dados básicos
    private lateinit var edtEmail: EditText

    // endereço do cliente
    private lateinit var edtCep: EditText
    private lateinit var edtEndereco: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

        this.clienteDAO = ClienteDAO(this)
        this.loader = Loader(this, findViewById(R.id.container_possui_loader))
        this.gerenciaCampoObrigatorio = GerenciaCampoObrigatorio(this)

        // mapear elementos de interface
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.rbTipoPessoaJuridica = findViewById(R.id.rb_pessoa_juridica)
        this.rbTipoPessoaFisica = findViewById(R.id.rb_pessoa_fisica)
        this.edtTelefoneCelular = findViewById(R.id.edt_telefone_celular)
        this.edtTelefoneFixo = findViewById(R.id.edt_telefone_fixo)
        this.edtTelefoneComplementar  = findViewById(R.id.edt_telefone_complementar)
        this.linearContainerExpandirCadastroCompleto = findViewById(R.id.linear_container_cadastro_completo_cliente_topo)
        this.linearContainerCadastroCompleto = findViewById(R.id.linear_container_cadastro_completo_cliente_conteudo)
        this.linearContainerExpandirEnderecoCliente = findViewById(R.id.linear_container_endereco_cliente_topo)
        this.linearContainerCadastroClienteEndereco = findViewById(R.id.linear_container_endereco_cliente_conteudo)
        this.btnSalvarCliente = findViewById(R.id.btn_salvar_cadastro_cliente)

        // dados básicos
        this.edtEmail = findViewById(R.id.edt_email_cliente)

        // mapear eventos
        this.linearContainerExpandirCadastroCompleto.setOnClickListener(this)
        this.linearContainerExpandirEnderecoCliente.setOnClickListener(this)
        this.btnSalvarCliente.setOnClickListener(this)

        this.txtTitulo.text = "Cadastro de cliente"
        this.btnAdicionar.visibility = View.GONE
        this.btnFiltro.visibility = View.GONE

        // configurar mascaras nos campos de telefone
        this.edtTelefoneCelular.addTextChangedListener(MascaraTextWhatcher(this.edtTelefoneCelular, Mascaras.TELEFONE_CELULAR))
        this.edtTelefoneFixo.addTextChangedListener(MascaraTextWhatcher(this.edtTelefoneFixo, Mascaras.TELEFONE_FIXO))
        this.edtTelefoneComplementar.addTextChangedListener(MascaraTextWhatcher(this.edtTelefoneComplementar, Mascaras.TELEFONE_CELULAR))

        if (this.rbTipoPessoaFisica.isChecked) {
            this.setCamposPessoaFisica()
        } else {
            this.setCamposPessoaJuridica()
        }

        this.setCamposEndereco()

        this.loader.iniciarLoader("Enviando cliente, aguarde...")
    }

    private fun setCamposEndereco() {
        val viewCamposEndereco: View = layoutInflater.inflate(R.layout.conteudo_cadastro_endereco_cliente, null, false)
        this.linearContainerCadastroClienteEndereco.addView(viewCamposEndereco)
        // mapear os campos
        this.edtCep = viewCamposEndereco.findViewById(R.id.edt_cep_cliente)
        this.edtEndereco = viewCamposEndereco.findViewById(R.id.edt_endereco_cliente)

        // configurar a máscara do cep
        this.edtCep.addTextChangedListener(MascaraTextWhatcher(this.edtCep, Mascaras.CEP))
    }

    private fun setCamposPessoaFisica() {

    }

    private fun setCamposPessoaJuridica() {

    }

    private fun controlarExpandirSecao(secao: String) {

        if (secao == "cadastro_completo") {

            if (!this.cadastroCompletoExpandido) {
                this.cadastroCompletoExpandido = true
                this.linearContainerCadastroCompleto.visibility = View.VISIBLE
            } else {
                // esconder seção do cadastro completo
                this.cadastroCompletoExpandido = false
                this.linearContainerCadastroCompleto.visibility = View.GONE
            }

        } else {
            // seção do endereço

            if (!this.enderecoExpandido) {
                this.enderecoExpandido = true
                this.linearContainerCadastroClienteEndereco.visibility = View.VISIBLE
            } else {
                this.enderecoExpandido = false
                this.linearContainerCadastroClienteEndereco.visibility = View.GONE
            }

        }

    }

    private fun validarFormularioCliente(): Boolean {
        var ok: Boolean = true

        return ok
    }

    private fun enviarClienteServidor() {

    }

    private fun salvarCliente() {
        this.apresentarLoader()

        try {

            if (this.validarFormularioCliente()) {
                this.enviarClienteServidor()
            }

        } catch (e: Exception) {
            this.esconderLoader()
            // apresentar mensagem informando que ocorre um erro ao tentar-se enviar o cliente para o servidor
        }

    }

    private fun apresentarLoader() {
        // desabilitar todos os elementos de interface

        // apresentar loader
    }

    private fun esconderLoader() {
        // habilitar todos os elementos de interface

        // esconder loader
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.linear_container_cadastro_completo_cliente_topo) {
            this.controlarExpandirSecao("cadastro_completo")
        } else if (p0!!.id == R.id.linear_container_endereco_cliente_topo) {
            this.controlarExpandirSecao("endereco")
        } else if (p0!!.id == R.id.btn_salvar_cadastro_cliente) {
            this.salvarCliente()
        }

    }

}