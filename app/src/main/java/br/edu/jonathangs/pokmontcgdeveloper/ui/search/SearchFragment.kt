package br.edu.jonathangs.pokmontcgdeveloper.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.Card
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.NetworkException
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.ResponseException
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import br.edu.jonathangs.pokmontcgdeveloper.ui.cards.CardsAdapter
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.types_group_layout.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

typealias OnFilterCheckedChange = (isChecked: Boolean, value: String) -> Unit

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModel<SearchViewModel>()

    private val adapter = CardsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTypes()
        setupSupertypes()
        setupSubtypes()
        setupCards()
        subscribe()
        searchFab.setOnClickListener { viewModel.search() }
    }

    private fun setupSubtypes() {
        subtypes.options_title.setText(R.string.subtypes)
        subtypes.options_chipGroup.isSingleSelection = false
    }

    private fun setupSupertypes() {
        supertypes.options_title.setText(R.string.supertypes)
        supertypes.options_chipGroup.isSingleSelection = false
    }

    private fun subscribe() {
        viewModel.observeTypes.observe(owner = viewLifecycleOwner) { renderTypes(it) }
        viewModel.observeSupertypes.observe(owner = viewLifecycleOwner) { renderSupertypes(it) }
        viewModel.observeSubtypes.observe(owner = viewLifecycleOwner) { renderSubtypes(it) }
        viewModel.observeCards.observe(owner = viewLifecycleOwner) { renderCards(it) }
    }

    private fun renderSubtypes(state: LoadState<List<String>>) {
        when (state) {
            is LoadState.InProgress -> subtypes.loading()
            is LoadState.Success -> subtypes.loaded(
                data = state.data,
                onCheckedChange = { isChecked, value ->
                    viewModel.onSubtypeChecked(isChecked, value)
                }
            )
            is LoadState.Exception -> subtypes.error(state.cause)
        }
    }

    private fun renderSupertypes(state: LoadState<List<String>>) {
        when (state) {
            is LoadState.InProgress -> supertypes.loading()
            is LoadState.Success -> supertypes.loaded(
                data = state.data,
                onCheckedChange = { isChecked, value ->
                    viewModel.onSupertypeChecked(isChecked, value)
                }
            )
            is LoadState.Exception -> supertypes.error(state.cause)
        }
    }

    private fun renderTypes(state: LoadState<List<String>>) {
        when (state) {
            is LoadState.InProgress -> types.loading()
            is LoadState.Success -> types.loaded(
                data = state.data,
                onCheckedChange = { isChecked, value ->
                    viewModel.onTypeChecked(isChecked, value)
                }
            )
            is LoadState.Exception -> types.error(state.cause)
        }
    }

    private fun View.loading() {
        options_loading.visibility = View.VISIBLE
    }

    private inline fun View.loaded(
        data: List<String>?,
        crossinline onCheckedChange: OnFilterCheckedChange
    ) {
        post {
            data?.forEach { type ->
                val chip = createOption(type, onCheckedChange)
                options_chipGroup.addView(chip)
            }
            if (data.isNullOrEmpty())
                visibility = View.GONE
            else
                options_loading.visibility = View.GONE
        }
    }

    private fun View.error(error: Throwable) {
        Log.d("PokemonApp", "renderTypes: $error")
        visibility = View.GONE
        onFailure(error)
    }

    private fun renderCards(state: LoadState<List<Card>>) {
        when (state) {
            is LoadState.InProgress -> {}
            is LoadState.Success -> state.data?.let { adapter.setItems(it) }
            is LoadState.Exception -> {
                Log.d("PokemonApp", "render: ${state.cause.message}", state.cause)
                onFailure(state.cause)
            }
        }
    }

    private fun onFailure(cause: Throwable) {
        val message = when (cause) {
            is ResponseException -> cause.message
            is NetworkException -> getString(R.string.network_error)
            else -> getString(R.string.generic_error)
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setupCards() {
        cards.adapter = adapter
    }

    private fun setupTypes() {
        types.options_title.setText(R.string.types)
        types.options_chipGroup.isSingleSelection = false
    }

    private inline fun createOption(
        description: String,
        crossinline onCheckedChange: OnFilterCheckedChange
    ): Chip {
        val chip = Chip(requireContext())
        return chip.apply {
            text = description
            isCheckable = true
            setOnCheckedChangeListener { _, isChecked ->
                onCheckedChange(isChecked, description)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle()
            }
    }

}