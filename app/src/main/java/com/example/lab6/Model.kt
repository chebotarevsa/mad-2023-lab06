package com.example.lab6

import android.graphics.Bitmap

object Model {
    private val _cards = mutableListOf(
        Card(
            "0",
            "to choose a small number of things, or to choose by making careful decisions",
            "here was a choice of four prizes, and the winner could ... one of them",
            "Select",
            "Выбрать"
        ), Card(
            "1",
            "to make something more modern or suitable for use now by adding new information or changing its design",
            "an ...ed version of the software",
            "Update",
            "Обновить"
        ), Card(
            "2",
            "to take something away",
            "An operation was needed to ... the bullets from his chest",
            "Remove",
            "Удалить"
        ), Card(
            "3",
            "to make something happen or exist",
            "The project will ... more than 500 jobs",
            "Create",
            "Создать"
        )
    )
    val cards
        get() = _cards.toList()

    fun getCardById(id: String): Card {
        if (id == "-1") {
            return createNewCard("", "", "", "", null)
        } else {
            return _cards.first { it.id == id }

        }
    }

    fun deleteCard(id: String) {
        _cards.removeIf {
            it.id == id
        }
    }

    fun addCard(card: Card) {
        _cards.add(card)
    }

    fun updateCardList(card1: Card, card2: Card) {
        val num = _cards.indexOf(card1)
        _cards.remove(card1)
        _cards.add(num, card2)
    }

    fun updateCardList(cardId: String, card: Card) {
        _cards.remove(getCardById(cardId))
        _cards.add(card)
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