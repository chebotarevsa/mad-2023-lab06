package com.example.lab6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.databinding.ActivityCardListBinding

class CardListActivity : AppCompatActivity() {
    lateinit var binding: ActivityCardListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
