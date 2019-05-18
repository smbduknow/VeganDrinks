package me.smbduknow.vegandrinks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.smbduknow.vegandrinks.data.model.Product

class DrinkListAdapter : RecyclerView.Adapter<DrinkListAdapter.ViewHolder>() {

    var items = emptyList<Product>()
    var onItemClickListener: ((Product) -> Unit)? = null

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view) {
            onItemClickListener?.invoke(items[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.cityTextView.text = "${items[pos].product_name} - ${items[pos].status}"
    }

    class ViewHolder(itemView: View, clickListener: (pos: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val cityTextView: TextView
            get() = itemView as TextView

        init {
            itemView.setOnClickListener { clickListener(adapterPosition) }
        }
    }
}