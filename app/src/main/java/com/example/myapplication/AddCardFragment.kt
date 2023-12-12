package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddCardBinding


class AddCardFragment : Fragment() {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentAddCardBinding.inflate(layoutInflater, container, false)

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.addButton.setOnClickListener {
            val question = when {
                binding.setQuestion.text.toString()
                    .isNotEmpty() -> binding.setQuestion.text.toString()

                else -> getString(R.string.misquestion)
            }
            val example = when {
                binding.setExample.text.toString()
                    .isNotEmpty() -> binding.setExample.text.toString()

                else -> getString(R.string.misexample)
            }
            val answer = when {
                binding.setAnswer.text.toString()
                    .isNotEmpty() -> binding.setAnswer.text.toString()

                else -> getString(R.string.misanswer)
            }
            val translation = when {
                binding.setTranslation.text.toString()
                    .isNotEmpty() -> binding.setTranslation.text.toString()

                else -> getString(R.string.mistranslation)
            }
            val newCard = Cards.createNewCard(
                question, example, answer, translation, imageUri
            )
            Cards.addCard(newCard)
            val action = AddCardFragmentDirections.actionAddCardFragmentToCardListFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        val name = requireActivity().packageName
        requireActivity().grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        binding.cardImage.setImageURI(it)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}