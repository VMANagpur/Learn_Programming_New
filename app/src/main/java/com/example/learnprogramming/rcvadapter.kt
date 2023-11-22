package com.example.learnprogramming

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class rcvadapter (val requiredContext : Context, private val phoneDetails : ArrayList<phonedetailsClass>) :
RecyclerView.Adapter<rcvadapter.phoneDetailsViewHolder>() {
    class phoneDetailsViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val personName : TextView = itemView.findViewById(R.id.item_txt1)
        val personNumber : TextView = itemView.findViewById(R.id.item_txt2)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): rcvadapter.phoneDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_items,parent, false)
        return phoneDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: rcvadapter.phoneDetailsViewHolder, position: Int) {
      holder.personName.text = phoneDetails[position].personName
        holder.personNumber.text = phoneDetails[position].personNumber.toString()
        holder.itemView.setOnClickListener {
            requiredContext.startActivity(Intent(requiredContext,ExamLevelActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
       return phoneDetails.size
    }



}