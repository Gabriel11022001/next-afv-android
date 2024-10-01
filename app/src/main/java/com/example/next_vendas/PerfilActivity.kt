package com.example.next_vendas

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.next_vendas.utils.Alerta

class PerfilActivity : AppCompatActivity(), OnClickListener {

    private lateinit var alerta: Alerta
    private lateinit var txtTituloMenu: TextView
    private lateinit var btnRetornar: ImageButton
    private lateinit var btnFiltrar: ImageButton
    private lateinit var btnAdicionar: ImageButton
    private lateinit var txtNomeCompletoUsuarioLogado: TextView
    private lateinit var txtEmailClienteLogado: TextView
    private lateinit var txtTelefoneCelularUsuarioLogado: TextView
    private lateinit var txtNivelAcessoUsuarioLogado: TextView
    private lateinit var btnEditarPerfil: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // mapear elementos de interface
        this.btnFiltrar = findViewById(R.id.btn_filtro_menu)
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnRetornar = findViewById(R.id.btn_retornar)
        this.txtTituloMenu = findViewById(R.id.txt_titulo)
        this.btnEditarPerfil = findViewById(R.id.btn_alterar_perfil_usuario)
        this.txtNomeCompletoUsuarioLogado = findViewById(R.id.txt_nome_completo_usuario_logado)
        this.txtEmailClienteLogado = findViewById(R.id.txt_email_usuario_logado)
        this.txtTelefoneCelularUsuarioLogado = findViewById(R.id.txt_telefone_celular_usuario_logado)
        this.txtNivelAcessoUsuarioLogado = findViewById(R.id.txt_nivel_acesso_usuario_logado)

        // mapear elementos de interface
        this.btnRetornar.setOnClickListener(this)
        this.btnEditarPerfil.setOnClickListener(this)

        this.btnAdicionar.visibility = View.GONE
        this.btnFiltrar.visibility = View.GONE

        this.txtTituloMenu.text = "Perfil"
    }

    private fun retornar() {

    }

    private fun alterarPerfil() {

    }

    override fun onBackPressed() {
        this.retornar()
    }

    override fun onClick(p0: View?) {

        if (p0?.id == R.id.btn_retornar) {
            this.retornar()
        } else if (p0?.id == R.id.btn_alterar_perfil_usuario) {
            this.alterarPerfil()
        }

    }

}