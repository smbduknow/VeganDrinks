package me.smbduknow.vegandrinks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import me.smbduknow.vegandrinks.common.SingleLiveEvent
import me.smbduknow.vegandrinks.data.SearchRepository
import me.smbduknow.vegandrinks.data.exception.NoConnectionException
import me.smbduknow.vegandrinks.data.model.Product

class MainViewModel : ViewModel() {

    private val repository = SearchRepository()

    val viewState = flowOf<ViewState>(ViewState.Initial)
        .onCompletion { emitAll(actionFlow) }
        .asLiveData(Dispatchers.Default)

    private val viewAction = SingleLiveEvent<ViewAction>()

    private val actionFlow = viewAction.asFlow()
        .flatMapLatest(::handleAction)

    fun acceptAction(action: ViewAction) {
        viewAction.value = action
    }

    private suspend fun doSearch(query: String): Flow<ViewState> =
        repository.search(query)
            .map { result -> handleResult(result) }
            .catch { error -> emit(handleError(error)) }
            .onStart { emit(ViewState.Loading) }

    private suspend fun handleAction(action: ViewAction): Flow<ViewState> = when (action) {
        is ViewAction.StartSearch -> doSearch(action.query)
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

    sealed class ViewAction {
        class StartSearch(val query: String) : ViewAction()
    }
}
