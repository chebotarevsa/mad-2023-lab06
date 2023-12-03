package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards
    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    fun setCardList() {
        _cards.value = Model.cards
    }

    fun setCardToDelete(cardId: Int) {
        _card.value = Model.getCardById(cardId)
    }

    fun deleteCardById(cardId: Int) {
        Model.removeCard(cardId)
        _cards.value = Model.cards
    }
}