package br.edu.jonathangs.pokmontcgdeveloper.ui.sets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import br.edu.jonathangs.pokmontcgdeveloper.R
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState
import kotlinx.android.synthetic.main.fragment_sets.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SetsFragment : Fragment(R.layout.fragment_sets) {

    private val viewModel: SetsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        viewModel.sets.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ListState.InProgress -> showLoading()
                is ListState.Success<*, *> -> hideLoading()
                else -> hideLoading()
            }
        }
    }

    private fun showLoading() {
        loading_view.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_view.visibility = View.GONE
    }

}
