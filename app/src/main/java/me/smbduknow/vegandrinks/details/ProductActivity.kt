package me.smbduknow.vegandrinks.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_product.*
import me.smbduknow.vegandrinks.R
import me.smbduknow.vegandrinks.data.model.Product

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = intent.getParcelableExtra<Product?>("product")

        title_text.text = product?.product_name
        company_text.text = product?.company?.company_name
        status_text.text = product?.status
        notes_text.text = product?.company?.notes
    }

    companion object {

        fun newIntent(context: Context, product: Product) =
            Intent(context, ProductActivity::class.java).apply {
                putExtra("product", product)
            }
    }
}
