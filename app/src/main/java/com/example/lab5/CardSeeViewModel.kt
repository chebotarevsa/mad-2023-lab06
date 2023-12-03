package com.example.lab5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class CardSeeViewModel : ViewModel(){
    private var Mlist_cards = MutableLiveData<Card>()
    val list_cards: LiveData<Card> = Mlist_cards

    fun setCardOfFragment(cardId: Int) {
        Mlist_cards.value = Model.getCardById(cardId)
    }
}