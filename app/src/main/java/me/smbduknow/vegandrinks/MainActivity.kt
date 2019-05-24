package me.smbduknow.vegandrinks

import android.os.Bundle
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

        btn_search.setOnClickListener { vm.onSubmit(search_edit.text?.toString() ?: "") }

        suggestionAdapter.onItemClickListener = { product ->
            val company = vm.companies.find{ it.id == product.company_id }
            startActivity(ProductActivity.newIntent(this, product, company))
        }

        vm.suggestionsState.observeNotNull(this) { suggestions ->
            suggestionAdapter.items = suggestions
            suggestionAdapter.notifyDataSetChanged()
        }
    }
}