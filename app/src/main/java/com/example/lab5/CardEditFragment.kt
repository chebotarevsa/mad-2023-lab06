package com.example.lab5

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab5.databinding.FragmentCardEditBinding

class CardEditFragment : Fragment() {
    private var _binding: FragmentCardEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CardEditViewModel by viewModels()

    private val args by navArgs<CardEditFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardEditBinding.inflate(layoutInflater, container, false)

        with(viewModel) {
            setCardOfFragment(cardId)
            with(binding) {
                сard.observe(viewLifecycleOwner) {
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
                image.observe(viewLifecycleOwner) {
                    cardImage.setImageBitmap(it)
                }
                cardImage.setOnClickListener {
                    getSystemContent.launch("image/*")
                }
                question_error.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        questionField.error = it
                    }
                }
                example_error.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        exampleField.error = it
                    }
                }
                answer_error.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        answerField.error = it
                    }
                }
                translation_error.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        translationField.error = it
                    }
                }
                status.observe(viewLifecycleOwner) {
                    if (it.isProcessed) {
                        return@observe
                    }
                    if (it is Failed) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else if (it is Success) {
                        if (cardId != -1) {
                            val navAction =
                                CardEditFragmentDirections.actionCardEditFragmentToCardSeeFragment(
                                    cardId
                                )
                            findNavController().navigate(navAction)
                        } else {
                            val navAction =
                                CardEditFragmentDirections.actionCardEditFragmentToCardListFragment()
                            findNavController().navigate(navAction)
                        }
                    }
                    it.isProcessed = true
                }
                questionField.addTextChangedListener(object : CustomEmptyTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        validateQuestion(s.toString())
                    }
                })
                exampleField.addTextChangedListener(object : CustomEmptyTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        validateExample(s.toString())
                    }
                })
                answerField.addTextChangedListener(object : CustomEmptyTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        validateAnswer(s.toString())
                    }
                })
                translationField.addTextChangedListener(object : CustomEmptyTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        validateTranslation(s.toString())
                    }
                })
                saveButton.setOnClickListener {
                    if (сard.value != null) {
                        updateCardById(
                            cardId,
                            questionField.text.toString(),
                            exampleField.text.toString(),
                            answerField.text.toString(),
                            translationField.text.toString(),
                        )
                    } else {
                        addCard(
                            questionField.text.toString(),
                            exampleField.text.toString(),
                            answerField.text.toString(),
                            translationField.text.toString(),
                            image.value
                        )
                    }
                }
                return root
            }
    }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.setImage(it.bitmap(requireContext()))
    }

}
