package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
import com.example.next_vendas.utils.AlertaConfirmarPadrao
import com.example.next_vendas.utils.Constantes
import com.example.next_vendas.utils.GerenciaCampoObrigatorio
import com.example.next_vendas.utils.Loader
import com.example.next_vendas.utils.Mascaras
import com.example.next_vendas.utils.removerMascaras
import com.example.next_vendas.utils.validarCep
import com.example.next_vendas.utils.validarCpf
import com.example.next_vendas.utils.validarDataNascimento
import com.example.next_vendas.utils.validarEmail
import com.example.next_vendas.utils.validarEstaConectadoInternet
import java.util.ArrayList

class CadastroClienteActivity : AppCompatActivity(), OnClickListener {

    private lateinit var alertaConfirmarPadrao: AlertaConfirmarPadrao
    private lateinit var alerta: Alerta
    private lateinit var loader: Loader
    private var cliente: Pessoa = Pessoa()
    private var clienteId: Int = 0
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
    private lateinit var rbGrupoTipoPessoa: RadioGroup
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

    // pessoa juridica
    private lateinit var edtRazaoSocial: EditText
    private lateinit var edtCnpj: EditText
    private lateinit var edtInscricaoEstadual: EditText
    private lateinit var edtLinkSiteEmpresa: EditText

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
        this.rbGrupoTipoPessoa = findViewById(R.id.radio_group_tipo_pessoa)
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

        this.rbGrupoTipoPessoa.setOnCheckedChangeListener { radioGrupoTipoPessoa, idRadioTipoPessoaSelecionado ->

            if (idRadioTipoPessoaSelecionado == R.id.rb_pessoa_fisica) {
                // o usuário selecionou o rb de pessoa física
                salvarModelPessoa()
                salvarModelPessoaJuridica()
                setCamposPessoaFisica()
            } else {
                // o usuário selecionou o rb de pessoa juridica
                salvarModelPessoa()
                salvarModelPessoaFisica()
                setCamposPessoaJuridica()
            }

        }

        this.txtTitulo.text = "Cadastro de cliente"
        this.btnAdicionar.visibility = View.GONE
        this.btnFiltro.visibility = View.GONE

        // configurar mascaras nos campos de telefone
        this.edtTelefoneCelular.addTextChangedListener(MascaraTextWhatcher(this.edtTelefoneCelular, Mascaras.TELEFONE_CELULAR))
        this.edtTelefoneFixo.addTextChangedListener(MascaraTextWhatcher(this.edtTelefoneFixo, Mascaras.TELEFONE_FIXO))
        this.edtTelefoneComplementar.addTextChangedListener(MascaraTextWhatcher(this.edtTelefoneComplementar, Mascaras.TELEFONE_CELULAR))

        // validar se id_cliente foi informado na intent
        val clienteIdInformadoIntent = intent.getIntExtra("id_cliente", 0)

        if (clienteIdInformadoIntent != 0) {
            // buscar cliente pelo id
            this.clienteId = clienteIdInformadoIntent
            this.editar = true
            this.cliente = this.clienteDAO.buscarClientePeloId(this.clienteId)!!

            if (this.cliente.tipoPessoa == Constantes.FISICA) {
                this.rbTipoPessoaFisica.isChecked = true
            } else {
                this.rbTipoPessoaJuridica.isChecked = true
            }

        }

        if (this.rbTipoPessoaFisica.isChecked) {
            this.setCamposPessoaFisica()
        } else {
            this.setCamposPessoaJuridica()
        }

        this.setCamposEndereco()

        if (this.clienteId != 0) {

            if (this.cliente.endereco.cep.isNotBlank()) {
                // expandir seção com os dados do endereço
                this.enderecoExpandido = true
                this.linearContainerCadastroClienteEndereco.visibility = View.VISIBLE
                this.icExpandirEndereco.setImageResource(R.drawable.ic_seta_nao_expandir)

                this.apresentarEnderecoCliente()
            }

            // expandir seção do cadastro completo
            this.cadastroCompletoExpandido = true
            this.linearContainerCadastroCompleto.visibility = View.VISIBLE
            this.icExpandirCadastroCompleto.setImageResource(R.drawable.ic_seta_nao_expandir)

            this.apresentarDadosCliente()
        }

        /**
         * evento que vai redirecionar o usuário para a tela com os detalhes do cliente
         * cadastrado
         */
        val onConfirmarCadastroCliente: () -> Unit = {

            if (clienteId != 0) {
                this.redirecionarUsuarioTelaDetalhesCliente()
            }

        }

        this.alertaConfirmarPadrao = AlertaConfirmarPadrao(this, "Cadastro de cliente", onConfirmarCadastroCliente)
    }

    // redirecionar o usuário para a tela com os detalhes do cliente consultado a partir do id passado
    private fun redirecionarUsuarioTelaDetalhesCliente() {
        val intentTelaDetalhesCliente = Intent(this, ClienteDetalhesActivity::class.java)
        intentTelaDetalhesCliente.putExtra("cliente_id", this.clienteId)
        startActivity(intentTelaDetalhesCliente)
        finish()
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

        val generoSelecionado: String = this.spnGenero.selectedItem.toString()
        this.cliente.sexo = generoSelecionado
    }

    private fun salvarModelPessoaJuridica() {
        this.cliente.cnpj = this.edtCnpj.text.toString()
        this.cliente.razaoSocial = this.edtRazaoSocial.text.toString()
        this.cliente.inscricaoEstadual = this.edtInscricaoEstadual.text.toString()
        this.cliente.site = this.edtLinkSiteEmpresa.text.toString()
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

        val generos: ArrayList<String> = arrayListOf(
            "Masculino",
            "Feminino",
            "Outro"
        )

        // setando os gêneros no spinner de gêneros de pessoa fisica
        this.spnGenero.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, generos)
    }

    private fun setCamposPessoaJuridica() {
        val viewConteudoCadastroPessoaJuridica: View = layoutInflater.inflate(R.layout.conteudo_cadastro_completo_pj, null)
        this.linearContainerCadastroCompleto.removeAllViews()
        this.linearContainerCadastroCompleto.addView(viewConteudoCadastroPessoaJuridica)

        // mapear campos
        this.edtCnpj = viewConteudoCadastroPessoaJuridica.findViewById(R.id.edt_cnpj_cliente_pj)
        this.edtRazaoSocial = viewConteudoCadastroPessoaJuridica.findViewById(R.id.edt_razao_social_cliente_pj)
        this.edtInscricaoEstadual = viewConteudoCadastroPessoaJuridica.findViewById(R.id.edt_inscricao_estadual_cliente_pj)
        this.edtLinkSiteEmpresa = viewConteudoCadastroPessoaJuridica.findViewById(R.id.edt_link_site_cliente_pj)

        // definir máscara do cnpj no campo de cnpj da pessoa juridica
        this.edtCnpj.addTextChangedListener(MascaraTextWhatcher(this.edtCnpj, Mascaras.CNPJ))
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
        val nomeCompleto: String = this.edtNomeCompleto.text.toString().trim()
        val dataNascimento: String = this.edtDataNascimento.text.toString().trim()
        val cpf: String = this.edtCpf.text.toString().trim()
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

        if (nomeCompleto == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtNomeCompleto)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtNomeCompleto)
        }

        if (dataNascimento == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtDataNascimento)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtDataNascimento)
        }

        if (cpf == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtCpf)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtCpf)
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
        val telefoneCelular: String = this.edtTelefoneCelular.text.toString().trim()
        val email: String = this.edtEmail.text.toString().trim()
        val cnpj: String = this.edtCnpj.text.toString()
        val razaoSocial: String = this.edtRazaoSocial.text.toString()
        val inscricaoEstadual: String = this.edtInscricaoEstadual.text.toString()

        if (telefoneCelular == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtTelefoneCelular)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtTelefoneCelular)
        }

        if (email == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtEmail)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtEmail)
        }

        if (cnpj == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtCnpj)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtCnpj)
        }

        if (razaoSocial == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtRazaoSocial)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtRazaoSocial)
        }

        if (inscricaoEstadual == "") {
            ok = false
            this.gerenciaCampoObrigatorio.setErroCampoObrigatorio(this.edtInscricaoEstadual)
        } else {
            this.gerenciaCampoObrigatorio.removeErroCampoOgrigatorio(this.edtInscricaoEstadual)
        }

        return ok
    }

    // método onde envio o cliente para o servidor
    private fun enviarClienteServidor(idCliente: Int) {

        if (validarEstaConectadoInternet(this)) {
            apresentarLoader("Enviando dados do cliente para o servidor, aguarde...")

            val clienteEnviar: Pessoa? = this.clienteDAO.buscarClientePeloId(idCliente)

            if (clienteEnviar != null) {

                ClienteApi.cadastrarCliente(clienteEnviar, object : IOnEnviarServidor {

                    override fun sucesso(mensagemSucesso: String) {
                        esconderLoader()
                        clienteDAO.setClienteEnviadoServidor(idCliente = idCliente)
                        /**
                         * apresentar alerta de sucesso que ao ser clicado no botão
                         * "ok" vai redirecionar o usuário para a tela com as informações
                         * do cliente que acabou de ser cadastrado
                         */

                        clienteId = idCliente

                        alertaConfirmarPadrao.apresentar("Cliente enviado com sucesso para o servidor.")
                    }

                    override fun erro(mensagemErro: String) {
                        esconderLoader()
                        /**
                         * apresentar alerta de erro ao usuário informando para o mesmo
                         * tentar enviar os dados do cliente para o servidor novamente
                         */
                        // deletar o cliente que foi cadastrado na base local do aplicativo
                        clienteDAO.deletarCliente(idCliente = idCliente)

                        alerta.apresentar(mensagemErro)
                    }

                })
            }

        } else {
            /**
             * apresentar alerta para o usuário, informando que o cliente será enviado
             * quando o usuário estiver conectado a internet e redirecionar o mesmo
             * para a tela com a listagem dos dados do cliente quando o mesmo clicar no
             * botão "ok"
             */
            clienteId = idCliente

            this.alertaConfirmarPadrao.apresentar("Os dados do cliente serão enviados ao servidor quando você estiver conectado a internet.")
        }

    }

    private fun salvarCliente() {

        try {

            if (this.validarFormularioCliente()) {
                this.salvarModelPessoa()
                var msgErro: String = ""

                if (this.cliente.tipoPessoa == Constantes.FISICA) {
                    this.salvarModelPessoaFisica()

                    if (!validarEmail(this.cliente.email)) {
                        msgErro = "E-mail inválido!"
                    } else if (!validarCpf(this.cliente.cpf)) {
                        msgErro = "Cpf inválido!"
                    } else if (!validarDataNascimento(this.cliente.dataNascimento)) {
                        msgErro = "Data de nascimento inválida!"
                    } else if (!validarCep(this.cliente.endereco.cep)) {
                        msgErro = "Cep inválido!"
                    } else if (this.clienteDAO.buscarClientePeloCpf(this.cliente.cpf) != null) {
                        msgErro = "Já existe um cliente cadastrado com esse cpf."
                    }

                } else {
                    this.salvarModelPessoaJuridica()
                }

                if (msgErro.isNotEmpty()) {
                    this.alerta.apresentar(msgErro)
                } else {
                    // salvar cliente na base local
                    val idClienteCadastrado = this.clienteDAO.cadastrar(this.cliente)
                    this.enviarClienteServidor(idClienteCadastrado)
                }

            } else {
                this.alerta.apresentar("Preencha todos os campos obrigatórios!")
            }

        } catch (e: Exception) {
            Log.e("erro_cadastrar_cliente", e.message.toString(), e)
        }

    }

    private fun apresentarLoader(mensagem: String) {
        this.loader.iniciarLoader(mensagem)
    }

    private fun esconderLoader() {
        this.loader.finalizarLoader()
    }

    private fun apresentarDadosCliente() {
        this.edtEmail.setText(this.cliente.email)
        this.edtTelefoneFixo.setText(this.cliente.telefoneFixo)
        this.edtTelefoneCelular.setText(this.cliente.telefoneCelular)
        this.edtTelefoneComplementar.setText(this.cliente.telefoneComplementar)

        if (this.cliente.tipoPessoa == Constantes.FISICA) {
            this.apresentarDadosClientePf()
        } else {
            this.apresentarDadosClientePj()
        }

    }

    private fun apresentarDadosClientePf() {
        this.edtNomeCompleto.setText(this.cliente.nome)
        this.edtCpf.setText(this.cliente.cpf.removerMascaras())
        this.edtDataNascimento.setText(this.cliente.dataNascimento)

        val generos: List<String> = listOf("Masculino", "Feminino", "Outro")

        for (contador in generos.indices) {

            if (generos[ contador ].lowercase() == this.cliente.sexo.lowercase()) {
                this.spnGenero.setSelection(contador)
            }

        }

    }

    private fun apresentarDadosClientePj() {

    }

    private fun apresentarEnderecoCliente() {
        this.edtCep.setText(this.cliente.endereco.cep)
        this.edtComplemento.setText(this.cliente.endereco.complemento)
        this.edtEndereco.setText(this.cliente.endereco.endereco)
        this.edtBairro.setText(this.cliente.endereco.bairro)
        this.edtCidade.setText(this.cliente.endereco.cidade)
        this.edtNumero.setText(this.cliente.endereco.numero)

        val estados: List<String> = listOf(
            "SP",
            "RJ",
            "PR"
        )

        var posicaoEstado: Int = 0

        for (contador in estados.indices) {

            if (this.cliente.endereco.uf == estados[ contador ]) {
                posicaoEstado = contador
            }

        }

        this.spnEstado.setSelection(posicaoEstado)
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