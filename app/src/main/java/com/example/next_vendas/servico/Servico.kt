package com.example.next_vendas.servico

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Servico {

    private val URL_BASE: String = "http://192.168.1.4:8080/api/"
    private val URL_VIA_CEP: String = "https://viacep.com.br/ws/"

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(this.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // obter a instância Retrofit para consumir a api do ViaCEP
    private fun getRetrofitViaCep(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(this.URL_VIA_CEP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getClienteService(): ClienteServico {

        return this.getRetrofit().create(ClienteServico::class.java)
    }

    fun getLoginService(): LoginServico {

        return this.getRetrofit().create(LoginServico::class.java)
    }

    fun getConfiguracaoService(): ConfiguracaoServico {

        return this.getRetrofit().create(ConfiguracaoServico::class.java)
    }

    fun getTotalServico(): TotalEntidadeServico {

        return this.getRetrofit().create(TotalEntidadeServico::class.java)
    }

    fun getCategoriaProdutoServico(): CategoriaServico {

        return this.getRetrofit().create(CategoriaServico::class.java)
    }

    fun getProdutoServico(): ProdutoServico {

        return this.getRetrofit().create(ProdutoServico::class.java)
    }

    fun getViaCepServico(): ViaCEPServico {

        return this.getRetrofitViaCep().create(ViaCEPServico::class.java)
    }

    fun getUsuarioServico(): UsuarioServico {

        return this.getRetrofit().create(UsuarioServico::class.java)
    }

    fun getNotificacaoServico(): NotificacaoServico {

        return this.getRetrofit().create(NotificacaoServico::class.java)
    }

}