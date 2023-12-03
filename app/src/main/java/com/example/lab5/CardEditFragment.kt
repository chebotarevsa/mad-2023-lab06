package com.example.lab5

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab5.databinding.FragmentCardEditBinding

class CardEditFragment : Fragment() {
    private var _binding: FragmentCardEditBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null

    private val args by navArgs<CardEditFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardEditBinding.inflate(layoutInflater)
        val card = Model.cards.get(cardId) //Получение самой карточки

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

        binding.saveButton.setOnClickListener {//Сохранение, считывая то, что введено
            val question = when {
                binding.questionField.text.toString()
                    .isNotEmpty() -> binding.questionField.text.toString()

                else -> "Поле вопроса отсутствует"
            }
            val example = when {
                binding.exampleField.text.toString()
                    .isNotEmpty() -> binding.exampleField.text.toString()

                else -> "Поле примера отсутствует"
            }
            val answer = when {
                binding.answerField.text.toString()
                    .isNotEmpty() -> binding.answerField.text.toString()

                else -> "Поле ответа отсутствует"
            }
            val translation = when {
                binding.translationField.text.toString()
                    .isNotEmpty() -> binding.translationField.text.toString()

                else -> "Поле перевода отсутствует"
            }
            val newCard = Model.updateCard(
                card, question, example, answer, translation, image
            )
            Model.updateCardList(cardId, newCard)
            val action = CardEditFragmentDirections.actionCardEditFragmentToCardSeeFragment(cardId)
            findNavController().navigate(action)
        }
        return binding.root
    }
    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) { //Получение изображения
        image = it.bitmap(requireContext())
        binding.cardImage.setImageBitmap(image)
    }

}
