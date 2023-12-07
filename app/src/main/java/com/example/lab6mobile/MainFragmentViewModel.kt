package com.example.lab6mobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6mobile.Data.CardsRepository
import com.example.lab6mobile.Data.TermCard

class MainFragmentViewModel : ViewModel() {

    private val _cardsList = MutableLiveData<List<TermCard>>()
    val cardsList: LiveData<List<TermCard>> get() = _cardsList

    init {
        loadCards()
    }
    fun loadCards() {
        _cardsList.value = CardsRepository.getCards()
    }
    fun deleteCard(card: TermCard) {
        CardsRepository.deleteCard(card)
        loadCards()
    }
}