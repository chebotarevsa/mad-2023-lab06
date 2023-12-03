package com.example.lab5

import android.graphics.Bitmap

object Model {
    private val _cards = mutableListOf( //Создание списка mutableListOf
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
    val cards get() = _cards.toList() //Создаётся переменная cards, которая содержит в себе весь список элементов, что-то типа статической переменной

    fun getCardById(id: Int): Card =
        _cards.first { it.id == id }

    fun removeCard(id: Int) { //Всё просто - удаление по id
        _cards.removeIf {
            it.id == id
        }
    }

    fun addCard(card: Card) { //Всё просто - добавления нового элемента
        _cards.add(card)
    }

    fun updateCardList(card1: Card, card2: Card) { //Всё не просто - передача 2-х карточек
        val num = _cards.indexOf(card1) //Берём id первой карты
        _cards.remove(card1) //Удаляем первую и на место первой ставим вторую
        _cards.add(num, card2)
    }

    fun updateCardList(position: Int, card: Card) { //То же самое: удаление по id карточки и замена
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
    ): Card { //Тип, который функция будет возвращать
        return oldCard.copy(oldCard.id, question, example, answer, translation, image) //Копирование всей переданной карочки
        //и вставка в копированный элемент тех значений, что были переданы вместе с функцией
    }

    fun createNewCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ): Card {
        val nextId = _cards.maxBy { it.id }.id + 1 //Ищется максимальный id среди всех элементов списка, найденное присваивается nextId, после +1
        val card = Card(nextId, question, example, answer, translation, image)
        return card
    }
}