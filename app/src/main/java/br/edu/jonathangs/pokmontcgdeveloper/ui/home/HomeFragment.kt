package br.edu.jonathangs.pokmontcgdeveloper.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.RecyclerView
import br.edu.jonathangs.pokmontcgdeveloper.R
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
        content_view.adapter = adapter
        content_view.setCurrentItem(0, false)
    }

    private fun setupNavView() {
        navigation_view.setOnNavigationItemSelectedListener { select(it.itemId) }
    }

    private fun select(id: Int): Boolean {
        when (id) {
            R.id.action_sets -> content_view.setCurrentItem(0, true)
            //R.id.action_cards -> content_view.setCurrentItem(1, true)
        }
        return true
    }

}
