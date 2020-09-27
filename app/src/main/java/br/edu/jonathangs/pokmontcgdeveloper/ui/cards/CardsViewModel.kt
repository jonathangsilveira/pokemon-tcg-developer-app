package br.edu.jonathangs.pokmontcgdeveloper.ui.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.edu.jonathangs.pokmontcgdeveloper.domain.Repository
import kotlinx.coroutines.Dispatchers

internal class CardsViewModel(
    application: Application,
    private val repo: Repository
) : AndroidViewModel(application) {

    private val io = viewModelScope.coroutineContext + Dispatchers.IO

    private val page = MutableLiveData(1)

    internal fun nextPage() {
        val currentPage = page.value!!
        page.value = currentPage.inc()
    }

}