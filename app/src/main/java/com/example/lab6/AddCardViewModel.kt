package com.example.lab6

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel

class AddCardViewModel : ViewModel() {
    fun addCard(
        question: String,
        example: String,
        answer: String,
        translation: String,
        image: Bitmap?
    ) {
        val card = Model.createNewCard(question, example, answer, translation, image)
        Model.addCard(card)
    }
}