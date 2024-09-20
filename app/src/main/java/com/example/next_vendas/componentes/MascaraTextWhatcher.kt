package com.example.next_vendas.componentes

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MascaraTextWhatcher(
    private val campo: EditText,
    private val mascaraFormatar: String
): TextWatcher {

    private var isUpdating = false
    private var old = ""

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        val str = unMasck(p0!!.toString())
        var mascara = ""

        if (isUpdating) {
            old = str
            isUpdating = false

            return
        }

        var i = 0

        for (m in this.mascaraFormatar) {

            if (m != '#' && str.length > old.length) {
                mascara += m

                continue
            }

            try {
                mascara += str[i]
            } catch (e: Exception) {

                break
            }

            i++
        }

        isUpdating = true
        this.campo.setText(mascara)
        this.campo.setSelection(mascara.length)
    }

    override fun afterTextChanged(p0: Editable?) { }

    private fun unMasck(textoCampo: String): String {

        return textoCampo.replace(".", "").replace("-", "").replace("/", "")
            .replace("(", "").replace(" ", "").replace(":", "").replace(")", "")
    }

}