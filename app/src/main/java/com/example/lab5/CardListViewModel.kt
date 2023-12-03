package com.example.lab5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class CardListViewModel : ViewModel() {
    private val Mlist_cards = MutableLiveData<List<Card>>() //А тут уже можно менять
    val list_cards: LiveData<List<Card>> = Mlist_cards //Только чтение
    private val Mcard = MutableLiveData<Card>()
    val card: LiveData<Card> = Mcard

    init { //Инициализация
        Mlist_cards.value = Model.cards
    }

    fun setCard(cardId: Int) {
        Mcard.value = Model.getCardById(cardId)
    }

    fun removeCard(cardId: Int) {
        Model.removeCard(cardId)
        Mlist_cards.value = Model.cards
    }

    fun getCard(): String {
        return "\n Название: ${card.value!!.answer}"
    }
}