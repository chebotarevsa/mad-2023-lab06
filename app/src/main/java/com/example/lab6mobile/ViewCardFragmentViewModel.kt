import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6mobile.Data.CardsRepository
import com.example.lab6mobile.Data.TermCard

class ViewCardFragmentViewModel : ViewModel() {

    private var cardIndex: Int = 0
    private val _card = MutableLiveData<TermCard?>()
    val card: LiveData<TermCard?> get() = _card

    fun init(index: Int) {
        cardIndex = index
        loadCard()
    }

    private fun loadCard() {
        val cards = CardsRepository.getCards()
        if (cardIndex in cards.indices) {
            _card.value = cards[cardIndex]
        } else {
            _card.value = null
        }
    }
}


