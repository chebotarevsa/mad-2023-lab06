package com.example.lab6mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.la6mobile.databinding.FragmentMainBinding
import com.example.lab6mobile.Data.CallbackFun
import com.example.lab6mobile.Data.CardsAdapter
import com.example.lab6mobile.Data.TermCard

class MainFragment : Fragment() {
    private lateinit var adapter: CardsAdapter
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        adapter = CardsAdapter(adapterCallBack)
        val layoutManager = LinearLayoutManager(context)
        binding.RecyclerView.layoutManager = layoutManager
        binding.RecyclerView.adapter = adapter

        val button = binding.button
        button.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragment3ToAddCardFragment()
            findNavController().navigate(action)
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.cardsList.observe(viewLifecycleOwner) { cards ->
            adapter.setItem(cards)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCards()
    }

    private val adapterCallBack = object : CallbackFun {
        override fun deleteCard(card: TermCard) {
            viewModel.deleteCard(card)
        }

        override fun showCard(index: Int) {
            val action = MainFragmentDirections.actionMainFragment3ToViewCardFragment(index)
            findNavController().navigate(action)
        }
    }
}
