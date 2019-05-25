package me.smbduknow.vegandrinks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import me.smbduknow.vegandrinks.data.SearchRepository
import me.smbduknow.vegandrinks.data.model.Product

class MainViewModel : ViewModel() {

    val suggestionsState = MutableLiveData<List<Product>>()

    private val onSearchSubmitSubject = BehaviorSubject.create<String>()

    private val repo = SearchRepository()

    private val disposable = onSearchSubmitSubject.hide()
        .switchMapSingle(repo::search)
        .onErrorReturnItem(emptyList())
        .subscribe { suggestionsState.postValue(it) }

    fun onSubmit(query: String) = onSearchSubmitSubject.onNext(query)

    override fun onCleared() = disposable.dispose()
}
