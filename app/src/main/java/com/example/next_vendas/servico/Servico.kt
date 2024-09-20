package com.example.next_vendas.servico

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Servico {

    private val URL_BASE: String = "http://192.168.0.103:8080/api/"

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(this.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getClienteService(): ClienteServico {

        return this.getRetrofit().create(ClienteServico::class.java)
    }

    fun getLoginService(): LoginServico {

        return this.getRetrofit().create(LoginServico::class.java)
    }

}