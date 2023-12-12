package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCardListBinding

class CardListFragment : Fragment() {
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentCardListBinding.inflate(layoutInflater, container, false)


        val recyclerView: RecyclerView = binding.recyclerid
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = RecyclerAdapter(action).apply {
            cards = Cards.cards
        }

        recyclerView.adapter = adapter

        adapter.enableSwipeToDelete(recyclerView)

        binding.addButton.setOnClickListener {
            val action = CardListFragmentDirections.actionCardListFragmentToAddCardFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshCardsViewWith(Cards.cards)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val action = object : ActionInterface {
        override fun onItemClick(cardId: Int) {
            val action = CardListFragmentDirections.actionCardListFragmentToViewCardFragment(cardId)
            findNavController().navigate(action)
        }

        override fun onDeleteCard(cardId: Int) {
            showDeleteConfirmationDialog(cardId)
        }
    }

    private fun showDeleteConfirmationDialog(cardId: Int) {
        val card = Cards.getCardById(cardId)

        AlertDialog.Builder(requireContext()).setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(getString(R.string.delete_card_title))
            .setMessage(getString(R.string.delete_card_message, card.answer, card.translation))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                Cards.removeCard(card.id)
                adapter.refreshCardsViewWith(Cards.cards)
                Toast.makeText(
                    requireContext(), getString(R.string.deleted_successfully), Toast.LENGTH_LONG
                ).show()
            }.setNegativeButton(getString(R.string.no)) { _, _ ->
                Toast.makeText(
                    requireContext(), getString(R.string.deletion_canceled), Toast.LENGTH_LONG
                ).show()
                adapter.notifyItemChanged(cardId)
            }.show()

    }

}