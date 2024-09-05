package com.example.next_vendas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var btnRealizarLogin: AppCompatButton
    private lateinit var btnRegistrarse: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // mapear elementos de interface
        this.btnRealizarLogin = findViewById(R.id.btn_realizar_login)
        this.btnRegistrarse = findViewById(R.id.btn_registrarse)

        this.btnRealizarLogin.setOnClickListener(this)
        this.btnRegistrarse.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_realizar_login) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {

        }

    }

}