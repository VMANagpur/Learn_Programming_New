package com.example.learnprogramming

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.learnprogramming.databinding.DesignMockItemBinding
import com.example.learnprogramming.databinding.InstructionPageBinding
import com.example.learnprogramming.model.MockTestItem
import com.example.learnprogramming.model.MockTestItemItem

class test_adapter(private var itemList:List<MockTestItemItem>) : RecyclerView.Adapter<test_adapter.ViewHolder>() {

    inner class ViewHolder(private  val binding: DesignMockItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
             //   val item = itemList[adapterPosition]
                val intent = Intent(binding.root.context, ExamLevelActivity::class.java)
//                intent.putExtra("mockTestNo", item.mockTest_no)
//                intent.putExtra("description", item.description)
                binding.root.context.startActivity(intent)

            }
        }
        fun bind(item: MockTestItemItem){
//            binding.mockTestNo.text = item.mockTest_no
//            binding.description.text = item.description

            binding.mockTestNo.text = item.title
            binding.description.text = item.description

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DesignMockItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

    }

    fun setData(newData: List<MockTestItemItem>)
    {
        itemList = newData
        notifyDataSetChanged()
    }
}