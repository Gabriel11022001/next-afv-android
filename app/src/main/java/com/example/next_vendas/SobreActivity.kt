package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SobreActivity : AppCompatActivity() {

    private lateinit var txtSobre: TextView
    private lateinit var btnFecharSobre: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        this.txtSobre = findViewById(R.id.txt_sobre)
        this.btnFecharSobre = findViewById(R.id.btn_fechar_sobre)

        // definir o texto

        this.btnFecharSobre.setOnClickListener {
            fecharTelaSobre()
        }
    }

    private fun fecharTelaSobre() {
        val intentHome = Intent(this, HomeActivity::class.java)
        startActivity(intentHome)
        finish()
    }

}