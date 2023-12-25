package com.example.lab6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab6.databinding.FragmentCardViewBinding

class CardViewFragment : Fragment() {
    private var _binding: FragmentCardViewBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<CardViewFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardViewBinding.inflate(layoutInflater)
        val card = Model.cards[cardId]

        binding.textQuestion.text = getString(R.string.questionField, card.question)
        binding.textExample.text = getString(R.string.exampleField, card.example)
        binding.textAnswer.text = getString(R.string.answerField, card.answer)
        binding.textTranslation.text = getString(R.string.translationField, card.translation)
        card.image?.let {
            binding.picture.setImageBitmap(it)
        }

        binding.buttonEdit.setOnClickListener {
            val action = CardViewFragmentDirections.actionCardViewFragmentToCardEditFragment(cardId)
            findNavController().navigate(action)
        }

        binding.buttonBack.setOnClickListener {
            val action = CardViewFragmentDirections.actionCardViewFragmentToCardListFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

}





