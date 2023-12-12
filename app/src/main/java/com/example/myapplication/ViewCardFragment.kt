package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentViewCardBinding

class ViewCardFragment : Fragment() {
    private var _binding: FragmentViewCardBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ViewCardFragmentArgs>()
    private val cardId by lazy { args.cardId }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentViewCardBinding.inflate(layoutInflater, container, false)

        val card = Cards.cards.get(cardId)

        binding.cardQuestion.text = getString(R.string.questionField, card.question)
        binding.cardExample.text = getString(R.string.exampleField, card.example)
        binding.cardAnswer.text = getString(R.string.answerField, card.answer)
        binding.cardTranslation.text = getString(R.string.translationField, card.translation)
        binding.cardThumbnail.setImageURI(card.imageURI)

        binding.editButton.setOnClickListener {
            val action = ViewCardFragmentDirections.actionViewCardFragmentToEditCardFragment(cardId)
            findNavController().navigate(action)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val action =
                        ViewCardFragmentDirections.actionViewCardFragmentToCardListFragment()
                    findNavController().navigate(action)
                }
            })
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}