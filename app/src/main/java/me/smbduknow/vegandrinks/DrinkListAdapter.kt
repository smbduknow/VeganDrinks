package me.smbduknow.vegandrinks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.statusText.text = item.status
    }

    class ViewHolder(itemView: View, clickListener: (pos: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val titleView: TextView by lazy { itemView.title_text }
        val statusText: TextView by lazy { itemView.status_text }

        init {
            itemView.setOnClickListener { clickListener(adapterPosition) }
        }
    }
}