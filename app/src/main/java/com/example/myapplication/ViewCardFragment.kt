package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentViewCardBinding

class ViewCardFragment : Fragment() {
    private var _binding: FragmentViewCardBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ViewCardFragmentArgs>()
    private val cardId by lazy { args.cardId }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentViewCardBinding.inflate(layoutInflater, container, false)

        val card = Model.getCardById(cardId)

        binding.questionTextCard.text = getString(R.string.questionT, card.question)
        binding.hintTextCard.text = getString(R.string.hintT, card.example)
        binding.answerTextCard.text = getString(R.string.answerT, card.answer)
        binding.translationTextCard.text = getString(R.string.translateT, card.translate)
        card.image?.let { binding.cardImage.setImageBitmap(it) }

        binding.edit.setOnClickListener {
            val action = ViewCardFragmentDirections.actionViewCardFragmentToEditCardFragment(cardId)
            findNavController().navigate(action)
        }
        return binding.root
    }

}