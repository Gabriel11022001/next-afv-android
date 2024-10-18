package com.example.next_vendas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView

class VendaActivity : AppCompatActivity() {

    private lateinit var fragmentContainerViewVenda: FragmentContainerView

    private var idVenda: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venda)

        // mapear elementos de interface
        this.fragmentContainerViewVenda = findViewById(R.id.fragment_container_view_realizar_venda)

        // registrar a venda quando o usuário acessa a activity pela primeira vez
        this.registrarVendaInicio()

        // configurar qual vai ser o primeiro fragment a aparecer
        this.setFragmentInicial(savedInstanceState)
    }

    private fun registrarVendaInicio() {

    }

    private fun setFragmentInicial(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            val fragmentInicialVenda = VendaCarrinhoFragment()

            val bundle = Bundle()
            bundle.putInt("id_venda", this.idVenda)

            fragmentInicialVenda.arguments = bundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view_realizar_venda, fragmentInicialVenda)
                .commit()
        } else {
            Log.e("carrinho_fragment_add", "O fragmento inicial já foi adicionado.")
        }

    }

}