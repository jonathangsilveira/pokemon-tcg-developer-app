package br.edu.jonathangs.pokmontcgdeveloper.ui.search

import android.app.Application
import androidx.lifecycle.*
import br.edu.jonathangs.pokmontcgdeveloper.data.repo.Cards
import br.edu.jonathangs.pokmontcgdeveloper.data.repo.SearchRepository
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class SearchViewModel(
    application: Application,
    private val repo: SearchRepository
) : AndroidViewModel(application) {

    private val _selectedTypes = mutableListOf<String>()

    val observeTypes = repo.flowTypes
        .flowOn(context = Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)

    private val searchtTrigger = MutableLiveData(false)

    private val emptyCardsLiveData: LiveData<LoadState<Cards>> =
        MutableLiveData(LoadState.Success(emptyList()))

    val observeCards: LiveData<LoadState<Cards>> = searchtTrigger.switchMap { refresh ->
        if (refresh)
            repo.flowCards(_selectedTypes, refresh = true)
                .flowOn(context = Dispatchers.IO)
                .asLiveData(context = viewModelScope.coroutineContext)
        else
            emptyCardsLiveData
    }

    fun selectType(type: String) {
        _selectedTypes.add(type)
    }

    fun unselectType(type: String) {
        _selectedTypes.remove(type)
    }

    fun search() {
        searchtTrigger.value = true
    }

}