package me.smbduknow.vegandrinks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import me.smbduknow.vegandrinks.data.SearchRepository
import me.smbduknow.vegandrinks.data.exception.NoConnectionException
import me.smbduknow.vegandrinks.data.model.Product

class MainViewModel : ViewModel() {

    val viewState = MutableLiveData<ViewState>()

    private val onSearchSubmitSubject = BehaviorSubject.create<String>()

    private val repo = SearchRepository()

    private val disposable = Observable.concat(
        Observable.just<ViewState>(ViewState.Initial),
        onSearchSubmitSubject.switchMap(::doSearch)
    )
        .subscribe { viewState.postValue(it) }

    fun onSubmit(query: String) = onSearchSubmitSubject.onNext(query)

    override fun onCleared() = disposable.dispose()

    private fun doSearch(q: String) = repo.search(q)
        .toObservable()
        .map(::handleResult)
        .onErrorReturn(::handleError)
        .startWith(ViewState.Loading)

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
