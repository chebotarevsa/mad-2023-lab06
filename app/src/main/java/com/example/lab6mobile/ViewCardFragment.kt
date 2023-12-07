package com.example.lab6mobile

import ViewCardFragmentViewModel
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.la6mobile.R
import com.example.la6mobile.databinding.FragmentViewCardBinding
import com.example.lab6mobile.Data.TermCard

class ViewCardFragment : Fragment() {

    private lateinit var binding: FragmentViewCardBinding
    private lateinit var viewModel: ViewCardFragmentViewModel

    private val args by navArgs<ViewCardFragmentArgs>()
    private val index by lazy { args.id }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewCardFragmentViewModel::class.java)

        viewModel.card.observe(viewLifecycleOwner, { card ->
            card?.let {
                updateUI(it)
            }
        })

        binding.button.setOnClickListener {
            val action =
                ViewCardFragmentDirections.actionViewCardFragmentToAddCardFragment().apply {
                    id = index
                }
            findNavController().navigate(action)
        }

        viewModel.init(index)
    }

    private fun updateUI(card: TermCard) {
        binding.questionField.text = card.question
        binding.exampleField.text = card.example
        binding.answerView.text = card.answer
        binding.translateView.text = card.translate

        if (card.image != null) {
            binding.imageView3.setImageBitmap(card.image)
        } else {
            binding.imageView3.setImageResource(R.drawable.ic_image)
        }
    }
}

