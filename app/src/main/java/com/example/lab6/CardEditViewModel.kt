package com.example.lab6

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Основной ViewModel
class CardEditViewModel : ViewModel() {
    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image

    fun setCardById(cardId: Int) {
        if (cardId != -1) {
            _card.value = Model.getCardById(cardId)
        }
    }

    fun updateCard(
        cardId: Int,
        question: String,
        example: String,
        answer: String,
        translation: String,
    ) {
        with(Model) {
            updateCard(
                card.value!!,
                question,
                example,
                answer,
                translation,
                this@CardEditViewModel.image.value
            ).also { _card.value = it }
            updateCardList(cardId, card.value!!)
        }
    }

    fun setImage(image: Bitmap?) {
        _image.value = image
    }
}