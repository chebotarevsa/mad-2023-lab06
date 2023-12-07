package com.example.lab6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.databinding.ActivityArraylistBinding

class ArrayListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArraylistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArraylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}