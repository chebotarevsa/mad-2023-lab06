package com.example.lab6mobile.Data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.la6mobile.R
import com.example.la6mobile.databinding.ItemCardBinding

interface CallbackFun {
    fun showCard(index: Int): Unit
    fun deleteCard(card: TermCard): Unit
}

class CardsAdapter(private val callback: CallbackFun ): RecyclerView.Adapter<CardsAdapter.CardHolder>() {

    private var _list: List<TermCard> = emptyList()

    class CardHolder(val binding: ItemCardBinding) : ViewHolder(binding.root) {
//        val textItem = itemView.findViewById<TextView>(R.id.cardsNameTextView)
//        val textItem2 = itemView.findViewById<TextView>(R.id.cardsTranslateTextView)
    }

    fun setItem(list: List<TermCard>) {
        _list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding2 = ItemCardBinding.inflate(inflater, parent, false)
        return CardHolder(binding2)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val item = _list[position]
        holder.binding.cardsNameTextView.text = item.answer
        holder.binding.cardsTranslateTextView.text = item.translate

        if (item.image != null)
            holder.binding.photoImageView.setImageBitmap(item.image)
        else
            holder.binding.photoImageView.setImageResource(R.drawable.ic_image)

        //нажатие на элемент карточки
        holder.itemView.setOnClickListener {
            callback.showCard(position)
        }

        //установка стиля при нажатии/отпускании
        holder.binding.photoImageViewButtonDelete.setOnTouchListener { view, motionEvent ->

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    holder.binding.photoImageViewButtonDelete.setBackgroundResource(R.drawable.round_background)
                }

                MotionEvent.ACTION_UP -> {
                    holder.binding.photoImageViewButtonDelete.setBackgroundResource(R.drawable.base_round_background)
                    callback.deleteCard(item)
                    notifyDataSetChanged()// Обычный фон
                }
            }
            true
        }
    }
}

