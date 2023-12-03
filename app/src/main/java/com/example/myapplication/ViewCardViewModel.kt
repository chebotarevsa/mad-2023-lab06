package com.example.myapplication

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewCardViewModel : ViewModel() {
    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image
    fun setCardToView(cardId: Int) {
        _card.value = Model.getCardById(cardId)
    }
    fun setImageToCard(image: Bitmap?){
        _image.value=image
    }

}