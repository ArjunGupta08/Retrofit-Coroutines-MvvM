package com.arjun.retrofitcoroutinesmvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arjun.retrofitcoroutinesmvvm.model.Result
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class Adapter(val context: Context, val list: List<Result>) : RecyclerView.Adapter<Adapter.viewHolder>() {

    class viewHolder(itemView: View) : ViewHolder(itemView){
        val text1 = itemView.findViewById<TextView>(R.id.text1)
        val text2 = itemView.findViewById<TextView>(R.id.text2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_list,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val item = list[position]
        holder.text1.text = "Author name: ${item.author}"
        holder.text2.text = "Quote: ${item.content}"
    }
}