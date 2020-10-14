package br.edu.jonathangs.pokmontcgdeveloper.ui.search

import android.app.Application
import androidx.lifecycle.*
import br.edu.jonathangs.pokmontcgdeveloper.PokemonApp
import br.edu.jonathangs.pokmontcgdeveloper.data.repo.Cards
import br.edu.jonathangs.pokmontcgdeveloper.data.repo.SearchRepository
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import br.edu.jonathangs.pokmontcgdeveloper.ui.ext.connectivityManager
import br.edu.jonathangs.pokmontcgdeveloper.ui.ext.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class SearchViewModel(
    application: Application,
    private val repo: SearchRepository
) : AndroidViewModel(application) {

    private val isOnline: Boolean
        get() {
            val context = getApplication<PokemonApp>().applicationContext
            return context.connectivityManager?.isOnline() == true
        }

    private val _selectedTypes = mutableListOf<String>()

    private val _selectedSupertypes = mutableListOf<String>()

    private val _selectedSubtypes = mutableListOf<String>()

    val observeTypes = repo.flowTypes
        .flowOn(context = Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)

    private val searchTrigger = MutableLiveData(false)

    private val emptyCardsLiveData: LiveData<LoadState<Cards>> =
        MutableLiveData(LoadState.Success(emptyList()))

    val observeCards: LiveData<LoadState<Cards>> = searchTrigger.switchMap { refresh ->
        if (refresh)
            repo.flowCards(
                types = _selectedTypes,
                supertypes = _selectedSupertypes,
                subtypes = _selectedSubtypes,
                refresh = isOnline
            ).flowOn(context = Dispatchers.IO)
                .asLiveData(context = viewModelScope.coroutineContext)
        else
            emptyCardsLiveData
    }

    val observeSupertypes = repo.flowSupertypes
        .flowOn(context = Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)

    val observeSubtypes = repo.flowSubtypes
        .flowOn(context = Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)

    fun search() {
        searchTrigger.value = true
    }

    fun onTypeChecked(isChecked: Boolean, value: String) {
        if (isChecked)
            _selectedTypes.add(value)
        else
            _selectedTypes.remove(value)
    }

    fun onSupertypeChecked(isChecked: Boolean, value: String) {
        if (isChecked)
            _selectedSupertypes.add(value)
        else
            _selectedSupertypes.remove(value)
    }

    fun onSubtypeChecked(isChecked: Boolean, value: String) {
        if (isChecked)
            _selectedSubtypes.add(value)
        else
            _selectedSubtypes.remove(value)
    }

}