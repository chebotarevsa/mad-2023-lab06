package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Card
import com.example.myapplication.model.Model

class SeeCardViewModel : ViewModel() {
    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    fun setCardOfFragment(cardId: Int) {
        _card.value = Model.getCardById(cardId)
    }
}
