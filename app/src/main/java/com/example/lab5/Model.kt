package com.example.lab5

import android.graphics.Bitmap

object Model {
    private val _cards = mutableListOf(
        Card(
            0,
            "what we usually say when we meet",
            "... Ben, how are you?",
            "Hello",
            "Привет"
        ), Card(
            1,
            "what surrounds us, what includes each person",
            "this news has spread all over the ...",
            "World",
            "Мир"
        )
    )
    val cards get() = _cards.toList()

    fun getCardById(id: Int): Card =
        _cards.first { it.id == id }

    fun removeCard(id: Int) {
        _cards.removeIf {
            it.id == id
        }
    }

    fun addCard(card: Card) {
        _cards.add(card)
    }

    fun addCardToList(card: Card) {
        _cards.add(card)
    }

    fun updateCardList(card1: Card, card2: Card) {
        val num = _cards.indexOf(card1)
        _cards.remove(card1)
        _cards.add(num, card2)
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

    fun createNewCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ): Card {
        val nextId = _cards.maxBy { it.id }.id + 1
        val card = Card(nextId, question, example, answer, translation, image)
        return card
    }
}