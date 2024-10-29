package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.utils.validarEmail
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var btnRetornar: ImageButton
    private lateinit var btnFinalizarCadastro: AppCompatButton
    private lateinit var edtNomeUsuario: TextInputEditText
    private lateinit var edtEmailUsuario: TextInputEditText
    private lateinit var edtSenhaUsuario: TextInputEditText
    private lateinit var edtSenhaConfirmar: TextInputEditText
    private lateinit var edtChaveAcesso: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        // mapear elementos de interface
        this.btnRetornar = findViewById(R.id.btn_retornar)
        this.btnFinalizarCadastro = findViewById(R.id.btn_finalizar_cadastro)
        this.edtNomeUsuario = findViewById(R.id.edt_nome_usuario)
        this.edtEmailUsuario = findViewById(R.id.edt_email_usuario)
        this.edtSenhaUsuario = findViewById(R.id.edt_senha_usuario)
        this.edtSenhaConfirmar = findViewById(R.id.edt_senha_usuario_confirmar)
        this.edtChaveAcesso = findViewById(R.id.edt_chave_acesso)

        // mapear eventos
        this.btnRetornar.setOnClickListener { this.retornar() }
        this.btnFinalizarCadastro.setOnClickListener { this.cadastrarse() }
    }

    private fun retornar() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun apresentarAlerta(mensagem: String) {
        val snackbarAlertaErro = Snackbar.make(findViewById(android.R.id.content), mensagem, Snackbar.LENGTH_LONG)
        // definir a cor de fundo vermelha para a snackbar
        snackbarAlertaErro.view.setBackgroundColor(getColor(android.R.color.holo_red_dark))

        snackbarAlertaErro.setAction("X") {
            snackbarAlertaErro.dismiss()
        }

        snackbarAlertaErro.show()
    }

    private fun validarFormulario(): Boolean {
        val nomeUsuario: String = this.edtNomeUsuario.text.toString().trim()
        val emailUsuario: String = this.edtEmailUsuario.text.toString().trim()
        val senhaUsuario: String = this.edtSenhaUsuario.text.toString().trim()
        val senhaConfirmar: String = this.edtSenhaConfirmar.text.toString().trim()
        val chaveAcesso: String = this.edtChaveAcesso.text.toString().trim()

        if (nomeUsuario.isBlank()) {
            this.apresentarAlerta("Informe seu nome.")

            return false
        }

        if (nomeUsuario.length < 3) {
            this.apresentarAlerta("O nome deve ter no mínimo três caracteres.")

            return false
        }

        if (emailUsuario.isBlank()) {
            this.apresentarAlerta("Informe seu e-mail.")

            return false
        }

        if (!validarEmail(emailUsuario)) {
            this.apresentarAlerta("E-mail inválido.")

            return false
        }

        if (senhaUsuario.isBlank()) {
            this.apresentarAlerta("Informe sua senha.")

            return false
        }

        if (senhaConfirmar.isBlank()) {
            this.apresentarAlerta("Informe a senha de confirmação.")

            return false
        }

        if (senhaUsuario.length < 5) {
            this.apresentarAlerta("A senha deve conter no mínimo cinco caracteres.")

            return false
        }

        if (senhaConfirmar.length < 5) {
            this.apresentarAlerta("A senha de confirmação deve conter no mínimo cinco caracteres.")

            return false
        }

        if (senhaUsuario != senhaConfirmar) {
            this.apresentarAlerta("A senha e a senha de confirmação estão diferentes.")

            return false
        }

        if (chaveAcesso.isBlank()) {
            this.apresentarAlerta("Informe a chave de acesso.")

            return false
        }

        return true
    }

    private fun cadastrarse() {

        try {

            if (this.validarFormulario()) {

            }

        } catch (e: Exception) {

        }

    }

}