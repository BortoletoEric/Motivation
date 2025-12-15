package com.example.motivation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motivation.helper.MotivationConstants
import com.example.motivation.repository.PhraseRepository
import com.example.motivation.R
import com.example.motivation.repository.SecurityPreferences
import com.example.motivation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var phraseRepository: PhraseRepository
    private var filter: Int = 0
    private val listId = listOf(R.id.imageViewAll, R.id.imageViewHappy, R.id.imageViewSunny)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
        getUserName()
        refreshPhrase()
    }

    private fun refreshPhrase() {
        binding.textViewPhrase.text = phraseRepository.getPhrase(filter)
    }

    private fun getUserName() {
        val name = SecurityPreferences(applicationContext).getString(MotivationConstants.KEY.PERSON_NAME)
        binding.textViewHello.text = "Olá, $name!"
    }

    private fun handleNewPhrase() {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnNewPhrase) {
            handleNewPhrase()
        } else if (v.id in listId) {
            handleFilter(v.id)
        }
    }

    private fun handleFilter(id: Int) {
        when (id) {
            R.id.imageViewAll -> {
                filter = MotivationConstants.PHRASE.ALL
                binding.imageViewSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
            R.id.imageViewHappy -> {
                filter = MotivationConstants.PHRASE.HAPPY
                binding.imageViewHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
            R.id.imageViewSunny -> {
                filter = MotivationConstants.PHRASE.SUNNY
                binding.imageViewSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
        }
    }

    private fun setListeners() {
        binding.btnNewPhrase.setOnClickListener {this}
        binding.imageViewAll.setOnClickListener {this}
        binding.imageViewHappy.setOnClickListener {this}
        binding.imageViewSunny.setOnClickListener {this}
        phraseRepository = PhraseRepository()
    }
}