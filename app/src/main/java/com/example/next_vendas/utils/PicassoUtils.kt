package com.example.next_vendas.utils

import android.widget.ImageView
import com.example.next_vendas.R
import com.squareup.picasso.Picasso

class PicassoUtils {

    companion object {

        fun apresentarImagem(imagem: String, imageView: ImageView) {

            if (imagem.isBlank()) {

                throw Exception("Informe a url da imagem que será apresentada!")
            }

            val picasso = Picasso.get()

            picasso.load(imagem)
                .placeholder(R.drawable.ic_carregando_imagem)
                .error(R.drawable.ic_erro_carregar_imagem)
                .into(imageView)
        }

    }

}