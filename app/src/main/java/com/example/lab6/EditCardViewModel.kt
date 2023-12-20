package com.example.lab6

import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras


class EditCardViewModel(val cardId: String) : ViewModel() {

    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image
    fun checkIfNewCard() = this.cardId == "-1"
    private fun getEmptyCard() =
        Card(id = null,question = "", example = "", translation = "", answer = "")

    init {
        if (!checkIfNewCard()) {
            _card.value = Model.getCardById(cardId)
        } else {
            _card.value = getEmptyCard()
        }
    }

    fun updateCardById(
        cardId: String,
        question: String,
        example: String,
        answer: String,
        translation: String,
    ) {
        with(Model) {
            updateCard(
                card.value!!, question, example, answer, translation, image.value
            ).also { _card.value = it }
            updateCardList(cardId, card.value!!)
        }
    }

    fun addCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ) {
        with(Model) {
            createNewCard(
                question, example, answer, translation, image
            ).also {
                _card.value = it
                addCard(it)
            }
        }
    }

    fun setImage(image: Bitmap?) {
        _image.value = image
    }

    companion object {

        fun Factory(cardId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>, extras: CreationExtras
                ): T {
                    val application =
                        checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                    return EditCardViewModel(cardId) as T
                }
            }
    }
}