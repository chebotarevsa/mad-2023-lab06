package com.example.myapplication

import android.graphics.Bitmap

object Cards {

    private val _cards = mutableListOf(
        Card(0,
            "to decide not to do or have something",
            "After careful consideration, he chose to ... the job offer",
            "Decline",
            "Отклонить"),
        Card(1,
            "to provide information or facts that can be used to prove something",
            "Can you ... evidence to support your claim?",
            "Substantiate",
            "Подтвердить"),
        Card(2,
            "to combine two or more things to create something new",
            "The chef decided to ... different cuisines to create a unique dish",
            "Blend",
            "Смешать")
    )
    val cards
        get() = _cards.toList()

    fun removeCard(id: Int) {
        _cards.removeIf {
            it.id == id
        }
    }

    fun addCard(card: Card) {
        _cards.add(card)
    }

    fun updateCardList(position: Int, card: Card) {
        _cards.remove(_cards[position])
        _cards.add(position, card)
    }

    fun updateCard(
        oldCard: Card,
        question: String,
        example: String,
        answer: String,
        translation: String,
        image: Bitmap?
    ): Card {
        return oldCard.copy(oldCard.id, question, example, answer, translation, image)
    }

    fun getCardById(id: Int): Card =
        _cards.first { it.id == id }

    fun createNewCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ): Card {
        val nextId = _cards.maxBy { it.id }.id + 1
        val card = Card(nextId, question, example, answer, translation, image)
        return card
    }


}