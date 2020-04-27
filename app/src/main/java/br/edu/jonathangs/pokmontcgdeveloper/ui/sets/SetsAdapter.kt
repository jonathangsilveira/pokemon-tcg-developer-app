package br.edu.jonathangs.pokmontcgdeveloper.ui.sets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.database.Set
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sets_card_item.view.*
import kotlinx.android.synthetic.main.sets_item.view.*

internal class SetsAdapter(
    private val style: Style = Style.SIMPLE,
    private val items: List<Set>
) : RecyclerView.Adapter<SetsAdapter.SetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsViewHolder {
        return when (style) {
            Style.SIMPLE -> createSimpleViewHolder(parent)
            else -> createCardViewHolder(parent)
        }
    }

    private fun createSimpleViewHolder(parent: ViewGroup): SimpleViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.sets_item, parent, false)
        return SimpleViewHolder(layout)
    }

    private fun createCardViewHolder(parent: ViewGroup): CardViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.sets_card_item, parent, false)
        return CardViewHolder(layout)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SetsViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    internal class SimpleViewHolder(itemView: View) : SetsViewHolder(itemView) {

        override fun bind(item: Set) {
            itemView.set_name.text = item.name
        }

    }

    internal class CardViewHolder(itemView: View) : SetsViewHolder(itemView) {

        override fun bind(item: Set) {
            Picasso.get().load(item.symbolUrl).into(itemView.symbol_image)
            itemView.name_text.text = item.name
            itemView.total_cards_text.text = item.totalCards.toString()
        }

    }

    internal abstract class SetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal abstract fun bind(item: Set)

    }

    internal enum class Style {

        SIMPLE, CARD

    }

}