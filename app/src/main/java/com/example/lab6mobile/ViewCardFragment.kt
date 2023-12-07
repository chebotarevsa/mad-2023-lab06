package com.example.lab6mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.la6mobile.R
import com.example.la6mobile.databinding.FragmentViewCardBinding

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
        viewModel = ViewModelProvider(this).get(ViewCardFragmentViewModel::class.java)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.init(index)
        initView()
    }

    private fun initView() {
        val card = viewModel.getCard()

        if (card != null) {
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

        binding.button.setOnClickListener {
            val action =
                ViewCardFragmentDirections.actionViewCardFragmentToAddCardFragment().apply {
                    id = index
                }
            findNavController().navigate(action)
        }
    }
}