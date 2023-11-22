package com.example.lab6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab6.databinding.FragmentSeeCardBinding

class SeeCardFragment : Fragment() {
    private var _binding: FragmentSeeCardBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<SeeCardFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeCardBinding.inflate(layoutInflater, container, false)
        val card = Model.getCardById(cardId)

        binding.cardQuestion.text = getString(R.string.questionField, card.question)
        binding.cardExample.text = getString(R.string.exampleField, card.example)
        binding.cardAnswer.text = getString(R.string.answerField, card.answer)
        binding.cardTranslation.text = getString(R.string.translationField, card.translation)
        card.image?.let {
            binding.cardThumbnail.setImageBitmap(it)
        }

        binding.editButton.setOnClickListener {
            val action = SeeCardFragmentDirections.actionSeeCardFragmentToEditCardFragment(cardId)
            findNavController().navigate(action)
        }

        binding.backButton.setOnClickListener {
            val action = SeeCardFragmentDirections.actionSeeCardFragmentToListCardFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}