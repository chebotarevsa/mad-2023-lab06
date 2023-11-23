package com.example.lab6

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditCardViewModel : ViewModel() {
    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    fun setCardOfFragment(cardId: Int) {
        _card.value = Model.getCardById(cardId)
    }

    fun updateCardById(
        cardId: Int,
        question: String,
        example: String,
        answer: String,
        translation: String,
        image: Bitmap?
    ) {
        _card.value = Model.updateCard(card.value!!, question, example, answer, translation, image)
        Model.updateCardList(cardId, card.value!!)
    }
}