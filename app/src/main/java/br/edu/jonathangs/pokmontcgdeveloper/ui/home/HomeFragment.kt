package br.edu.jonathangs.pokmontcgdeveloper.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.ui.cards.CardsFragment
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
        adapter.addItem(CardsFragment())
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

}
