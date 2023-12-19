package com.example.lab6

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.lab6.databinding.FragmentCardAddBinding

class CardAddFragment : Fragment() {
    private var _binding: FragmentCardAddBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardAddBinding.inflate(layoutInflater)

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.addButton.setOnClickListener {
            val question = binding.enterQuestion.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledQuestion)
            val example = binding.enterExample.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledExample)
            val answer = binding.enterAnswer.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledAnswer)
            val translation = binding.enterTranslation.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledTranslation)
            val newCard = Model.createNewCard(question, example, answer, translation, image)
            Model.addCard(newCard)
            val action = CardAddFragmentDirections.actionCardAddFragmentToCardListFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        image = it.bitmap(requireContext())
        binding.cardImage.setImageBitmap(image)
    }

}