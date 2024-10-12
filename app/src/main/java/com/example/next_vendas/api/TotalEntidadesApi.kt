package com.example.next_vendas.api

import android.util.Log
import com.example.next_vendas.model_servico.TotalModelServico
import com.example.next_vendas.servico.RespostaBase
import com.example.next_vendas.servico.Servico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TotalEntidadesApi {

    fun getTotais(iOnObterTotais: IOnObterTotais) {
        val totalEntidadeServico = Servico().getTotalServico()
        iOnObterTotais.sincronizando()

        totalEntidadeServico.obterTotais()
            .enqueue(object : Callback<RespostaBase<TotalModelServico>> {

                override fun onResponse(call: Call<RespostaBase<TotalModelServico>>, response: Response<RespostaBase<TotalModelServico>>) {

                    if (response.isSuccessful) {
                        Log.d("obter_totais", "Terminou de obter os totais(clientes, categorias de produtos, produtos e vendas)...")
                        val totalModelServicoResp = response.body()!!.corpo

                        iOnObterTotais.terminouObterTotais(
                            totalClientes = totalModelServicoResp.totalClientes,
                            totalCategorias = totalModelServicoResp.totalCategoriasProdutos,
                            totalProdutos = totalModelServicoResp.totalProdutos,
                            totalVendas = totalModelServicoResp.totalVendas
                        )
                    } else {
                        // ocorreu um erro
                        Log.e("erro_obter_totais", "Ocorreu um erro ao tentar-se obter os totais.");
                        iOnObterTotais.erro("Ocorreu um erro ao tentar-se obter os totais.")
                    }

                }

                override fun onFailure(call: Call<RespostaBase<TotalModelServico>>, t: Throwable) {
                    Log.e("erro_obter_totais", "Erro: " + t.message.toString())
                    iOnObterTotais.erro("Ocorreu um erro ao tentar-se obter os totais: ${ t.message.toString() }")
                }

            })
    }

}