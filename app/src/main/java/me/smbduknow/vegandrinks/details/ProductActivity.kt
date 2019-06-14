package me.smbduknow.vegandrinks.details

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_product.*
import me.smbduknow.vegandrinks.R
import me.smbduknow.vegandrinks.data.model.Product

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val product = intent.getParcelableExtra<Product?>("product")

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = product?.status
        }
        collapsing_toolbar.scrimAnimationDuration = 200
        collapsing_toolbar.setExpandedTitleColor(Color.parseColor("#00FFFFFF"))

        title_text.text = product?.product_name
        tv_company.text = product?.company?.company_name
        tv_status.text = product?.status
        tv_country.text = product?.country
        notes_text.text = product?.company?.notes

        bg_status.setBackgroundResource(
            when (product?.red_yellow_green?.toLowerCase()) {
                "red" -> R.drawable.bg_red
                "yellow" -> R.drawable.bg_yellow
                "green" -> R.drawable.bg_green
                else -> android.R.color.transparent
            }
        )

        img_drink_icon.setBackgroundResource(
            when (product?.booze_type?.toLowerCase()) {
                "beer" -> R.drawable.ic_beer
                "wine" -> R.drawable.ic_wine
                "liquor" -> R.drawable.ic_liquor
                else -> 0
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onBackPressed() = finish()

    companion object {

        fun newIntent(context: Context, product: Product) =
            Intent(context, ProductActivity::class.java).apply {
                putExtra("product", product)
            }
    }
}
