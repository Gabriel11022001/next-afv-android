package com.example.next_vendas.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import com.example.next_vendas.LoginActivity
import com.example.next_vendas.R

class MenuOpcoesCabecalho(
    private val contexto: Context,
    private val onLogout: () -> Unit,
    private val onRedirecionarSobre: () -> Unit
) {

    private lateinit var popupMenu: PopupMenu

    fun apresentarMenu(view: View) {
        this.popupMenu = PopupMenu(this.contexto, view)
        this.popupMenu.menuInflater.inflate(R.menu.menu_app_cabecalho, this.popupMenu.menu)

        this.popupMenu.setOnMenuItemClickListener { menuItem ->

            if (menuItem.itemId == R.id.menu_item_sobre) {
                // implementar lógica de o usuário ir para a tela "sobre"
                redirecionarSobre()

                true
            }

            if (menuItem.itemId == R.id.menu_item_logout) {
                // implementar lógica de o usuário fazer o logout
                logout()

                true
            }

            false
        }

        this.popupMenu.show()
    }

    private fun redirecionarSobre() {
        this.onRedirecionarSobre()
    }

    private fun logout() {
        this.onLogout()
    }

}