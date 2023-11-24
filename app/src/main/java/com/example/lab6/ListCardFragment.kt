package com.example.lab6

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.databinding.FragmentListCardBinding

class ListCardFragment : Fragment() {
    private var _binding: FragmentListCardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CustomRecyclerAdapter
    private val viewModel: ListCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCardBinding.inflate(layoutInflater, container, false)
        val recyclerView: RecyclerView = binding.recyclerid
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CustomRecyclerAdapter(action).apply {
            viewModel.cards.observe(viewLifecycleOwner) {
                cards = it
            }
        }
        recyclerView.adapter = adapter
        binding.addbuttonid.setOnClickListener {
            val action = ListCardFragmentDirections.actionListCardFragmentToAddCardFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.cards.observe(viewLifecycleOwner) {
            adapter.cards = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val action = object : ActionInterface {
        override fun onItemClick(cardId: Int) {
            val action = ListCardFragmentDirections.actionListCardFragmentToSeeCardFragment(cardId)
            findNavController().navigate(action)
        }

        override fun onDeleteCard(cardId: Int) {
            viewModel.setCardOfFragment(cardId)
            AlertDialog.Builder(requireContext())
                .setIcon(android.R.drawable.ic_menu_delete)
                .setTitle("Вы действительно хотите удалить карточку?")
                .setMessage("Будет удалена карточка:" + viewModel.getCardShortData()
                )
                .setPositiveButton("Да") { _, _ -> viewModel.removeCardById(cardId) }
                .setNegativeButton("Нет") { _, _ ->
                    Toast
                        .makeText(requireContext(), "Удаление отменено", Toast.LENGTH_LONG)
                        .show()
                }.show()
        }
    }
}