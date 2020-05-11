package br.edu.jonathangs.pokmontcgdeveloper.ui.set

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.jonathangs.pokmontcgdeveloper.domain.CardRepository
import kotlinx.coroutines.Dispatchers

class SetViewModel(
    application: Application,
    repo: CardRepository,
    setCode: String
) : AndroidViewModel(application) {

    private val io = viewModelScope.coroutineContext + Dispatchers.IO

    val cards = repo.setCards(setCode = setCode, context = io)

}