package com.example.motivation.repository

import android.content.Context

class SecurityPreferences(context: Context) {

    private val shared = context.getSharedPreferences("Motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, str: String) {
        shared.edit().putString(key, str).apply()
    }

    fun getString(key: String): String {
        return shared.getString(key, "") ?: ""
    }
}