package com.example.lab6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab6.databinding.FragmentSeeCardBinding

class SeeCardFragment : Fragment() {
    private var _binding: FragmentSeeCardBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<SeeCardFragmentArgs>()
    private val cardId by lazy { args.cardId }
    private val viewModel: SeeCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeCardBinding.inflate(layoutInflater, container, false)
        viewModel.setCardOfFragment(cardId)

        viewModel.card.observe(viewLifecycleOwner) {
            binding.cardQuestion.text = getString(R.string.questionField, it.question)
            binding.cardExample.text = getString(R.string.exampleField, it.example)
            binding.cardAnswer.text = getString(R.string.answerField, it.answer)
            binding.cardTranslation.text = getString(R.string.translationField, it.translation)
            if (it.image != null) {
                binding.cardImage.setImageBitmap(it.image)
            } else {
                binding.cardImage.setImageResource(R.drawable.wallpapericon)
            }
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