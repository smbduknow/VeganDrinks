package me.smbduknow.vegandrinks.feature.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search_result.view.*
import me.smbduknow.vegandrinks.feature.search.domain.model.Product

internal class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    var items = emptyList<Product>()
    var onItemClickListener: ((Product) -> Unit)? = null

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view) {
            onItemClickListener?.invoke(items[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val item = items[pos]
        holder.titleView.text = item.name
        holder.companyText.text = "by ${item.company?.name}"
        holder.statusText.text = item.status.name
        holder.statusText.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                when (item.status) {
                    Product.Status.VEGAN -> R.color.green_dark
                    Product.Status.NOT_VEGAN -> R.color.red_dark
                    Product.Status.UNKNOWN -> R.color.yellow_dark
                }
            )
        )
        holder.statusLabel.setBackgroundResource(
            when (item.status) {
                Product.Status.VEGAN -> R.drawable.bg_green
                Product.Status.NOT_VEGAN -> R.drawable.bg_red
                Product.Status.UNKNOWN -> R.drawable.bg_yellow
            }
        )
        holder.iconView.setBackgroundResource(
            when (item.type) {
                Product.Type.BEER -> R.drawable.ic_beer
                Product.Type.WINE -> R.drawable.ic_wine
                Product.Type.LIQUOR -> R.drawable.ic_liquor
                Product.Type.OTHER -> 0
            }
        )
    }

    class ViewHolder(
        override val containerView: View,
        clickListener: (pos: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        val titleView: TextView by lazy { itemView.tv_title }
        val companyText: TextView by lazy { itemView.tv_company }
        val statusText: TextView by lazy { itemView.tv_drink_status }
        val statusLabel: View by lazy { itemView.drink_status }
        val iconView: ImageView by lazy { itemView.img_drink_icon }

        init {
            itemView.setOnClickListener { clickListener(adapterPosition) }
        }
    }
}
