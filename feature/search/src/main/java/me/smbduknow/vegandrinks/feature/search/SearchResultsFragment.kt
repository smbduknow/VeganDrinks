package me.smbduknow.vegandrinks.feature.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_search_results.*
import kotlinx.coroutines.flow.collect
import me.smbduknow.vegandrinks.feature.search.domain.model.Product
import me.smbduknow.vegandrinks.feature.search.presentation.Action
import me.smbduknow.vegandrinks.feature.search.presentation.PresentationModel
import me.smbduknow.vegandrinks.feature.search.presentation.ViewState

class SearchResultsFragment : Fragment(R.layout.fragment_search_results) {

    private val vm: PresentationModel by viewModels()

    private val suggestionAdapter by lazy { SearchResultsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            vm.observe().collect { state ->
                when (state) {
                    is ViewState.Content -> setContent(state.items)
                    is ViewState.Initial -> setEmpty("Is your fav beer vegan friendly?")
                    is ViewState.NoResults -> setEmpty("Nothing was found. Please try again")
                    is ViewState.NoConnection -> setEmpty("Please check your connection")
                    is ViewState.Error -> setEmpty("Something went wrong :(")
                    is ViewState.Loading -> setLoading()
                }
            }
        }

        suggestionAdapter.onItemClickListener = { product ->
            vm.dispatch(Action.SelectProduct(product))
        }

    }

    fun startSearch(query: String) {
        vm.dispatch(Action.StartSearch(query))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvItems.adapter = suggestionAdapter
    }

    private fun setContent(items: List<Product>) {
        rvItems.isVisible = true
        progressBar.isVisible = false
        tvPlaceholder.isVisible = false
        suggestionAdapter.items = items
        suggestionAdapter.notifyDataSetChanged()
    }

    private fun setEmpty(message: String) {
        rvItems.isVisible = false
        progressBar.isVisible = false
        tvPlaceholder.isVisible = true
        tvPlaceholder.text = message
    }

    private fun setLoading() {
        rvItems.isVisible = false
        progressBar.isVisible = true
        tvPlaceholder.isVisible = false
    }

}
