package br.edu.jonathangs.pokmontcgdeveloper.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.Card
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import br.edu.jonathangs.pokmontcgdeveloper.ui.cards.CardsAdapter
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.types_group_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModel<SearchViewModel>()

    private val adapter = CardsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTypes()
        setupCards()
        subscribe()
        searchFab.setOnClickListener { viewModel.search() }
    }

    private fun subscribe() {
        viewModel.observeTypes.observe(owner = viewLifecycleOwner) {
            renderTypes(it)
        }
        viewModel.observeCards.observe(
            viewLifecycleOwner,
            { renderCards(it) }
        )
    }

    private fun renderTypes(state: LoadState<List<String>>) {
        when (state) {
            is LoadState.InProgress -> {
                loading_types.visibility = View.VISIBLE
            }
            is LoadState.Success -> {
                types.post {
                    state.data?.forEach { type ->
                        val chip = createTypeOption(type)
                        types.addView(chip)
                    }
                    loading_types.visibility = View.GONE
                }
            }
            is LoadState.Exception -> {
                Log.d("PokemonApp", "renderTypes: ${state.cause}")
                loading_types.visibility = View.GONE
            }
        }
    }

    private fun renderCards(state: LoadState<List<Card>>) {
        when (state) {
            is LoadState.InProgress -> {}
            is LoadState.Success -> state.data?.let { adapter.setItems(it) }
            is LoadState.Exception -> {
                Log.d("PokemonApp", "render: ${state.cause.message}", state.cause)
            }
        }
    }

    private fun setupCards() {
        cards.adapter = adapter
    }

    private fun setupTypes() {
        types.isSingleSelection = false
    }

    private fun createTypeOption(type: String): Chip {
        val chip = Chip(requireContext())
        return chip.apply {
            text = type
            isCheckable = true
            setOnCheckedChangeListener { _, isChecked ->
                onTypeChecked(isChecked, type)
            }
        }
    }

    private fun onTypeChecked(
        isChecked: Boolean,
        type: String
    ) {
        if (isChecked)
            viewModel.selectType(type)
        else
            viewModel.unselectType(type)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle()
            }
    }

}