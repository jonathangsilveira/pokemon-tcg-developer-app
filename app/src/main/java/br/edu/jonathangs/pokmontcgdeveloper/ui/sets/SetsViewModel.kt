package br.edu.jonathangs.pokmontcgdeveloper.ui.sets

import android.app.Application
import androidx.lifecycle.*
import br.edu.jonathangs.pokmontcgdeveloper.data.local.dao.Sets
import br.edu.jonathangs.pokmontcgdeveloper.data.repo.SetsRepository
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class SetsViewModel(
    application: Application,
    repo: SetsRepository
): AndroidViewModel(application) {

    private val refreshTrigger = MutableLiveData(Unit)

    val observeSetsState: LiveData<LoadState<Sets>> = refreshTrigger.switchMap {
        repo.flowSets()
            .flowOn(context = Dispatchers.IO)
            .asLiveData(context = viewModelScope.coroutineContext)
    }

    fun refresh() {
        refreshTrigger.value = Unit
    }

}