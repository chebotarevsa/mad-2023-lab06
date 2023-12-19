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
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardListBinding.inflate(layoutInflater)
        val recyclerView: RecyclerView = binding.recyclerId
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdapterRecyclerView(action).apply {
            cards = Model.cards
        }
        recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            val action =
                CardListFragmentDirections.actionCardListFragmentToCardAddFragment()
            findNavController().navigate(action)
        }
        return binding.root

    }

    private val action = object : ActionInterface {
        override fun onItemClick(cardId: Int) {
            val action = CardListFragmentDirections.actionCardListFragmentToCardSeeFragment(cardId)
            findNavController().navigate(action)
        }

        override fun onDeleteCard(cardId: Int) {
            val card = Model.getCardById(cardId)
            AlertDialog.Builder(requireContext()).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure you want to delete the card?")
                .setMessage("The following card will be deleted:\n ${card.answer} / ${card.translation}")
                .setPositiveButton("Yes") { _, _ ->
                    Model.removeCard(card.id)
                    adapter.cards = Model.cards
                }.setNegativeButton("No") { _, _ ->
                    Toast.makeText(
                        requireContext(), "Deletion canceled", Toast.LENGTH_LONG
                    ).show()
                }.show()
        }
    }
}