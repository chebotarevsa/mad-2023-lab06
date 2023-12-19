package com.example.lab6

import android.graphics.Bitmap

object Model {
    private val _cards = mutableListOf<Card>()
    val cards: List<Card>
        get() = _cards.toList()

    fun removeCard(id: Int) {
        _cards.removeIf { it.id == id }
    }

    fun addCard(card: Card) {
        _cards.add(card)
    }

    fun updateCardList(position: Int, card: Card) {
        _cards.removeAt(position)
        _cards.add(position, card)
    }
    fun getCardById(id: Int): Card =
        _cards.first { it.id == id }
    fun updateCard(
        oldCard: Card,
        question: String,
        example: String,
        answer: String,
        translation: String,
        image: Bitmap?
    ): Card {
        return oldCard.copy(
            question = question,
            example = example,
            answer = answer,
            translation = translation,
            image = image
        )
    }

    fun createNewCard(
        question: String,
        example: String,
        answer: String,
        translation: String,
        image: Bitmap?
    ): Card {
        val nextId = _cards.maxByOrNull { it.id }?.id?.plus(1) ?: 0
        return Card(nextId, question, example, answer, translation, image)
    }
}