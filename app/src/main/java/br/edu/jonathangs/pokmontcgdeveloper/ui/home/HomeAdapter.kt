package br.edu.jonathangs.pokmontcgdeveloper.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val items = mutableListOf<Fragment>()

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment = items[position]

    internal fun addItem(item: Fragment) {
        items.add(item)
    }

}