package com.harsh.discordclone.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harsh.discordclone.R
import com.harsh.discordclone.model.CountryCode
import com.harsh.discordclone.util.CustomDataCallback

class CountryCodeRecyclerViewAdapter() :
    RecyclerView.Adapter<CountryCodeRecyclerViewAdapter.CountryCodeRecyclerViewHolder>() {

    private var list: List<CountryCode> = ArrayList()
    private var listener = CustomDataCallback<CountryCode>{}
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CountryCodeRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_code_item_view, parent, false)
        val holder = CountryCodeRecyclerViewHolder(view)

        view.setOnClickListener {
            listener.onCallback(list[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: CountryCodeRecyclerViewHolder, position: Int) {
        val curr = list[position]
        holder.countryName.text = curr.name
        holder.countryDialCode.text = "+${curr.dialCode}"
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CountryCode>){
        this.list = data
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: CustomDataCallback<CountryCode>): CountryCodeRecyclerViewAdapter {
        this.listener = listener
        return this
    }

    inner class CountryCodeRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.country_name)
        val countryDialCode: TextView = itemView.findViewById(R.id.country_dial_code)
    }

}