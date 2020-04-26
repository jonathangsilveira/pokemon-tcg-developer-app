package br.edu.jonathangs.pokmontcgdeveloper.ui.sets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.edu.jonathangs.pokmontcgdeveloper.network.Endpoint
import br.edu.jonathangs.pokmontcgdeveloper.network.WebService
import br.edu.jonathangs.pokmontcgdeveloper.domain.Repository

class SetsViewModel(
    application: Application,
    private val repo: Repository
): AndroidViewModel(application) {

    val sets = repo.allSets()

}