package com.example.lab5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab5.databinding.FragmentCardSeeBinding

class CardSeeFragment : Fragment() {
    private var _binding: FragmentCardSeeBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<CardSeeFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardSeeBinding.inflate(layoutInflater)
        val card = Model.cards.get(cardId) //Получение данных по id

        binding.textQuestion.text = getString(R.string.questionField, card.question) //Собственно, установление текста и картиночки
        binding.textExample.text = getString(R.string.exampleField, card.example)
        binding.textAnswer.text = getString(R.string.answerField, card.answer)
        binding.textTranslation.text = getString(R.string.translationField, card.translation)
        card.image?.let {
            binding.picture.setImageBitmap(it)
        }

        binding.buttonEdit.setOnClickListener { //Кнопка редактирования
            val action = CardSeeFragmentDirections.actionCardSeeFragmentToCardEditFragment(cardId)
            findNavController().navigate(action)
        }

        binding.buttonBack.setOnClickListener {
            val action = CardSeeFragmentDirections.actionCardSeeFragmentToCardListFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

}





