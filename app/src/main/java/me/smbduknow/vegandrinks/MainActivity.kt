package me.smbduknow.vegandrinks

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.smbduknow.vegandrinks.common.observeNotNull
import me.smbduknow.vegandrinks.details.ProductActivity

class MainActivity : AppCompatActivity() {

    private val vm by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }

    private val suggestionAdapter by lazy { DrinkListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        suggestionListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = suggestionAdapter
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

        vm.suggestionsState.observeNotNull(this) { suggestions ->
            suggestionAdapter.items = suggestions
            suggestionAdapter.notifyDataSetChanged()
        }
    }

    fun hideKeyboardFrom(view: View) {
        val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}