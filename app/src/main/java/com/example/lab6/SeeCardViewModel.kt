package com.example.lab6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeeCardViewModel : ViewModel() {
    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    fun setCardOfFragment(cardId: Int) {
        _card.value = Model.getCardById(cardId)
    }
}
