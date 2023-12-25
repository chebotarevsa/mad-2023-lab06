package com.example.lab6

//noinspection SuspiciousImport
import android.R
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
import com.example.lab6.databinding.FragmentCardListBinding

class CardListFragment : Fragment() {
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterRecyclerView
    private val viewModel: CardListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardListBinding.inflate(layoutInflater, container, false)
        val recyclerView: RecyclerView = binding.recyclerId
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdapterRecyclerView(action).apply {
            viewModel.cards.observe(viewLifecycleOwner) {
                cards = it
            }
        }
        recyclerView.adapter = adapter
        binding.addButton.setOnClickListener {
            val navAction = CardListFragmentDirections.actionCardListFragmentToCardAddFragment()
            findNavController().navigate(navAction)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val action = object : ActionInterface {
        override fun onItemClick(cardId: Int) {
            val action = CardListFragmentDirections.actionCardListFragmentToCardViewFragment(cardId)
            findNavController().navigate(action)
        }

        override fun onDeleteCard(cardId: Int) {
            viewModel.setCardOfFragment(cardId)
            AlertDialog.Builder(requireContext())
                .setIcon(R.drawable.ic_menu_delete)
                .setTitle("Вы действительно хотите удалить карточку?")
                .setMessage(
                    "Будет удалена карточка:" + viewModel.getCardShortData()
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