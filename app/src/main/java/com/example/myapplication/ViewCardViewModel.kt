package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewCardViewModel : ViewModel() {
    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    fun setCardOfFragment(cardId: Int) {
        _card.value = Cards.getCardById(cardId)
    }
}