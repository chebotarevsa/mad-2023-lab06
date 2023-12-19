package com.example.lab6

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab6.databinding.FragmentCardEditBinding

class CardEditFragment : Fragment() {
    private var _binding: FragmentCardEditBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null

    private val args by navArgs<CardEditFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardEditBinding.inflate(inflater, container, false)
        val card = Model.cards[cardId]

        binding.questionField.setText(card.question)
        binding.exampleField.setText(card.example)
        binding.answerField.setText(card.answer)
        binding.translationField.setText(card.translation)
        binding.cardImage.setImageBitmap(card.image)
        image = card.image

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.saveButton.setOnClickListener {
            val question = binding.questionField.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledQuestion)
            val example = binding.exampleField.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledExample)
            val answer = binding.answerField.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledAnswer)
            val translation = binding.translationField.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledTranslation)
            val newCard = Model.updateCard(card, question, example, answer, translation, image)
            Model.updateCardList(cardId, newCard)
            val action = CardEditFragmentDirections.actionCardEditFragmentToCardSeeFragment(cardId)
            findNavController().navigate(action)
        }
        return binding.root
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        image = it.bitmap(requireContext())
        binding.cardImage.setImageBitmap(image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}