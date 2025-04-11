package com.example.next_vendas

import com.example.next_vendas.utils.validarCpf
import org.junit.Assert
import org.junit.Test

class ValidaCpfTest {

    // validar caso em que o cpf é válido
    @Test
    fun validarCasoCpfValido() {
        val cpf = "111.444.777-35"

        Assert.assertTrue(validarCpf(cpf))
    }

    // validar caso em que o cpf possui somente numeros mas é valido
    @Test
    fun validarCpfValidoComSomenteNumeros() {
        val cpf = "11144477735"

        Assert.assertTrue(validarCpf(cpf))
    }

    // validar o caso em que o cpf é inválido
    @Test
    fun validarCpfInvalido() {
        val cpf = "123.456.789-00"

        Assert.assertFalse(validarCpf(cpf))
    }

    // validar caso em que o cpf é inválido e possui somente numeros
    @Test
    fun validarCpfInvalidoComSomenteNumeros() {
        val cpf = "12345678900"

        Assert.assertFalse(validarCpf(cpf))
    }

    // validar caso em que o cpf possui menos de 11 caracteres
    @Test
    fun validarCasoEmQueCpfPossuiMenosDeOnzeCaracteres() {
        val cpf = "123.45.789-00"

        Assert.assertFalse(validarCpf(cpf))
    }

}