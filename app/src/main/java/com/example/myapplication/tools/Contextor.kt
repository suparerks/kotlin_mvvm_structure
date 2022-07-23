package com.example.myapplication.tools

import android.content.Context

class Contextor {
    var context: Context? = null

    fun init(context: Context) {
        this.context = context
    }

    companion object {
        private var contextor: Contextor? = null

        fun getInstance (): Contextor {
            if (contextor == null) {
                contextor = Contextor()
            }
            return contextor as Contextor
        }
    }
}