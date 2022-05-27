package com.example.gifimagegallery.ui.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.gifimagegallery.R
import com.example.gifimagegallery.databinding.ActivitySearchGifsBinding
import com.example.gifimagegallery.ui.main.viewModels.SearchGIFsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGIFsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchGifsBinding
    private val viewModel by lazy{ViewModelProvider(this).get(SearchGIFsViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_gifs)
        binding.lifecycleOwner = this

    }
}