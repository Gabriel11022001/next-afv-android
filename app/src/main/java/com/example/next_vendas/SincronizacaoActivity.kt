package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class SincronizacaoActivity : AppCompatActivity(), OnClickListener {

    private lateinit var txtTitulo: TextView
    private lateinit var btnAdicionar: ImageButton
    private lateinit var btnFiltro: ImageButton
    private lateinit var btnSincronizar: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sincronizacao)

        // mapear elementos de interface
        this.btnAdicionar = findViewById(R.id.btn_adicionar_menu)
        this.btnFiltro = findViewById(R.id.btn_filtro_menu)
        this.txtTitulo = findViewById(R.id.txt_titulo)
        this.btnSincronizar = findViewById(R.id.btn_sincronizar)

        // mapear eventos
        this.btnSincronizar.setOnClickListener(this)

        this.txtTitulo.text = "Sincronização"
        this.btnAdicionar.visibility = View.GONE
        this.btnFiltro.visibility = View.GONE
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_sincronizar) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

    }

}