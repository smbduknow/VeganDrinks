package me.smbduknow.vegandrinks.search

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.smbduknow.vegandrinks.R
import me.smbduknow.vegandrinks.details.ProductActivity
import me.smbduknow.vegandrinks.feature.search.SearchResultsFragment

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchResultsFragment = supportFragmentManager.findFragmentById(R.id.searchResults)
        as? SearchResultsFragment

        searchResultsFragment?.productIntentBuilder = { product ->
            ProductActivity.newIntent(this, product)
        }

        search_edit.onSubmit { text ->
            searchResultsFragment?.startSearch(text)
        }
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
