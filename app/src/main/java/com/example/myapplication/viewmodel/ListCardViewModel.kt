package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Card
import com.example.myapplication.model.Model

class ListCardViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards
    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    init {
        _cards.value = Model.cards
    }

    fun setCardOfFragment(cardId: Int) {
        _card.value = Model.getCardById(cardId)
    }

    fun removeCardById(cardId: Int) {
        Model.removeCard(cardId)
        _cards.value = Model.cards
    }

    fun getCardShortData(): String {
        return "\n ${card.value!!.answer} / ${card.value!!.translation}"
    }
}