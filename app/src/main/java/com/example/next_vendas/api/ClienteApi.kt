package com.example.next_vendas.api

import android.hardware.lights.Light
import android.util.Log
import com.example.next_vendas.model.Pessoa
import com.example.next_vendas.servico.ClienteServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ClienteApi {

    companion object {

        private val TOTAL_CLIENTES_POR_PAGINA: Int = 5

        fun cadastrarCliente(cliente: Pessoa, iOnEnviarServidor: IOnEnviarServidor) {

        }

        fun sincronizarClientes(totalClientes: Int, iOnSincronizar: IOnSincronizar) {

        }

    }

}