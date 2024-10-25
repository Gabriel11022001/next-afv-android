package com.example.next_vendas.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import com.example.next_vendas.model_servico.ConfiguracaoModelServico
import com.example.next_vendas.model_servico.UsuarioModelServico
import com.google.gson.JsonObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

const val QTD_DIAS_SINC: Int = 3

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

    editor.commit()

    Log.d("resultado_login", "Os dados do usuário logado foram salvos com sucesso nas preferências compartilhadas.")
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

/**
 * setar a data da ultima sincronização do usuário, para poder validar
 * depois se já se passaram 3 dias desde a ultima sinc para forçar o usuário a sincronizar
 */
fun setarPreferenciasCompartilhadasUltimaSincronizacao(
    dataUltimaSincronizacao: Date,
    sharedPreferencesSincronizacao: SharedPreferences
) {
    val editorSharedPreferencesSincronizacao: Editor = sharedPreferencesSincronizacao.edit()

    // obter data da ultima sinc em formato de texto Y-m-d H:i:s
    val formatoData: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val dataUltimaSincFormatada: String = formatoData.format(dataUltimaSincronizacao)

    editorSharedPreferencesSincronizacao.putString("data_ultima_sinc", dataUltimaSincFormatada)

    Log.d("data_ultima_sinc", dataUltimaSincFormatada)

    editorSharedPreferencesSincronizacao.commit()
}

/**
 * função para validar se vai ser necessário o usuário sincronizar o app novamente
 */
fun validarPrecisaSincronizar(
    sharedPreferencesSincronizacao: SharedPreferences
): Boolean {
    // obter data da ultima sinc
    val dataUltimaSincTexto: String = sharedPreferencesSincronizacao.getString("data_ultima_sinc", "").toString()

    if (dataUltimaSincTexto.isEmpty()) {
        // é a primeira vez que está logando, precisa fazer a sinc

        return true
    }

    val dataUltimaSinc: Date? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataUltimaSincTexto)
    val dataAtual: Date = Date()

    if (dataUltimaSinc != null) {
        val diferencaDiasTime = dataAtual.time - dataUltimaSinc.time
        val diferencaEmDias = TimeUnit.MILLISECONDS.toDays(diferencaDiasTime)

        return diferencaEmDias >= QTD_DIAS_SINC
    } else {
        throw Exception("Ocorreu um erro ao tentar-se fazer o parse da data da ultima sincronização para um objeto do tipo Date.")
    }

}