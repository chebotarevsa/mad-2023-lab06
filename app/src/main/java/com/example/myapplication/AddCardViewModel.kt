package com.example.myapplication

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddCardViewModel : ViewModel() {
    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image
    fun addCard(
        question: String,
        example: String,
        answer: String,
        translation: String,
        image: Bitmap?
    ) {
        val newCard = Model.NewCard(question, example, answer, translation, image)
        Model.addCard(newCard)
    }
    fun setImageToCard(image: Bitmap?){
        _image.value=image
    }
}