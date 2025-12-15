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
    private var filter: Int = MotivationConstants.PHRASE.ALL


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
        handleFilter(R.id.image_all)
        refreshPhrase()
    }

    private fun refreshPhrase() {
        binding.textViewPhrase.text = phraseRepository.getPhrase(filter)
    }

    private fun getUserName() {
        val name = SecurityPreferences(applicationContext).getString(MotivationConstants.KEY.PERSON_NAME)
        binding.textViewHello.text = "Olá, $name!"
    }

    override fun onClick(view: View) {
        val id: Int = view.id

        val listId = listOf(
            R.id.image_all,
            R.id.image_happy,
            R.id.image_sunny
        )
        if (id in listId) {
            handleFilter(id)
        } else if (id == R.id.button_new_phrase) {
            refreshPhrase()
        }
    }

    private fun handleFilter(id: Int) {

        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.black))

        when (id) {
            R.id.image_all -> {
                filter = MotivationConstants.PHRASE.ALL
                binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
            R.id.image_happy -> {
                filter = MotivationConstants.PHRASE.HAPPY
                binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
            R.id.image_sunny -> {
                filter = MotivationConstants.PHRASE.SUNNY
                binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
        }
    }

    private fun setListeners() {
        binding.buttonNewPhrase.setOnClickListener (this)
        binding.imageAll.setOnClickListener (this)
        binding.imageHappy.setOnClickListener (this)
        binding.imageSunny.setOnClickListener (this)
        phraseRepository = PhraseRepository()
    }
}