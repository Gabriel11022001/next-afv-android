package com.example.next_vendas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.next_vendas.listener.IOnConfirmarListener
import com.example.next_vendas.utils.AlertaConfirmarVoltar
import java.lang.Exception

class HomeActivity : AppCompatActivity(), OnClickListener {

    private lateinit var alertaRetornar: AlertaConfirmarVoltar
    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var btnVoltar: ImageButton
    private lateinit var cardGestaoClientes: ConstraintLayout
    private lateinit var cardGestaoProdutos: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // mapear elementos de interface
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnVoltar = findViewById(R.id.btn_retornar)
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.cardGestaoClientes = findViewById(R.id.constraint_card_gestao_clientes)
        this.cardGestaoProdutos = findViewById(R.id.constraint_card_gestao_produtos)

        // mapear eventos
        this.btnVoltar.setOnClickListener(this)
        this.cardGestaoClientes.setOnClickListener(this)
        this.cardGestaoProdutos.setOnClickListener(this)

        this.btnFiltro.visibility = View.GONE
        this.btnAdicionar.visibility = View.GONE

        this.txtTitulo.text = "Home"

        // configurar listener de confirmar para sair do app
        val confirmarListenerSairApp = object : IOnConfirmarListener {

            override fun confirmar() {
                finish()
            }

        }

        this.alertaRetornar = AlertaConfirmarVoltar(this, "Deseja sair do aplicativo?", confirmarListenerSairApp)
    }

    private fun voltar() {

        try {
            this.alertaRetornar.apresentar()
        } catch (e: Exception) {
            Log.e("erro_sair", e.message.toString())
        }

    }

    override fun onBackPressed() {
        this.voltar()
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_retornar) {
            this.voltar()
        }

    }

}