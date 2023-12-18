package com.example.lab6

import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


sealed class Status(var isProcessed: Boolean = false)
class Success() : Status()
class Failed(val message: String) : Status()

open class CustomEmptyTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) = Unit

}

class EditCardViewModel : ViewModel() {

    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card


    private var _questionError = MutableLiveData<String>()
    val questionError: LiveData<String> = _questionError
    private var _exampleError = MutableLiveData<String>()
    val exampleError: LiveData<String> = _exampleError
    private var _answerError = MutableLiveData<String>()
    val answerError: LiveData<String> = _answerError
    private var _translationError = MutableLiveData<String>()
    val translationError: LiveData<String> = _translationError

    private var _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image

    fun setCardOfFragment(cardId: Int) {
        if (cardId != -1) {
            _card.value = Model.getCardById(cardId)
        }
    }

    fun updateCardById(
        cardId: Int,
        question: String,
        example: String,
        answer: String,
        translation: String,
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        _status.value = Failed("One or several fields are blank")
    } else {
        with(Model) {
            updateCard(
                card.value!!, question, example, answer, translation, image.value
            ).also { _card.value = it }
            updateCardList(cardId, card.value!!)
        }
        _status.value = Success()
    }

    fun addCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        _status.value = Failed("One or several fields are blank")
    } else {
        with(Model) {
            createNewCard(
                question, example, answer, translation, image
            ).also {
                _card.value = it
                addCardToList(it) }
        }
        _status.value = Success()
    }

    fun setImage(image: Bitmap?) {
        _image.value = image
    }

    fun validateQuestion(question: String) {
        if (question.isBlank()) {
            _questionError.value = "Error"
        }
    }

    fun validateExample(example: String) {
        if (example.isBlank()) {
            _exampleError.value = "Error"
        }
    }

    fun validateAnswer(answer: String) {
        if (answer.isBlank()) {
            _answerError.value = "Error"
        }
    }

    fun validateTranslation(translation: String) {
        if (translation.isBlank()) {
            _translationError.value = "Error"
        }
    }
}