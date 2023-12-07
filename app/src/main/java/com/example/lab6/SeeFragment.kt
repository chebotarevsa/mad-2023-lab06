package com.example.lab6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab6.databinding.FragmentSeeBinding

class SeeFragment : Fragment() {

    private var _binding: FragmentSeeBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<SeeFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentSeeBinding.inflate(layoutInflater, container, false)

        val card = CardService.cards[cardId]

        binding.cardQuestion.text = getString(R.string.question_field, card.question)
        binding.cardExample.text = getString(R.string.example_field, card.example)
        binding.cardAnswer.text = getString(R.string.answer_field, card.answer)
        binding.cardTranslation.text = getString(R.string.translation_field, card.translation)
        card.image?.let {
            binding.cardThumbnail.setImageBitmap(it)
        }

        binding.editButton.setOnClickListener {
            val action = SeeFragmentDirections.actionSeeCardFragmentToEditCardFragment(cardId)
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}