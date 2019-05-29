package me.smbduknow.vegandrinks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_search_result.view.*
import me.smbduknow.vegandrinks.data.model.Product

class DrinkListAdapter : RecyclerView.Adapter<DrinkListAdapter.ViewHolder>() {

    var items = emptyList<Product>()
    var onItemClickListener: ((Product) -> Unit)? = null

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view) {
            onItemClickListener?.invoke(items[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val item = items[pos]
        holder.titleView.text = item.product_name
        holder.companyText.text = "by ${item.company?.company_name}"
        holder.statusLabel.setBackgroundResource(
            when (item.red_yellow_green.toLowerCase()) {
                "red" -> R.drawable.bg_red
                "yellow" -> R.drawable.bg_yellow
                "green" -> R.drawable.bg_green
                else -> android.R.color.transparent
            }
        )
        holder.iconView.setBackgroundResource(
            when (item.booze_type.toLowerCase()) {
                "beer" -> R.drawable.ic_beer
                "wine" -> R.drawable.ic_wine
                "liquor" -> R.drawable.ic_liquor
                else -> 0
            }
        )
    }

    class ViewHolder(itemView: View, clickListener: (pos: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val titleView: TextView by lazy { itemView.tv_title }
        val companyText: TextView by lazy { itemView.tv_company }
        val statusLabel: View by lazy { itemView.drink_status }
        val iconView: ImageView by lazy { itemView.img_drink_icon }

        init {
            itemView.setOnClickListener { clickListener(adapterPosition) }
        }
    }
}