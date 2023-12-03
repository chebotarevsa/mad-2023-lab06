package com.example.lab5

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.databinding.FragmentCardListBinding

class CardListFragment : Fragment() {
    private var _binding: FragmentCardListBinding? = null //Для привязки макета
    private val binding get() = _binding!! //Не может быть пустым, вызовет значение _binding
    private lateinit var adapter: AdapterRecyclerView //Адаптер

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardListBinding.inflate(layoutInflater)
        val recyclerView: RecyclerView = binding.recyclerId
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdapterRecyclerView(action).apply {
            cards = Model.cards
        }
        recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {//Кнопка добавления нового элемента
            val action =
                CardListFragmentDirections.actionCardListFragmentToCardAddFragment() //Переход на другой элемент
            findNavController().navigate(action) //Получение контроллера
        }
        return binding.root

    }

    private val action = object : ActionInterface { //Перенесено из адаптера
        override fun onItemClick(cardId: Int) {
            val action = CardListFragmentDirections.actionCardListFragmentToCardSeeFragment(cardId)
            findNavController().navigate(action)
        }

        override fun onDeleteCard(cardId: Int) {
            val card = Model.getCardById(cardId)
            AlertDialog.Builder(requireContext()).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Вы действительно хотите удалить карточку?")
                .setMessage("Будет удалена карточка:\n ${card.answer} / ${card.translation}")
                .setPositiveButton("Да") { _, _ ->
                    Model.removeCard(card.id)
                    adapter.cards = Model.cards
                }.setNegativeButton("Нет") { _, _ ->
                    Toast.makeText(
                        requireContext(), "Удаление отменено", Toast.LENGTH_LONG
                    ).show()
                }.show()
        }
    }
}