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
import com.example.lab6.databinding.FragmentAddCardBinding

class AddCardFragment : Fragment() {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCardBinding.inflate(layoutInflater, container, false)

        with(binding) {
            with(viewModel) {
                cardImage.setOnClickListener {
                    getSystemContent.launch("image/*")
                }
                viewModel.image.observe(viewLifecycleOwner) {
                    cardImage.setImageBitmap(it)
                }
                questionError.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        enterQuestion.error = it
                    }
                }
                exampleError.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        enterExample.error = it
                    }
                }
                answerError.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        enterAnswer.error = it
                    }
                }
                translationError.observe(viewLifecycleOwner) {
                    if (it.isNotBlank()) {
                        enterTranslation.error = it
                    }
                }
                image.observe(viewLifecycleOwner) {
                    cardImage.setImageBitmap(it)
                }
                status.observe(viewLifecycleOwner) {
                    if (it.isProcessed) return@observe
                    if (it is Failed) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    else if (it is Success) {
                        val action =
                            AddCardFragmentDirections.actionAddCardFragmentToListCardFragment()
                        findNavController().navigate(action)
                    }
                    it.isProcessed = true
                }
                cardImage.setOnClickListener {
                    getSystemContent.launch("image/*")
                }
                enterQuestion.addTextChangedListener(object : CustomEmptyTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        validateQuestion(s.toString())
                    }
                })
                enterExample.addTextChangedListener(object : CustomEmptyTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        validateExample(s.toString())
                    }
                })
                enterAnswer.addTextChangedListener(object : CustomEmptyTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        validateAnswer(s.toString())
                    }
                })
                enterTranslation.addTextChangedListener(object : CustomEmptyTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        validateTranslation(s.toString())
                    }
                })
                addButton.setOnClickListener {
                    viewModel.addCard(
                        enterQuestion.text.toString(),
                        enterExample.text.toString(),
                        enterAnswer.text.toString(),
                        enterTranslation.text.toString(),
                        viewModel.image.value
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