package com.example.lab5

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.lab5.databinding.FragmentCardAddBinding

class CardAddFragment : Fragment() {
    private var _binding: FragmentCardAddBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardAddBinding.inflate(layoutInflater) //Надувание из хмл в вью

        binding.cardImage.setOnClickListener { //Имеется id - cardImage в XML, запускается при нажатии на картинку
            getSystemContent.launch("image/*") //Открытие системного окна с выбором картиночки
        }

        binding.addButton.setOnClickListener {
            val question = when { //Определение строк, где идёт считывание введённых данных, или установка своих, если пусто (и так на каждой)
                binding.enterQuestion.text.toString()
                    .isNotEmpty() -> binding.enterQuestion.text.toString()

                else -> "Поле вопроса отсутствует"
            }
            val example = when {
                binding.enterExample.text.toString()
                    .isNotEmpty() -> binding.enterExample.text.toString()

                else -> "Поле примера отсутствует"
            }
            val answer = when {
                binding.enterAnswer.text.toString()
                    .isNotEmpty() -> binding.enterAnswer.text.toString()

                else -> "Поле ответа отсутствует"
            }
            val translation = when {
                binding.enterTranslation.text.toString()
                    .isNotEmpty() -> binding.enterTranslation.text.toString()

                else -> "Поле перевода отсутствует"
            }
            val newCard = Model.createNewCard( //Создание нового объекта - элемента списка
                question, example, answer, translation, image
            )
            Model.addCard(newCard) //Добавление только что созданного объекта
            val action = CardAddFragmentDirections.actionCardAddFragmentToCardListFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }
    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        image = it.bitmap(requireContext())
        binding.cardImage.setImageBitmap(image)
    }

}