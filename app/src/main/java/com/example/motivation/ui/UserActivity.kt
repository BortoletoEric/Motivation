package com.example.motivation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motivation.R
import com.example.motivation.SecurityPreferences
import com.example.motivation.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
    }

    private fun setListeners() {
        binding.btnSave.setOnClickListener {
            handleSave()
        }
    }

    private fun handleSave() {
        try {
            val name = binding.editTextName.text.toString()
            if (name.isNotBlank() || name == R.string.whats_your_name.toString()) {
                SecurityPreferences(applicationContext).storeString("USER_NAME", name)
                Toast.makeText(applicationContext, "Olá $name!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(applicationContext, "Informe seu nome!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("UserActivityError", "Erro ao salvar: ${e.message}")
            Toast.makeText(applicationContext, "Ocorreu um erro ao salvar.", Toast.LENGTH_SHORT)
                .show()
        }
    }
}