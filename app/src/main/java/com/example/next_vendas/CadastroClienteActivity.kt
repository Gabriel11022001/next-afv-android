package com.example.next_vendas

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.api.ClienteApi
import com.example.next_vendas.api.IOnEnviarServidor
import com.example.next_vendas.componentes.EditTextTelefone
import com.example.next_vendas.componentes.MascaraTextWhatcher
import com.example.next_vendas.dao.ClienteDAO
import com.example.next_vendas.model.Endereco
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.utils.Alerta
import com.example.next_vendas.utils.Constantes
import com.example.next_vendas.utils.GerenciaCampoObrigatorio
import com.example.next_vendas.utils.Loader
import com.example.next_vendas.utils.Mascaras
import com.example.next_vendas.utils.Utils

class CadastroClienteActivity : AppCompatActivity(), OnClickListener {

    private lateinit var alerta: Alerta
    private lateinit var loader: Loader
    private var cliente: Pessoa = Pessoa()
    private var idCliente: Int = 0
    private var editar: Boolean = false
    private var cadastroCompletoExpandido: Boolean = false
    private var enderecoExpandido: Boolean = false
    private lateinit var gerenciaCampoObrigatorio: GerenciaCampoObrigatorio
    private lateinit var clienteDAO: ClienteDAO
    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var icExpandirCadastroCompleto: ImageView
    private lateinit var icExpandirEndereco: ImageView

    // telefones
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
    private lateinit var edtComplemento: EditText
    private lateinit var edtCidade: EditText
    private lateinit var edtBairro: EditText
    private lateinit var edtNumero: EditText
    private lateinit var spnEstado: Spinner
    private lateinit var btnConsultarCep: ImageButton

    // pessoa fisica
    private lateinit var edtCpf: EditText
    private lateinit var edtNomeCompleto: EditText
    private lateinit var edtDataNascimento: EditText
    private lateinit var spnGenero: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

        this.clienteDAO = ClienteDAO(this)
        this.gerenciaCampoObrigatorio = GerenciaCampoObrigatorio(this)
        this.alerta = Alerta(this)
        this.loader = Loader(this)

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
        this.icExpandirEndereco = findViewById(R.id.ic_expadir_endereco)
        this.icExpandirCadastroCompleto = findViewById(R.id.ic_expadir_cadastro_completo)

        // dados básicos
        this.edtEmail = findViewById(R.id.edt_email_cliente)

        // mapear eventos
        this.linearContainerExpandirCadastroCompleto.setOnClickListener(this)
        this.linearContainerExpandirEnderecoCliente.setOnClickListener(this)
        this.btnSalvarCliente.setOnClickListener(this)

        this.rbTipoPessoaFisica.setOnCheckedChangeListener { radio, i ->
            // o usuário selecionou o rb de pessoa física
            salvarModelPessoa()
            salvarModelPessoaJuridica()
            setCamposPessoaFisica()
        }

        this.rbTipoPessoaJuridica.setOnCheckedChangeListener { radio, i ->
            // o usuário selecionou o rb de pessoa juridica
            salvarModelPessoa()
            salvarModelPessoaFisica()
            setCamposPessoaJuridica()
        }

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
    }

    private fun salvarModelPessoa() {
        this.cliente.tipoPessoa = if (this.rbTipoPessoaFisica.isChecked) Constantes.FISICA else Constantes.JURIDICA
        this.cliente.telefoneFixo = this.edtTelefoneFixo.text.toString()
        this.cliente.telefoneCelular = this.edtTelefoneCelular.text.toString()
        this.cliente.telefoneComplementar = this.edtTelefoneComplementar.text.toString()
        this.cliente.email = this.edtEmail.text.toString()

        // salvar endereço model
        this.cliente.endereco = Endereco()
        this.cliente.endereco.cep = this.edtCep.text.toString()
        this.cliente.endereco.endereco = this.edtEndereco.text.toString()
        this.cliente.endereco.complemento = this.edtComplemento.text.toString()
        this.cliente.endereco.bairro = this.edtBairro.text.toString()
        this.cliente.endereco.cidade = this.edtCidade.text.toString()
        this.cliente.endereco.uf = this.spnEstado.selectedItem.toString()
        this.cliente.endereco.numero = this.edtNumero.text.toString()
    }

    private fun salvarModelPessoaFisica() {
        this.cliente.cpf = this.edtCpf.text.toString()
        this.cliente.nome = this.edtNomeCompleto.text.toString()
        this.cliente.dataNascimento = this.edtDataNascimento.text.toString()
    }

    private fun salvarModelPessoaJuridica() {

    }

    private fun setCamposEndereco() {
        val viewCamposEndereco: View = layoutInflater.inflate(R.layout.conteudo_cadastro_endereco_cliente, null, false)
        this.linearContainerCadastroClienteEndereco.addView(viewCamposEndereco)
        // mapear os campos
        this.edtCep = viewCamposEndereco.findViewById(R.id.edt_cep_cliente)
        this.edtEndereco = viewCamposEndereco.findViewById(R.id.edt_endereco_cliente)
        this.edtComplemento = viewCamposEndereco.findViewById(R.id.edt_complemento_cliente)
        this.edtCidade = viewCamposEndereco.findViewById(R.id.edt_cidade_cliente)
        this.edtBairro = viewCamposEndereco.findViewById(R.id.edt_bairro_cliente)
        this.spnEstado = viewCamposEndereco.findViewById(R.id.spn_estado)
        this.edtNumero = viewCamposEndereco.findViewById(R.id.edt_numero_cliente)
        this.btnConsultarCep = viewCamposEndereco.findViewById(R.id.btn_consultar_cep)

        this.btnConsultarCep.setOnClickListener(this)

        // configurar a máscara do cep
        this.edtCep.addTextChangedListener(MascaraTextWhatcher(this.edtCep, Mascaras.CEP))

        // configurar spinner de estado
        val estados = listOf(
            "SP",
            "PR"
        )
        val adapterSpinnerEstados: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados)
        this.spnEstado.adapter = adapterSpinnerEstados
    }

    private fun setCamposPessoaFisica() {
        val viewConteudoCadastroPessoaFisica = layoutInflater.inflate(R.layout.conteudo_cadastro_completo_pf, null)
        this.linearContainerCadastroCompleto.removeAllViews()
        this.linearContainerCadastroCompleto.addView(viewConteudoCadastroPessoaFisica)

        // mapear campos
        this.edtNomeCompleto = viewConteudoCadastroPessoaFisica.findViewById(R.id.edt_nome_completo_cliente)
        this.edtDataNascimento = viewConteudoCadastroPessoaFisica.findViewById(R.id.edt_data_nascimento_cliente)
        this.edtCpf = viewConteudoCadastroPessoaFisica.findViewById(R.id.edt_cpf_cliente)
        this.spnGenero = viewConteudoCadastroPessoaFisica.findViewById(R.id.spn_genero)

        // definir máscaras dos campos da pessoa fisica
        this.edtCpf.addTextChangedListener(MascaraTextWhatcher(this.edtCpf, Mascaras.CPF))
        this.edtDataNascimento.addTextChangedListener(MascaraTextWhatcher(this.edtDataNascimento, Mascaras.DATA_NASCIMENTO))

        // setando os dados nos campos de pf
        this.edtNomeCompleto.setText(this.cliente.nome)
        this.edtCpf.setText(this.cliente.cpf)
        this.edtDataNascimento.setText(this.cliente.dataNascimento)
    }

    private fun setCamposPessoaJuridica() {

    }

    private fun controlarExpandirSecao(secao: String) {

        if (secao == "cadastro_completo") {

            if (!this.cadastroCompletoExpandido) {
                this.cadastroCompletoExpandido = true
                this.linearContainerCadastroCompleto.visibility = View.VISIBLE
                this.icExpandirCadastroCompleto.setImageResource(R.drawable.ic_seta_nao_expandir)
            } else {
                // esconder seção do cadastro completo
                this.cadastroCompletoExpandido = false
                this.linearContainerCadastroCompleto.visibility = View.GONE
                this.icExpandirCadastroCompleto.setImageResource(R.drawable.ic_seta_expandir)
            }

        } else {
            // seção do endereço

            if (!this.enderecoExpandido) {
                this.enderecoExpandido = true
                this.linearContainerCadastroClienteEndereco.visibility = View.VISIBLE
                this.icExpandirEndereco.setImageResource(R.drawable.ic_seta_nao_expandir)
            } else {
                this.enderecoExpandido = false
                this.linearContainerCadastroClienteEndereco.visibility = View.GONE
                this.icExpandirEndereco.setImageResource(R.drawable.ic_seta_expandir)
            }

        }

    }

    private fun validarFormularioCliente(): Boolean {
        var ok: Boolean = true

        if (this.cliente.tipoPessoa == Constantes.FISICA) {
            ok = this.validarFormularioClientePf()
        } else {
            ok = this.validarFormularioClientePj()
        }

        return ok
    }

    private fun validarFormularioClientePf(): Boolean {
        var ok = true
        val telefoneCelular: String = this.edtTelefoneCelular.text.toString().trim()
        val email: String = this.edtEmail.text.toString().trim()
        val cep: String = this.edtCep.text.toString().trim()
        val endereco: String = this.edtEndereco.text.toString().trim()
        val bairro: String = this.edtBairro.text.toString().trim()
        val cidade: String = this.edtCidade.text.toString().trim()

        if (email == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtEmail)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtEmail)
        }

        if (telefoneCelular == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtTelefoneCelular)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtTelefoneCelular)
        }

        // validar campos do endereço
        if (cep == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtCep)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtCep)
        }

        if (endereco == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtEndereco)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtEndereco)
        }

        if (bairro == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtBairro)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtBairro)
        }

        if (cidade == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtCidade)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtCidade)
        }

        return ok
    }

    private fun validarFormularioClientePj(): Boolean {
        var ok = true

        return ok
    }

    // método onde envio o cliente para o servidor
    private fun enviarClienteServidor() {

    }

    private fun salvarCliente() {

        try {

            if (this.validarFormularioCliente()) {
                this.salvarModelPessoa()
                var msgErro: String = ""

                if (this.cliente.tipoPessoa == Constantes.FISICA) {
                    this.salvarModelPessoaFisica()

                    if (!Utils.validarEmail(this.cliente.email)) {
                        msgErro = "E-mail inválido!"
                    } else if (!Utils.validarCpf(this.cliente.cpf)) {
                        msgErro = "Cpf inválido!"
                    } else if (!Utils.validarDataNascimento(this.cliente.dataNascimento)) {
                        msgErro = "Data de nascimento inválida!"
                    } else if (!Utils.validarCep(this.cliente.endereco.cep)) {
                        msgErro = "Cep inválido!"
                    }

                } else {
                    this.salvarModelPessoaJuridica()
                }

                if (msgErro.isNotEmpty()) {
                    this.alerta.apresentar(msgErro)
                } else {
                    // salvar cliente na base local
                    this.clienteDAO.cadastrar(this.cliente)

                    if (Utils.estaConectadoInternet()) {
                        // enviar cliente para o servidor
                        // this.enviarClienteServidor()
                    } else {

                    }

                }

            } else {
                this.alerta.apresentar("Preencha todos os campos obrigatórios!")
            }

        } catch (e: Exception) {

        }

    }

    private fun apresentarLoader(mensagem: String) {
        this.loader.iniciarLoader(mensagem)
    }

    private fun esconderLoader() {
        this.loader.finalizarLoader()
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