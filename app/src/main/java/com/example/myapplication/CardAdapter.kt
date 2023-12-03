package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RecyclerItemBinding

class CardAdapter(
    private val action: ActionInterface,
    private var cardList: List<Card>,
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbnail: ImageView = binding.thumbnail
        val answerText: TextView = binding.answer
        val translateText: TextView = binding.translate
        val delete: ImageView = binding.deleteIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]

        if (card.image != null) {
            holder.thumbnail.setImageBitmap(cardList[position].image)
        } else {
            holder.thumbnail.setImageResource(R.drawable.panorama_outline)
        }
        holder.answerText.text = card.answer
        holder.translateText.text = card.translate

        holder.itemView.setOnClickListener {
            action.onItemClick(card.id)
        }

        holder.delete.setOnClickListener {
            action.onDeleteCard(card.id)
        }
    }


    fun setCards(cardList: List<Card>) {
        this.cardList = cardList
        notifyDataSetChanged()
    }

}

interface ActionInterface {
    fun onItemClick(cardId: Int)
    fun onDeleteCard(cardId: Int)
}