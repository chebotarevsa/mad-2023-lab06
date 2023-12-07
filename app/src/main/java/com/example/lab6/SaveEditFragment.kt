package com.example.lab6

import android.graphics.Bitmap
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
import com.example.lab6.databinding.FragmentSaveEditBinding

class SaveEditFragment : Fragment() {

    private var _binding: FragmentSaveEditBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null
    private val args by navArgs<SaveEditFragmentArgs>()
    private val cardId by lazy { args.cardId }
    private val viewModel: CardManagerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveEditBinding.inflate(layoutInflater, container, false)
        viewModel.setCard(cardId)
        viewModel.card.observe(viewLifecycleOwner) {
            binding.questionField.setText(it.question)
            binding.exampleField.setText(it.example)
            binding.answerField.setText(it.answer)
            binding.translationField.setText(it.translation)
            if (it.image != null) {
                binding.cardImage.setImageBitmap(it.image)
                viewModel.setImage(it.image)
            } else {
                binding.cardImage.setImageResource(R.drawable.image_icon)
            }
        }
        viewModel.image.observe(viewLifecycleOwner) {
            binding.cardImage.setImageBitmap(it)
        }
        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }
        viewModel.questionError.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                binding.questionField.error = it
            }
        }
        viewModel.exampleError.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                binding.exampleField.error = it
            }
        }
        viewModel.answerError.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                binding.answerField.error = it
            }
        }
        viewModel.translationError.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                binding.translationField.error = it
            }
        }
        viewModel.status.observe(viewLifecycleOwner) {
            if (it.isProcessed) {
                return@observe
            }
            if (it is Failed) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            } else if (it is Success) {
                if (cardId != -1) {
                    val navAction =
                        SaveEditFragmentDirections.actionEditCardFragmentToSeeCardFragment(
                            cardId
                        )
                    findNavController().navigate(navAction)
                } else {
                    val navAction =
                        SaveEditFragmentDirections.actionEditCardFragmentToListCardFragment()
                    findNavController().navigate(navAction)
                }
            }
            it.isProcessed = true
        }
        validateFields()
        binding.saveButton.setOnClickListener {
            createOrUpdateCard()
        }
        return binding.root
    }

    private fun validateFields() {
        binding.questionField.addTextChangedListener(object : CustomEmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validateQuestion(s.toString())
            }
        })
        binding.exampleField.addTextChangedListener(object : CustomEmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validateExample(s.toString())
            }
        })
        binding.answerField.addTextChangedListener(object : CustomEmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validateAnswer(s.toString())
            }
        })
        binding.translationField.addTextChangedListener(object : CustomEmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validateTranslation(s.toString())
            }
        })
    }

    private fun createOrUpdateCard() {
        if (viewModel.card.value != null) {
            viewModel.updateCardById(
                cardId,
                binding.questionField.text.toString(),
                binding.exampleField.text.toString(),
                binding.answerField.text.toString(),
                binding.translationField.text.toString(),
            )
        } else {
            viewModel.addCard(
                binding.questionField.text.toString(),
                binding.exampleField.text.toString(),
                binding.answerField.text.toString(),
                binding.translationField.text.toString(),
                viewModel.image.value
            )
        }
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