package br.edu.jonathangs.pokmontcgdeveloper.ui.sets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.database.Set
import kotlinx.android.synthetic.main.sets_item.view.*

internal class SetsAdapter(private val items: List<Set>)
    : RecyclerView.Adapter<SetsAdapter.SetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.sets_item, parent, false)
        return SetsViewHolder(layout)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SetsViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    internal class SetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal fun bind(item: Set) {
            itemView.set_name.text = item.name
        }

    }

}