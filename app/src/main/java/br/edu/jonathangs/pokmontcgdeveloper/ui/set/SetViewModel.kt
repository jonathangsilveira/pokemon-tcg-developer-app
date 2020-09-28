package br.edu.jonathangs.pokmontcgdeveloper.ui.set

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.edu.jonathangs.pokmontcgdeveloper.data.repo.SetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class SetViewModel(
    application: Application,
    repo: SetRepository,
    setCode: String
) : AndroidViewModel(application) {

    val observeCards = repo.flowCards(setCode)
        .flowOn(context = Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)

}