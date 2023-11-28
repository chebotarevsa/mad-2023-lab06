package com.example.lab6

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
import com.example.lab6.databinding.FragmentEditCardBinding

class EditCardFragment : Fragment() {
    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<EditCardFragmentArgs>()
    private val cardId by lazy { args.cardId }
    private val viewModel: EditCardViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCardBinding.inflate(layoutInflater, container, false)

        with(viewModel) {
            with(binding) {
                setCardOfFragment(cardId)
                card.observe(viewLifecycleOwner) {
                    questionField.setText(it.question)
                    exampleField.setText(it.example)
                    answerField.setText(it.answer)
                    translationField.setText(it.translation)
                    if (it.image != null) {
                        cardImage.setImageBitmap(it.image)
                        setImage(it.image)
                    } else {
                        cardImage.setImageResource(R.drawable.wallpapericon)
                    }
                }
                questionError.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        questionField.error = it
                    }
                }
                exampleError.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        exampleField.error = it
                    }
                }
                answerError.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        answerField.error = it
                    }
                }
                translationError.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        translationField.error = it
                    }
                }
                image.observe(viewLifecycleOwner) {
                    cardImage.setImageBitmap(it)
                }
                status.observe(viewLifecycleOwner) {
                    if (it.isProcessed) return@observe
                    when (it) {
                        is Failed -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }

                        is Success -> {
                            val action =
                                EditCardFragmentDirections.actionEditCardFragmentToSeeCardFragment(
                                    cardId
                                )
                            findNavController().navigate(action)
                        }
                    }
                    it.isProcessed = true
                }
                cardImage.setOnClickListener {
                    getSystemContent.launch("image/*")
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
                    updateCardById(
                        cardId,
                        questionField.text.toString(),
                        exampleField.text.toString(),
                        answerField.text.toString(),
                        translationField.text.toString(),
                    )
                }
                return root
            }
        }
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.setImage(it.bitmap(requireContext()))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

