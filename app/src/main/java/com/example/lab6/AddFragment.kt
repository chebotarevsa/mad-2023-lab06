package com.example.lab6

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab6.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.addButton.setOnClickListener {
            val question = when {
                binding.enterQuestion.text.toString()
                    .isNotEmpty() -> binding.enterQuestion.text.toString()

                else -> requireContext().getString(R.string.question_field_empty)
            }
            val example = when {
                binding.enterExample.text.toString()
                    .isNotEmpty() -> binding.enterExample.text.toString()

                else -> requireContext().getString(R.string.example_field_empty)
            }
            val answer = when {
                binding.enterAnswer.text.toString()
                    .isNotEmpty() -> binding.enterAnswer.text.toString()

                else -> requireContext().getString(R.string.answer_field_empty)
            }
            val translation = when {
                binding.enterTranslation.text.toString()
                    .isNotEmpty() -> binding.enterTranslation.text.toString()

                else -> requireContext().getString(R.string.translation_field_empty)
            }
            val newCard = CardService.createNewCard(
                question, example, answer, translation, image
            )
            CardService.addCard(newCard)
            val action = AddFragmentDirections.actionAddCardFragmentToListCardFragment()
            findNavController().navigate(action)
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