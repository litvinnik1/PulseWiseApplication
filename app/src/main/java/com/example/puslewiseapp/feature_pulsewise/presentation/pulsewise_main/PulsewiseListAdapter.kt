package com.example.puslewiseapp.feature_pulsewise.presentation.pulsewise_main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.puslewiseapp.MainActivity
import com.example.puslewiseapp.R
import com.example.puslewiseapp.feature_pulsewise.data.local.dto.LocalPulsewiseItem

@SuppressLint("NotifyDataSetChanged")
class PulsewiseListAdapter(
    private val context:Context,
    val listener: MainActivity
) : RecyclerView.Adapter<PulsewiseListAdapter.ItemViewHolder>(){


    private val pulsewiseItemsList = ArrayList<LocalPulsewiseItem>()
    private val fullList = ArrayList<LocalPulsewiseItem>()
    private val limit = 3

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val pulsewiseItemLayout = view.findViewById<CardView>(R.id.pulsewiseitem_layout)
        val systolic = view.findViewById<TextView>(R.id.systolicAmountTv)
        val diastolic = view.findViewById<TextView>(R.id.diastolicAmountTv)
        val pulse = view.findViewById<TextView>(R.id.pulseAmountTv)
        val date = view.findViewById<TextView>(R.id.dateAmountTv)
        val time = view.findViewById<TextView>(R.id.timeAmountTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.pulsewiseitem_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        if(pulsewiseItemsList.size > limit){
            return limit
        } else {
            return pulsewiseItemsList.size
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var currentPulsewiseItem = pulsewiseItemsList[position]
        holder.systolic.text = currentPulsewiseItem.systolic.toString()
        holder.diastolic.text = currentPulsewiseItem.diastolic.toString()
        holder.pulse.text = currentPulsewiseItem.pulse.toString()
        holder.date.text = currentPulsewiseItem.date
        holder.time.text = currentPulsewiseItem.time

        holder.pulsewiseItemLayout.setOnClickListener {
            listener.onItemClicked(pulsewiseItemsList[holder.adapterPosition])
        }

        holder.pulsewiseItemLayout.setOnLongClickListener {
            listener.onLongItemClicked(pulsewiseItemsList[holder.adapterPosition], holder.pulsewiseItemLayout)
            true
        }
    }
    fun updateList(newList: List<LocalPulsewiseItem>){
        fullList.clear()
        fullList.addAll(newList)

        pulsewiseItemsList.clear()
        pulsewiseItemsList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(){
        pulsewiseItemsList.clear()
        for(item in fullList){
            pulsewiseItemsList.add(item)
        }
        notifyDataSetChanged()
    }

    interface PulsewiseClickListener {
        fun onItemClicked(pulsewiseItem: LocalPulsewiseItem)
        fun onLongItemClicked(pulsewiseItem: LocalPulsewiseItem, cardView: CardView)
    }
}