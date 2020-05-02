package br.edu.jonathangs.pokmontcgdeveloper.ui.set

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.database.Card
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState
import br.edu.jonathangs.pokmontcgdeveloper.ui.cards.CardsAdapter
import kotlinx.android.synthetic.main.fragment_set.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class SetFragment : Fragment(R.layout.fragment_set) {

    private var code: String? = null

    private var name: String? = null

    private val viewModel: SetViewModel by viewModel { parametersOf(code) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code = it.getString(CODE)
            name = it.getString(NAME)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = name
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        subscribe()
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
        loading_view.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_view.visibility = View.GONE
    }

    private fun onSuccess(state: ListState.Success<Card>) {
        set_cards.adapter =
            CardsAdapter(
                cards = state.data ?: emptyList()
            )
        hideLoading()
    }

    private fun onFailure(state: ListState.Failure<Card>) {
        hideLoading()
    }

    companion object {

        fun newBundle(code: String, name: String): Bundle {
            return bundleOf(CODE to code, NAME to name)
        }

        private const val NAME = "backdropImage"

        private const val CODE = "setCode"

    }

}
