package br.edu.jonathangs.pokmontcgdeveloper.ui.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.edu.jonathangs.pokmontcgdeveloper.domain.Repository

internal class CardsViewModel(
    application: Application,
    private val repo: Repository
) : AndroidViewModel(application) {

}