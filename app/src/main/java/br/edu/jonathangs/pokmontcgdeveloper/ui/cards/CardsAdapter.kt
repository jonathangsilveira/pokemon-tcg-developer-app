package br.edu.jonathangs.pokmontcgdeveloper.ui.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.Card
import com.squareup.picasso.Picasso

internal class CardsAdapter: RecyclerView.Adapter<CardsAdapter.SetViewHolder>() {

    private val cards = mutableListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.minicard_item, parent, false)
        return SetViewHolder(layout)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(
        holder: SetViewHolder,
        position: Int
    ) = holder.bind(card = cards[position])

    fun setItems(cards: List<Card>) {
        clearItems()
        addItems(cards)
    }

    private fun clearItems() {
        this.cards.clear()
    }

    private fun addItems(cards: List<Card>) {
        this.cards.addAll(cards)
        notifyItemInserted(this.cards.size)
    }

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(card: Card) {
            Picasso.get()
                .load(card.imageUrl)
                .placeholder(R.drawable.card_back)
                .into(itemView as ImageView)
        }

    }

}