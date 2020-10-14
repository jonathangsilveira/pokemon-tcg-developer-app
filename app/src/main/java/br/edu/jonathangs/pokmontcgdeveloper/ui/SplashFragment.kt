package br.edu.jonathangs.pokmontcgdeveloper.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.jonathangs.pokmontcgdeveloper.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            delay(TimeUnit.SECONDS.toMillis(3))
            home()
        }
    }

    private fun home() { findNavController().navigate(R.id.action_splash_to_home) }

}
