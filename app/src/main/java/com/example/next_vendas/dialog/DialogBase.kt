package com.example.next_vendas.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

open class DialogBase(
    contexto: Context,
    protected var builderDialog: AlertDialog.Builder? = null,
    protected var dialog: AlertDialog? = null,
    protected var layoutInflater: LayoutInflater = LayoutInflater.from(contexto)
) { }