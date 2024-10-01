package com.example.next_vendas.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.next_vendas.model_servico.ConfiguracaoModelServico
import com.example.next_vendas.model_servico.UsuarioModelServico
import com.google.gson.JsonObject

/**
 * função que valida se o usuário está ou não conectado a internet,
 * retorna true caso o mesmo esteja conectado ou false caso não esteja
 * @param contexto
 * @return Boolean
 */
fun validarEstaConectadoInternet(contexto: Context): Boolean {

    return true
}

fun validarEmail(email: String): Boolean {

    return true
}

fun validarCpf(cpf: String): Boolean {

    return true
}

fun validarDataNascimento(dataNascimento: String): Boolean {

    return true
}

fun validarCep(cep: String): Boolean {

    return true
}

fun validarCnpj(cnpj: String): Boolean {

    return true
}

fun salvarDadosUsuarioLogadoPreferenciasCompartilhadas(
    usuarioModelServico: UsuarioModelServico,
    sharedPreferencesUsuarioLogado: SharedPreferences
) {
    val nomeUsuarioLogado: String = usuarioModelServico.nomeUsuarioLogado
    val emailUsuarioLogado: String = usuarioModelServico.email
    val telefoneCelularUsuarioLogado: String = usuarioModelServico.telefoneCelular
    val nivelAcesso: String = usuarioModelServico.nivelAcessoUsuarioLogado
    val ativo: Boolean = usuarioModelServico.ativo
    val ambiente: String = usuarioModelServico.ambiente
    val idUsuarioLogado: Int = usuarioModelServico.idUsuarioLogado

    val editor = sharedPreferencesUsuarioLogado.edit()
    editor.putInt("id_usuario_logado", idUsuarioLogado)
    editor.putString("nome_completo_usuario_logado", nomeUsuarioLogado)
    editor.putString("email_usuario_logado", emailUsuarioLogado)
    editor.putString("telefone_celular_usuario_logado", telefoneCelularUsuarioLogado)
    editor.putBoolean("usuario_logado_ativo", ativo)
    editor.putString("nivel_acesso_usuario_logado", nivelAcesso)
    editor.putString("ambiente", ambiente)

    editor.apply()

    println("Salvou os dados do usuário logado nas preferências compartilhadas.")
}

fun salvarConfiguracoesPreferenciasCompartilhadas(
    configuracaoModelServico: ConfiguracaoModelServico,
    sharedPreferencesConfiguracoes: SharedPreferences
) {
    val editor = sharedPreferencesConfiguracoes.edit()

    val ambiente: String = configuracaoModelServico.ambiente

    editor.putString("ambiente_servidor", ambiente)

    val camposObrigatorios: JsonObject? = configuracaoModelServico.camposObrigatorios

    if (camposObrigatorios != null) {
        val camposObrigatoriosEndereco = camposObrigatorios.getAsJsonObject("endereco")
        val camposObrigatoriosPessoaFisica = camposObrigatorios.getAsJsonObject("pessoa_fisica")
        val camposObrigatoriosPessoaJuridica = camposObrigatorios.getAsJsonObject("pessoa_juridica")

        // setar campos obrigatórios do endereço nas preferências compartilhadas
        val cepObrigatorio: Boolean = camposObrigatoriosEndereco.get("cep").asBoolean
        val enderecoObrigatorio: Boolean = camposObrigatoriosEndereco.get("endereco").asBoolean
        val bairroObrigatorio: Boolean = camposObrigatoriosEndereco.get("bairro").asBoolean
        val cidadeObrigatorio: Boolean = camposObrigatoriosEndereco.get("cidade").asBoolean
        val complementoObrigatorio: Boolean = camposObrigatoriosEndereco.get("complemento").asBoolean
        val numeroObrigatorio: Boolean = camposObrigatoriosEndereco.get("numero").asBoolean
        val estadoObrigatorio: Boolean = camposObrigatoriosEndereco.get("estado").asBoolean

        editor.putBoolean("cep_obrigatorio", cepObrigatorio)
        editor.putBoolean("endereco_obrigatorio", enderecoObrigatorio)
        editor.putBoolean("bairro_obrigatorio", bairroObrigatorio)
        editor.putBoolean("cidade_obrigatorio", cidadeObrigatorio)
        editor.putBoolean("complemento_obrigatorio", complementoObrigatorio)
        editor.putBoolean("numero_obrigatorio", numeroObrigatorio)
        editor.putBoolean("estado_obrigatorio", estadoObrigatorio)

        // setar campos obrigatórios da pessoa física nas preferências compartilhadas
    }

}