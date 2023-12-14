package com.example.myapplication

import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch


class EditCardViewModel(val cardId: Int) : ViewModel() {

    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card


    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image

    init {
        if (checkIfNewCard()){
            _card.value = Cards.createNewCard("","","","", null)
        }else{
            _card.value = Cards.getCardById(cardId)
        }
    }

    fun checkIfNewCard() = cardId == -1

    fun saveCard(
        question: String, example: String, answer: String, translation: String
    ) {
        _card.value = Cards.createNewCard(question, example, answer, translation, image.value)
            viewModelScope.launch {
                if (checkIfNewCard()) {
                    Cards.addCard(card.value!!)
                } else {
                    val newCard = Cards.updateCard(
                        card.value!!, question, example, answer, translation, image.value
                    )
                    Cards.updateCardList(cardId, newCard)
                }
            }
    }

    fun setImage(image: Bitmap?) {
        _image.value = image
    }

    companion object {
        fun Factory(cardId: Int): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return EditCardViewModel(cardId) as T
            }
        }
    }
}