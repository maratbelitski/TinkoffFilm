package com.tinkofffilm.presentation.detaildisplay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tinkofffilm.R
import com.tinkofffilm.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var idKinopoisk = DEFAULT_VALUE

    companion object {
        const val ID_KINOPOISK = "id_kinopoisk"
        const val ID_DEFAULT = -1
        private const val DEFAULT_VALUE = 0

        fun launchIntent(context: Context, idKinopoisk: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ID_KINOPOISK, idKinopoisk)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idKinopoisk = parseIntent()

        val fragment = MovieDetailFragment.newInstance(idKinopoisk)
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragmentDetail, fragment)
            .commit()
    }

    private fun parseIntent(): Int {
        return intent.getIntExtra(ID_KINOPOISK, ID_DEFAULT)
    }
}