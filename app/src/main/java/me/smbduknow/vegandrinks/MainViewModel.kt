package me.smbduknow.vegandrinks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.smbduknow.vegandrinks.data.SearchRepository
import me.smbduknow.vegandrinks.data.exception.NoConnectionException
import me.smbduknow.vegandrinks.data.model.Product

class MainViewModel : ViewModel() {

    val viewState = MutableLiveData<ViewState>()

    private val onSearchSubmitLiveData = MutableLiveData<String>()

    private val repo = SearchRepository()

    init {
        viewState.value = ViewState.Initial

        onSearchSubmitLiveData.observeForever { query ->
            viewState.value = ViewState.Loading

            GlobalScope.launch {
                viewState.postValue(doSearch(query))
            }
        }
    }

    fun onSubmit(query: String) {
        onSearchSubmitLiveData.value = query
    }

    private suspend fun doSearch(q: String): ViewState =
        withContext(Dispatchers.IO) {
            val products = repo.search(q)
            handleResult(products)
        }

    private fun handleResult(items: List<Product>) = when {
        items.isNotEmpty() -> ViewState.Content(items)
        else -> ViewState.NoResults
    }

    private fun handleError(e: Throwable) = when (e) {
        is NoConnectionException -> ViewState.NoConnection
        else -> ViewState.Error
    }

    sealed class ViewState {
        class Content(val items: List<Product>) : ViewState()
        object Initial : ViewState()
        object Loading : ViewState()
        object NoConnection : ViewState()
        object NoResults : ViewState()
        object Error : ViewState()
    }
}
