package com.example.lab6mobile.Data

import android.graphics.Bitmap
import java.util.UUID

typealias CardsListener = (cards: List<TermCard>) -> Unit
object CardsRepository {
    //хранит список карточек
    private var cards = mutableListOf<TermCard>()
    //хранит всех слушателей данного класса
    private val listeners = mutableListOf<CardsListener>()

    init {
        initializeCards()
    }

    private fun initializeCards() {
        val card1 = TermCard(
            id = "1",
            question = "A large vehicle that carries passengers by road, usually along a fixed route",
            example = "A school ...",
            answer = "Bus",
            translate = "Автобус",
            image = null
        )
        cards.add(card1)

        val card2 = TermCard(
            id = "2",
            question = "A place where students study at a high level to get a degree",
            example = "The ... of Cambridge",
            answer = "University",
            translate = "Университет",
            image = null
        )
        cards.add(card2)

    }

    //получение карточек
    fun getCards(): List<TermCard> {
        return cards
    }

    //удаление карточки
    fun deleteCard(cardToDelete: TermCard) {
        val indexToDelete = cards.indexOfFirst { it.id == cardToDelete.id }
        if (indexToDelete != -1) {
            cards.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    //создание новой карточки
    fun createNewCard(question: String,example: String,answer: String,translate: String,image: Bitmap?): TermCard{
        val id = UUID.randomUUID().toString()
        return TermCard(id,question,example,answer,translate,image)
    }

    //добавление карточки в список
    fun addCard(card: TermCard) {
        cards.add(card)
    }

    fun replaceCard(card:TermCard, index: Int){
        cards.removeAt(index)
        cards.add(index, card)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(cards) }
    }
}
