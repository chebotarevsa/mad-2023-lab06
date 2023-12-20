package com.example.lab6

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class SeeCardViewModel(cardId: String) : ViewModel() {
    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image
    init {
        _card.value = Model.getCardById(cardId)
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
                    return SeeCardViewModel(cardId) as T
                }
            }
    }
}