package com.example.myapplication

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditCardViewModel : ViewModel() {
    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image

    fun editCard(
        cardId: Int,
        question: String,
        example: String,
        answer: String,
        translation: String,
        image: Bitmap?
    ) {
        val newCard = Model.updateCard(
            Model.getCardById(cardId),
            question,
            example,
            answer,
            translation,
            image
        )
        Model.updateList(cardId, newCard)
    }

    fun setCardToEdit(cardId: Int) {
        _card.value = Model.getCardById(cardId)
    }
    fun setImageToCard(image: Bitmap?){
        _image.value=image
    }
}