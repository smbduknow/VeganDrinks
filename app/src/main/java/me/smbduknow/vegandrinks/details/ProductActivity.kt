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
import me.smbduknow.vegandrinks.feature.search.domain.model.Product

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT

        val product = intent.getSerializableExtra("product") as? Product

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = product?.status?.name
        }
        collapsing_toolbar.scrimAnimationDuration = 200
        collapsing_toolbar.setExpandedTitleColor(Color.parseColor("#00FFFFFF"))
        collapsing_toolbar.setContentScrimResource(product?.status.statusToColor())
        collapsing_toolbar.setStatusBarScrimResource(product?.status.statusToColor())

        title_text.text = product?.name
        tv_company.text = product?.company?.name
        tv_status.text = product?.status?.name
        tv_country.text = product?.country
        notes_text.text = product?.company?.notes

        bg_status.setBackgroundResource(
            when (product?.status) {
                Product.Status.NOT_VEGAN -> R.drawable.bg_red
                Product.Status.UNKNOWN -> R.drawable.bg_yellow
                Product.Status.VEGAN -> R.drawable.bg_green
                else -> android.R.color.transparent
            }
        )

        img_drink_icon.setBackgroundResource(
            when (product?.type) {
                Product.Type.BEER -> R.drawable.ic_beer
                Product.Type.WINE -> R.drawable.ic_wine
                Product.Type.LIQUOR -> R.drawable.ic_liquor
                else -> 0
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onBackPressed() = finish()

    private fun Product.Status?.statusToColor() = when (this) {
        Product.Status.NOT_VEGAN -> R.color.red_medium
        Product.Status.UNKNOWN -> R.color.yellow_medium
        Product.Status.VEGAN -> R.color.green_medium
        else -> android.R.color.transparent
    }

    companion object {

        fun newIntent(context: Context, product: Product) =
            Intent(context, ProductActivity::class.java).apply {
                putExtra("product", product)
            }
    }
}
