package com.example.myapplication

import android.graphics.Bitmap

data class Card(
    val id: Int,
    val question: String,
    val example: String,
    val answer: String,
    val translate: String,
    val image: Bitmap? = null,
)

