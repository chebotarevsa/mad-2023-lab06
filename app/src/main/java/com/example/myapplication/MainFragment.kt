package com.example.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentMainBinding

class MainFragment : Fragment(), ActionInterface {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CardAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.setCardList()

        val recyclerView: RecyclerView = binding.recid
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = CardAdapter(this, viewModel.cards.value!!).apply {
            viewModel.cards.observe(viewLifecycleOwner) {
                setCards(it)
            }
        }
        recyclerView.adapter = adapter

        binding.add.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddCardFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cardId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToViewCardFragment(cardId)
        findNavController().navigate(action)
    }

    override fun onDeleteCard(cardId: Int) {
        viewModel.setCardToDelete(cardId)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete_card_title))
        builder.setMessage(getString(R.string.delete_card_message))

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            viewModel.deleteCardById(viewModel.card.value!!.id)
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.setButtonColors(R.color.red, R.color.green)
        dialog.show()
    }


    private fun AlertDialog.setButtonColors(yesButtonColorResId: Int, cancelButtonColorResId: Int) {
        val yesButtonColor = ContextCompat.getColor(context, yesButtonColorResId)
        val cancelButtonColor = ContextCompat.getColor(context, cancelButtonColorResId)

        setOnShowListener {
            getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(yesButtonColor)
            getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(cancelButtonColor)
        }
    }
}
