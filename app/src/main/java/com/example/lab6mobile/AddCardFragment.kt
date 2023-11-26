package com.example.lab6mobile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.la6mobile.databinding.FragmentAddCardBinding
import com.example.la6mobile.R
import com.example.lab6mobile.Data.CardsRepository

const val NEW_CARD = -1
class AddCardFragment : Fragment() {

    private lateinit var binding: FragmentAddCardBinding
    private var imageBIT: Bitmap? = null


    private val args by navArgs<AddCardFragmentArgs>()
    private val index by lazy { args.id }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.activity
        binding = FragmentAddCardBinding.inflate(inflater, container, false)

        if (index != NEW_CARD) {
            val card = CardsRepository.getCards()[index]
            binding.editTextText.setText(card.question)
            binding.editTextText2.setText(card.example)
            binding.editTextText3.setText(card.answer)
            binding.editTextText4.setText(card.translate)
            imageBIT = card.image
            if (imageBIT != null) {
                binding.imageView2.setImageBitmap(imageBIT)
            } else {
                binding.imageView2.setImageResource(R.drawable.ic_image)
            }
        }

        binding.imageView2.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.button.setOnClickListener {
            addTermCard()
        }

        return binding.root
    }


    private val getImage = registerForActivityResult(ImageContract()) { uri ->
        uri?.let {
            val image = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.createSource(this.requireContext().contentResolver, uri)
            } else {
                TODO("VERSION.SDK_INT < P")
            }
            imageBIT = ImageDecoder.decodeBitmap(image)
            binding.imageView2.setImageBitmap(imageBIT)
        }
    }


    //добавление карточки
    private fun addTermCard() {

        val question = binding.editTextText.text.toString()
        val hint = binding.editTextText2.text.toString()
        val answer = binding.editTextText3.text.toString()
        val translate = binding.editTextText4.text.toString()
        val image = imageBIT

        // проверка на заполненность полей
        if (question.isEmpty() || hint.isEmpty() || answer.isEmpty() || translate.isEmpty()) {
            Toast.makeText(this.context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val newCard = CardsRepository.createNewCard(question, hint, answer, translate, image)
        if (index == NEW_CARD) {
            CardsRepository.addCard(newCard)
        } else {
            CardsRepository.replaceCard(newCard, index)
        }
        findNavController().popBackStack()
    }
}