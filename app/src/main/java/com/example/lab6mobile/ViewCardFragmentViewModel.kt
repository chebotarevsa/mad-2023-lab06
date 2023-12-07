package com.example.lab6mobile

import androidx.lifecycle.ViewModel
import com.example.lab6mobile.Data.CardsRepository
import com.example.lab6mobile.Data.TermCard

class ViewCardFragmentViewModel : ViewModel() {

    private var cardIndex: Int = 0
    private var card: TermCard? = null

    fun init(index: Int) {
        cardIndex = index
        loadCard()
    }

    fun getCard(): TermCard? {
        return card
    }

    private fun loadCard() {
        card = CardsRepository.getCards()[cardIndex]
    }
}
