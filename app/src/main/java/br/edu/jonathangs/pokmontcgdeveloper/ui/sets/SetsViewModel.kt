package br.edu.jonathangs.pokmontcgdeveloper.ui.sets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.jonathangs.pokmontcgdeveloper.domain.Repository
import kotlinx.coroutines.Dispatchers

class SetsViewModel(
    application: Application,
    repo: Repository
): AndroidViewModel(application) {

    private val ioContext = viewModelScope.coroutineContext + Dispatchers.IO

    val sets = repo.allSets(ioContext)

}