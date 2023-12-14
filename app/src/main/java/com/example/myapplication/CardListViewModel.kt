package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.nio.file.Files.delete
import kotlin.concurrent.thread

class CardListViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards
    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    init {
        _cards.value = Cards.cards
    }

    fun deleteCard(cardId: Int) {
        Cards.removeCard(cardId)
        _cards.value = Cards.cards
    }
}