package com.android.sunshineapp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.sunshineapp.DateUtils
import com.android.sunshineapp.R
import com.android.sunshineapp.data.database.WeatherEntry
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.forecast_list_item.view.*


class WeatherAdapter(private val context: Context) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {
    private var list: List<WeatherEntry> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): WeatherHolder {
        val view =
            LayoutInflater.from(context.applicationContext).inflate(R.layout.forecast_list_item, parent, false) as View
        return WeatherHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val model = list[position]
        holder.tvDesc.text = model.description
        holder.tvMax.text = String.format(context.getString(R.string.format_temperature), model.max)
        holder.tvMin.text = String.format(context.getString(R.string.format_temperature), model.min)
        Glide.with(context.applicationContext).load("https://api.openweathermap.org/img/w/${model.weatherIcon}")
            .into(holder.img)
        val date = DateUtils.getFriendlyDateString(context, model.date * 1000, false)
        holder.tvToday.text = date


    }

    class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvToday = itemView.item_today as TextView
        val tvDesc = itemView.item_weather_description as TextView
        val tvMax = itemView.item_high_temperature as TextView
        val tvMin = itemView.item_ow_temperature as TextView
        val img = itemView.item_weather_icon as ImageView
    }

    fun setWord(word: List<WeatherEntry>) {
        list = word
        notifyDataSetChanged()
    }
}