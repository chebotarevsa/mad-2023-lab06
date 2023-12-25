package com.example.lab6

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab6.databinding.FragmentCardEditBinding

class CardEditFragment : Fragment() {
    private var _binding: FragmentCardEditBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null
    private val args by navArgs<CardEditFragmentArgs>()
    private val cardId by lazy { args.cardId }
    private val viewModel: CardEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardEditBinding.inflate(inflater, container, false)

        with(viewModel) {
            setCardById(cardId)
            with(binding) {
                card.observe(viewLifecycleOwner) {
                    questionField.setText(it.question)
                    exampleField.setText(it.example)
                    answerField.setText(it.answer)
                    translationField.setText(it.translation)
                    if (it.image != null) {
                        cardImage.setImageBitmap(it.image)
                        setImage(it.image)
                    } else {
                        cardImage.setImageResource(R.drawable.empty)
                    }
                }
                cardImage.setOnClickListener {
                    getSystemContent.launch("image/*")
                }
                saveButton.setOnClickListener {
                    val question = questionField.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledQuestion)
                    val example = exampleField.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledExample)
                    val answer = answerField.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledAnswer)
                    val translation = translationField.text.toString().takeIf { it.isNotEmpty() } ?: getString(R.string.unfilledTranslation)
                    viewModel.updateCard(cardId, question, example, answer, translation)

                    val action = CardEditFragmentDirections.actionCardEditFragmentToCardViewFragment(cardId)
                    findNavController().navigate(action)
                }
                return root
            }
        }
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
