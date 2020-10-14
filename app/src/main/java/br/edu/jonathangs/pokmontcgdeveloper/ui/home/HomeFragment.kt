package br.edu.jonathangs.pokmontcgdeveloper.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.ui.search.SearchFragment
import br.edu.jonathangs.pokmontcgdeveloper.ui.sets.SetsFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupNavView()
    }

    private fun setupViewPager() {
        val adapter = HomeAdapter(this)
        adapter.addItem(SetsFragment())
        //adapter.addItem(CardsFragment())
        adapter.addItem(SearchFragment.newInstance())
        content_view.onPageScrollStateChanged { state ->
            if (state == ViewPager2.SCROLL_STATE_IDLE)
                when (content_view.currentItem) {
                    0 -> navigation_view.selectedItemId = R.id.action_sets
                    1 -> navigation_view.selectedItemId = R.id.action_cards
                }
        }
        content_view.adapter = adapter
        content_view.setCurrentItem(0, false)
    }

    private fun setupNavView() {
        navigation_view.setOnNavigationItemSelectedListener { select(it.itemId) }
    }

    private fun select(id: Int): Boolean {
        when (id) {
            R.id.action_sets -> changePage(position = 0)
            R.id.action_cards -> changePage(position = 1)
        }
        return true
    }

    private fun changePage(position: Int) {
        content_view.setCurrentItem(position, true)
    }

    private inline fun ViewPager2.onPageScrollStateChanged(
        crossinline onStateChanged: (state: Int) -> Unit
    ) {
        val callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                onStateChanged(state)
            }
        }
        registerOnPageChangeCallback(callback)
    }

}
