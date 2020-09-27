package br.edu.jonathangs.pokmontcgdeveloper.ui.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.edu.jonathangs.pokmontcgdeveloper.R
import kotlinx.android.synthetic.main.fragment_cards.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class CardsFragment : Fragment(R.layout.fragment_cards) {

    private val viewModel: CardsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeRefresh()
        subscribe()
    }

    private fun setupSwipeRefresh() {
        card_refresher.setOnRefreshListener {
            viewModel.nextPage()
        }
    }

    private fun subscribe() {
    }

    private fun showLoading() {
        card_refresher.isRefreshing = true
    }

    private fun hideLoading() {
        card_refresher.isRefreshing = false
    }

}
