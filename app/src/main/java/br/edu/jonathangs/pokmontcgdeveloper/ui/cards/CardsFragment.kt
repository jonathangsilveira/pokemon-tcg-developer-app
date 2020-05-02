package br.edu.jonathangs.pokmontcgdeveloper.ui.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.database.Card
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState
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
        viewModel.cards.observe(viewLifecycleOwner) { render(state = it) }
    }

    private fun render(state: ListState<Card>) {
        when (state) {
            is ListState.InProgress -> showLoading()
            is ListState.Success -> onSuccess(state)
            is ListState.Failure -> onFailure(state)
        }
    }

    private fun showLoading() {
        card_refresher.isRefreshing = true
    }

    private fun hideLoading() {
        card_refresher.isRefreshing = false
    }

    private fun onSuccess(state: ListState.Success<Card>) {
        cards.adapter = CardsAdapter(cards = state.data ?: emptyList())
        hideLoading()
    }

    private fun onFailure(state: ListState.Failure<Card>) {
        hideLoading()
    }

}
