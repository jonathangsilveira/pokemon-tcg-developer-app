package br.edu.jonathangs.pokmontcgdeveloper.ui.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.CardsResponse
import com.squareup.picasso.Picasso

internal class CardsAdapter(private val response: CardsResponse)
    : RecyclerView.Adapter<CardsAdapter.SetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.minicard_item, parent, false)
        return SetViewHolder(layout)
    }

    override fun getItemCount(): Int = response.cards.size

    override fun onBindViewHolder(
        holder: SetViewHolder,
        position: Int
    ) = holder.bind(card = response.cards[position])

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(card: CardsResponse.Card) {
            Picasso.get()
                .load(card.imageUrl)
                .placeholder(R.drawable.card_back)
                .into(itemView as ImageView)
        }

    }

}