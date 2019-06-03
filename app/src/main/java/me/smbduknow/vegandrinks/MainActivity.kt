package me.smbduknow.vegandrinks

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.smbduknow.vegandrinks.common.observeNotNull
import me.smbduknow.vegandrinks.data.model.Product
import me.smbduknow.vegandrinks.details.ProductActivity

class MainActivity : AppCompatActivity() {

    private val vm by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }

    private val suggestionAdapter by lazy { DrinkListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = suggestionAdapter
            viewTreeObserver.addOnScrollChangedListener {
                header.isSelected = canScrollVertically(-1)
            }
        }

        search_edit.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    vm.onSubmit(v.text?.toString() ?: "")
                    hideKeyboardFrom(v)
                    return true
                }
                return false
            }
        })

        suggestionAdapter.onItemClickListener = { product ->
            startActivity(ProductActivity.newIntent(this, product))
        }

        vm.viewState.observeNotNull(this) { state ->
            when (state) {
                is MainViewModel.ViewState.Content -> setContent(state.items)
                is MainViewModel.ViewState.Initial -> setEmpty("Is your fav beer vegan friendly?")
                is MainViewModel.ViewState.NoResults -> setEmpty("Nothing was found. Please try again")
                is MainViewModel.ViewState.NoConnection -> setEmpty("Please check your connection")
                is MainViewModel.ViewState.Error -> setEmpty("Something went wrong :(")
                is MainViewModel.ViewState.Loading -> setLoading()
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
}