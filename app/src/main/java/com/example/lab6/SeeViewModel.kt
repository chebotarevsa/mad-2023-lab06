package com.example.lab6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeeViewModel : ViewModel() {

    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    fun setCard(cardId: Int) {
        _card.value = CardService.getCardById(cardId)
    }
}