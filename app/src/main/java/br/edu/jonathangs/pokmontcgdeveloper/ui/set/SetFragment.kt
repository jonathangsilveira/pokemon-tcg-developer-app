package br.edu.jonathangs.pokmontcgdeveloper.ui.set

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.SetCard
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
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

    private val adapter = CardsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code = it.getString(CODE)
            name = it.getString(NAME)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        subscribe()
    }

    private fun setupRecyclerView() {
        set_cards.adapter = adapter
    }

    private fun setupToolbar() {
        toolbar.title = name
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun subscribe() {
        viewModel.observeCards.observe(
            owner = viewLifecycleOwner,
            onChanged = { state -> render(state) }
        )
    }

    private fun render(state: LoadState<List<SetCard>>) {
        when (state) {
            is LoadState.InProgress -> showLoading()
            is LoadState.Exception -> showFailure(state.cause)
            is LoadState.Success -> {
                hideLoading()
                state.data ?: return
                adapter.setItems(state.data)
            }
        }
    }

    private fun showLoading() {
        loading_view.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_view.visibility = View.GONE
    }

    private fun showFailure(cause: Throwable) {
        Toast.makeText(requireActivity(), cause.message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newBundle(code: String, name: String): Bundle {
            return bundleOf(CODE to code, NAME to name)
        }

        private const val NAME = "backdropImage"

        private const val CODE = "setCode"

    }

}
