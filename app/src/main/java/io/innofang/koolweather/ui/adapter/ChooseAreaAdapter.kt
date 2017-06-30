package io.innofang.koolweather.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Author: Inno Fang
 * Time: 2017/6/26 17:06
 * Description:
 */

class ChooseAreaAdapter(var list: List<String>): RecyclerView.Adapter<ChooseAreaAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent!!.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.bindHolder(list[position])
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView by lazy { itemView.findViewById(android.R.id.text1) as TextView }

        fun bindHolder(text: String) {
            itemTextView.text = text
        }
    }

}