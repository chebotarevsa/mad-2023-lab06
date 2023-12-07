package com.example.lab6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArrayListViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards
    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    init {
        _cards.value = CardService.cards
    }

    fun setCard(cardId: Int) {
        _card.value = CardService.getCardById(cardId)
    }

    fun removeCardById(cardId: Int) {
        CardService.removeCard(cardId)
        _cards.value = CardService.cards
    }
}