package com.example.gifimagegallery.ui.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.gifimagegallery.R
import com.example.gifimagegallery.databinding.ActivitySearchGifsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGIFsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchGifsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_gifs)
        binding.lifecycleOwner = this

    }
}