package com.example.lab6

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.databinding.FragmentArrayListBinding

class ArrayListFragment : Fragment() {

    private var _binding: FragmentArrayListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CustomRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArrayListBinding.inflate(layoutInflater, container, false)
        val recyclerView: RecyclerView = binding.recyclerid
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CustomRecyclerAdapter(action).apply {
            cards = CardService.cards
        }
        recyclerView.adapter = adapter
        binding.addbuttonid.setOnClickListener {
            val action = ArrayListFragmentDirections.actionListCardFragmentToAddCardFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    private val action = object : ActionInterface {
        override fun onItemClick(cardId: Int) {
            val action = ArrayListFragmentDirections.actionListCardFragmentToSeeCardFragment(cardId)
            findNavController().navigate(action)
        }

        override fun onDeleteCard(cardId: Int) {
            val card = CardService.getCardById(cardId)
            AlertDialog.Builder(requireContext()).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Вы действительно хотите удалить карточку?")
                .setMessage("Будет удалена карточка:\n ${card.answer} / ${card.translation}")
                .setPositiveButton("Да") { _, _ ->
                    CardService.removeCard(card.id)
                    adapter.cards = CardService.cards
                }.setNegativeButton("Нет") { _, _ ->
                    Toast.makeText(
                        requireContext(), "Удаление отменено", Toast.LENGTH_LONG
                    ).show()
                }.show()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.cards = CardService.cards
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}