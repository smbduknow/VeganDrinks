package me.smbduknow.vegandrinks.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_product.*
import me.smbduknow.vegandrinks.R
import me.smbduknow.vegandrinks.data.model.Company
import me.smbduknow.vegandrinks.data.model.Product

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = intent.getParcelableExtra<Product?>("product")
        val company = intent.getParcelableExtra<Company?>("company")

        title_text.text = product?.product_name
        company_text.text = company?.company_name
        status_text.text = product?.status
        notes_text.text = company?.notes
    }

    companion object {

        fun newIntent(context: Context, product: Product, company: Company?) =
            Intent(context, ProductActivity::class.java).apply {
                putExtra("product", product)
                putExtra("company", company)
            }
    }
}
