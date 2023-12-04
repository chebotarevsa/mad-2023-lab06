package com.example.lab5

import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


sealed class Status(var isProcessed: Boolean = false) //Запечатанный класс - ограничние наследников
class Success() : Status() //Успешно
class Failed(val message: String) : Status() //Неуспешно

open class CustomEmptyTextWatcher : TextWatcher { //Открытый класс, слежение за текстом
    //Методы для текста
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) = Unit

} //Unit - нет возвращаемого
class CardEditViewModel : ViewModel() {
    private var Mcards = MutableLiveData<Card>()
    val сards: LiveData<Card> = Mcards
    private var Mquestion_error = MutableLiveData<String>()
    val question_error: LiveData<String> = Mquestion_error
    private var Mexample_error = MutableLiveData<String>()
    val example_error: LiveData<String> = Mexample_error
    private var Manswer_error = MutableLiveData<String>()
    val answer_error: LiveData<String> = Manswer_error
    private var Mtranslation_error = MutableLiveData<String>()
    val translation_error: LiveData<String> = Mtranslation_error
    private var Mstatus = MutableLiveData<Status>()
    val status: LiveData<Status> = Mstatus
    private var Mimage = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = Mimage

    fun setCardOfFragment(cardId: Int) {
        if (cardId != -1) {
            Mcards.value = Model.getCardById(cardId)
        }
    }

    fun updateCardById(
        cardId: Int,
        question: String,
        example: String,
        answer: String,
        translation: String,
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        Mstatus.value = Failed("One or several fields are blank")
    } else {
        with(Model) {
            updateCard(
                сards.value!!, question, example, answer, translation, image.value
            ).also { Mcards.value = it }
            updateCardList(cardId, сards.value!!)
        }
        Mstatus.value = Success()
    }

    fun addCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        Mstatus.value = Failed("One or several fields are blank")
    } else {
        with(Model) {
            createNewCard(
                question, example, answer, translation, image
            ).also {
                Mcards.value = it
                addCardToList(it) }
        }
        Mstatus.value = Success()
    }

    fun setImage(image: Bitmap?) {
        Mimage.value = image
    }

    fun validateQuestion(question: String) {
        if (question.isBlank()) {
            Mquestion_error.value = "Error"
        }
    }

    fun validateExample(example: String) {
        if (example.isBlank()) {
            Mexample_error.value = "Error"
        }
    }

    fun validateAnswer(answer: String) {
        if (answer.isBlank()) {
            Manswer_error.value = "Error"
        }
    }

    fun validateTranslation(translation: String) {
        if (translation.isBlank()) {
            Mtranslation_error.value = "Error"
        }
    }

}