package me.smbduknow.vegandrinks.search

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.smbduknow.vegandrinks.R
import me.smbduknow.vegandrinks.common.observeNotNull
import me.smbduknow.vegandrinks.data.model.Product
import me.smbduknow.vegandrinks.details.ProductActivity

class SearchActivity : AppCompatActivity() {

    private val vm: SearchViewModel by viewModels()

    private val suggestionAdapter by lazy { SearchResultsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = suggestionAdapter
            viewTreeObserver.addOnScrollChangedListener {
                header.isSelected = canScrollVertically(-1)
            }
        }

        search_edit.onSubmit { text ->
            vm.dispatch(SearchAction.StartSearch(text))
        }
        suggestionAdapter.onItemClickListener = { product ->
            // TODO vm.dispatch(SearchAction.SelectProduct(product))
            startActivity(ProductActivity.newIntent(this, product))
        }

        vm.viewState.observeNotNull(this) { state ->
            when (state) {
                is SearchViewState.Content -> setContent(state.items)
                is SearchViewState.Initial -> setEmpty("Is your fav beer vegan friendly?")
                is SearchViewState.NoResults -> setEmpty("Nothing was found. Please try again")
                is SearchViewState.NoConnection -> setEmpty("Please check your connection")
                is SearchViewState.Error -> setEmpty("Something went wrong :(")
                is SearchViewState.Loading -> setLoading()
            }
        }
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

    private fun hideKeyboardFrom(view: View) {
        val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun EditText.onSubmit(callback: (text: String) -> Unit) {
        setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    callback(v.text?.toString() ?: "")
                    hideKeyboardFrom(v)
                    return true
                }
                return false
            }
        })
    }
}
