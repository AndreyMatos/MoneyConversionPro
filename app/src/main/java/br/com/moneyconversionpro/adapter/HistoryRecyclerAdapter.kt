package br.com.moneyconversionpro.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.moneyconversionpro.model.Conversion
import java.util.*

class HistoryRecyclerAdapter :
    RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>(), Filterable {
    private var conversions: List<Conversion>? = null
    private var conversionsFiltered: List<Conversion>? = null
    private val text2Color = Color.parseColor("#8492A6")

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text1: TextView = itemView.findViewById(android.R.id.text1)
        val text2: TextView = itemView.findViewById(android.R.id.text2)
    }

    fun setConversions(c: ArrayList<Conversion>) {
        conversions = c
        conversionsFiltered = conversions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(android.R.layout.simple_list_item_2, parent, false))
    }

    override fun getItemCount(): Int {
        return if (conversionsFiltered == null) 0 else conversionsFiltered!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conversion = conversionsFiltered?.get(position)
        holder.text1.text = conversion?.fromCurrency
        holder.text2.text = conversion?.toCurrency
        holder.text2.setTextColor(text2Color)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(q: CharSequence?): FilterResults {
                val queryString = q?.toString()?.toLowerCase(Locale.getDefault())
                val filterResult = FilterResults()
                filterResult.values = if (queryString == null || queryString.isEmpty())
                    conversions
                else
                    conversions?.filter {
                        it.fromCurrency.toLowerCase(Locale.getDefault()).contains(queryString) ||
                                it.toCurrency.toLowerCase(Locale.getDefault()).contains(queryString)
                    }
                return filterResult
            }

            override fun publishResults(q: CharSequence?, filterResults: FilterResults?) {
                conversionsFiltered = filterResults?.values as List<Conversion>?
                notifyDataSetChanged()
            }

        }
    }
}