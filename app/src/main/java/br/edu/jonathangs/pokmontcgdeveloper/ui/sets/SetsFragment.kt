package br.edu.jonathangs.pokmontcgdeveloper.ui.sets

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.database.model.Set
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState
import br.edu.jonathangs.pokmontcgdeveloper.network.NetworkException
import br.edu.jonathangs.pokmontcgdeveloper.ui.set.SetFragment
import kotlinx.android.synthetic.main.fragment_sets.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SetsFragment : Fragment(R.layout.fragment_sets) {

    private val viewModel: SetsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribe()
    }

    private fun setupRecyclerView() {
        set_list.layoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL,
            false
        )
    }

    private fun subscribe() {
        viewModel.sets.observe(viewLifecycleOwner) { render(state = it) }
    }

    private fun render(state: ListState<Set>) {
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

    private fun onSuccess(state: ListState.Success<Set>) {
        state.data?.let {
            set_list.adapter = SetsAdapter(
                style = SetsAdapter.Style.CARD,
                items = it,
                onClick = onClick()
            )
        }
        hideLoading()
        if (state.networkFailure is NetworkException)
            showNetworkFailure(state.networkFailure)
    }

    private fun onClick(): (code: String, name: String) -> Unit = { setCode, name ->
        val bundle = SetFragment.newBundle(setCode, name)
        findNavController().navigate(R.id.action_home_to_set, bundle)
    }

    private fun onFailure(state: ListState.Failure<Set>) {
        hideLoading()
    }

    private fun showNetworkFailure(cause: NetworkException) {
        Toast.makeText(requireActivity(), cause.message, Toast.LENGTH_SHORT).show()
    }

}
