package com.example.lab6mobile

import NEW_CARD
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6mobile.Data.CardsRepository

class AddCardFragmentViewModel : ViewModel() {

    private val _imageBitmap = MutableLiveData<Bitmap?>()
    val imageBitmap: LiveData<Bitmap?> get() = _imageBitmap

    fun setImageBitmap(bitmap: Bitmap?) {
        _imageBitmap.value = bitmap
    }

    fun addOrUpdateCard(
        question: String,
        hint: String,
        answer: String,
        translate: String,
        image: Bitmap?,
        index: Int
    ) {
        val newCard = CardsRepository.createNewCard(question, hint, answer, translate, image)
        if (index == NEW_CARD) {
            CardsRepository.addCard(newCard)
        } else {
            CardsRepository.replaceCard(newCard, index)
        }
    }
}
