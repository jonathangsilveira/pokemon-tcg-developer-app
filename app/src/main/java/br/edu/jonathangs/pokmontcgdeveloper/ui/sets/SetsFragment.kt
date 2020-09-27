package br.edu.jonathangs.pokmontcgdeveloper.ui.sets

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.data.local.dao.Sets
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import kotlinx.android.synthetic.main.fragment_sets.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SetsFragment : Fragment(R.layout.fragment_sets) {

    private val viewModel: SetsViewModel by viewModel()

    private val adapter = SetsAdapter(
        style = SetsAdapter.Style.CARD,
        onClick = onClick()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sets_refresher.setOnRefreshListener {
            viewModel.refresh()
            sets_refresher.isRefreshing = false
        }
        setupRecyclerView()
        subscribe()
    }

    private fun setupRecyclerView() {
        set_list.layoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL,
            false
        )
        set_list.adapter = adapter
    }

    private fun subscribe() {
        viewModel.observeSetsState.observe(
            viewLifecycleOwner,
            onChanged = { state -> render(state) }
        )
    }

    private fun render(state: LoadState<Sets>) {
        when (state) {
            is LoadState.InProgress -> showLoading()
            is LoadState.Success -> {
                state.data?.let { adapter.setItems(items = state.data) }
                hideLoading()
            }
            is LoadState.Exception -> showFailure(state.cause)
        }
    }

    private fun showLoading() {
        loading_view.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_view.visibility = View.GONE
    }

    private fun onClick(): (code: String, name: String) -> Unit = { setCode, name ->
        /*val bundle = SetFragment.newBundle(setCode, name)
        findNavController().navigate(R.id.action_home_to_set, bundle)*/
    }

    private fun showFailure(cause: Throwable) {
        Toast.makeText(requireActivity(), cause.message, Toast.LENGTH_SHORT).show()
    }

}
