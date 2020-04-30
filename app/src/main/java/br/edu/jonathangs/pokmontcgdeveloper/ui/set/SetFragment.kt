package br.edu.jonathangs.pokmontcgdeveloper.ui.set

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.database.Card
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_set.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class SetFragment : Fragment(R.layout.fragment_set) {

    private var setCode: String? = null

    private var backdropImage: String? = null

    private val viewModel: SetViewModel by viewModel { parametersOf(setCode) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            setCode = it.getString(SET_CODE)
            backdropImage = it.getString(BACKDROP_IMAGE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backdropImage?.let {
            Picasso.get().load(it).into(app_bar_image)
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
        set_cards.adapter = SetAdapter(cards = state.data ?: emptyList())
        hideLoading()
    }

    private fun onFailure(state: ListState.Failure<Card>) {
        hideLoading()
    }

    companion object {
        fun newBundle(setCode: String, backdropImage: String): Bundle {
            return bundleOf(SET_CODE to setCode, BACKDROP_IMAGE to backdropImage)
        }

        private const val BACKDROP_IMAGE = "backdropImage"
        private const val SET_CODE = "setCode"

    }

}
