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
import com.example.lab6.databinding.FragmentArrayListBinding

class ArrayListFragment : Fragment() {

    private var _binding: FragmentArrayListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CustomRecyclerAdapter
    private val viewModel: ArrayListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArrayListBinding.inflate(layoutInflater, container, false)
        val recyclerView: RecyclerView = binding.recyclerid
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CustomRecyclerAdapter(action).apply {
            viewModel.cards.observe(viewLifecycleOwner) {
                cards = it
            }
        }
        recyclerView.adapter = adapter
        binding.addbuttonid.setOnClickListener {
            val action = ArrayListFragmentDirections.actionListCardFragmentToEditCardFragment(-1)
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
            viewModel.setCard(cardId)
            AlertDialog.Builder(requireContext()).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Вы действительно хотите удалить карточку?")
                .setMessage("Будет удалена карточка:\n ${viewModel.card.value?.answer} / ${viewModel.card.value?.translation}")
                .setPositiveButton("Да") { _, _ ->
                    viewModel.removeCardById(cardId)
                }.setNegativeButton("Нет") { _, _ ->
                    Toast.makeText(
                        requireContext(), "Удаление отменено", Toast.LENGTH_LONG
                    ).show()
                }.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}