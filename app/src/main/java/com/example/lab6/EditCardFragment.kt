package com.example.lab6

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab6.databinding.FragmentEditCardBinding


class EditCardFragment : Fragment() {

    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null

    private val args by navArgs<EditCardFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCardBinding.inflate(layoutInflater, container, false)
        var card = Model.getCardById(cardId)

        binding.questionField.setText(card.question)
        binding.exampleField.setText(card.example)
        binding.answerField.setText(card.answer)
        binding.translationField.setText(card.translation)
        card.image?.let {
            binding.cardImage.setImageBitmap(it)
        }

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.saveButton.setOnClickListener {
            val question =
                binding.questionField.text.toString()

            val example =
                binding.exampleField.text.toString()

            val answer =
                binding.answerField.text.toString()


            val translation =
                binding.translationField.text.toString()


            if (cardId == "-1") {
                card = Model.createNewCard(question, example, answer, translation, image)
                Model.addCard(card)
                val action = EditCardFragmentDirections.actionEditCardFragmentToSeeCardFragment(card.id)
                findNavController().navigate(action)
            } else {
                val newCard = Model.updateCard(
                    card, question, example, answer, translation, image
                )
                Model.updateCardList(cardId, newCard)
                val action = EditCardFragmentDirections.actionEditCardFragmentToSeeCardFragment(cardId)
                findNavController().navigate(action)
            }

        }
        return binding.root
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        image = it.bitmap(requireContext())
        binding.cardImage.setImageBitmap(image)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}