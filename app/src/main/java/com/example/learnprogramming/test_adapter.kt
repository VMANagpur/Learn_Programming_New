package com.example.learnprogramming

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.learnprogramming.databinding.DesignMockItemBinding
import com.example.learnprogramming.databinding.InstructionPageBinding
import com.example.learnprogramming.model.MockTestItemItem
import com.example.learnprogramming.model.TestDetailsResponse
import com.example.learnprogramming.retrofit.CallingApi
import retrofit2.Call
import retrofit2.Response


class test_adapter(private var itemList:List<MockTestItemItem>) : RecyclerView.Adapter<test_adapter.ViewHolder>() {

    inner class ViewHolder(private  val binding: DesignMockItemBinding) : RecyclerView.ViewHolder(binding.root){

//        init {
//            binding.root.setOnClickListener {
          //   val item = itemList[adapterPosition]
//              //  val intent = Intent(binding.root.context, ExamLevelActivity::class.java)
////                intent.putExtra("mockTestNo", item.mockTest_no)
////                intent.putExtra("description", item.description)
//            //    binding.root.context.startActivity(intent)
//
//            }
//        }
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

        holder.itemView.setOnClickListener {

            val id = item.id
            Log.d("mocktestid", "onBindViewHolder: "+id)

            try {

                val callingApi: CallingApi = CallingApi
                val data = callingApi.MockTestApi.getPaperSetDetails(id)
                data.enqueue(object : retrofit2.Callback<TestDetailsResponse> {
                    override fun onResponse(
                        call: Call<TestDetailsResponse>,
                        response: Response<TestDetailsResponse>
                    ) {
                        if (response.isSuccessful) {
                            val testDetails: TestDetailsResponse? = response.body()

                            Log.d("mocktestid", "onBindViewHolder: " + testDetails.toString())

                            val totalmark = testDetails?.totalMarks.toString()
                            val time = testDetails?.totalTime.toString()
                            val noOfQue = testDetails?.noOfQuestions.toString()
                            val posMark = testDetails?.positiveMarking.toString()
                            var negMark = testDetails?.negativeMarking.toString()
                             val mockTestId = testDetails?.mockTestId.toString()


                            val view: View = LayoutInflater.from(holder.itemView.context)
                                .inflate(R.layout.instruction_page, null)
                            var bindingInstruction: InstructionPageBinding =
                                InstructionPageBinding.bind(view)
                            val dialog: AlertDialog = AlertDialog.Builder(holder.itemView.context)
                                .setView(view)
                                .create()

                            bindingInstruction.marks.setText(totalmark)
                            bindingInstruction.time.setText(time)
                            bindingInstruction.question.setText(noOfQue)
                            bindingInstruction.correctAns.setText(posMark)
                            if (negMark.contains("-")==false)
                            {
                                negMark = "-" + negMark
                            }
                            bindingInstruction.incorrectAns.setText(negMark)

                            bindingInstruction.btnContinue.setOnClickListener {

                                val intent =
                                    Intent(holder.itemView.context, QuestionActivity::class.java)
                                intent.putExtra("time", time)
                                intent.putExtra("marks", totalmark)
                                intent.putExtra("Que", noOfQue)
                                intent.putExtra("posMark", posMark)
                                intent.putExtra("negMark", negMark)
                                intent.putExtra("MockTestId",mockTestId)
                                Log.d("MockTestID", "IntentPassByValue "+mockTestId)
                               // holder.itemView.context.startActivity(intent)
                                val sharedPreferences = holder.itemView.context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                                val userId = sharedPreferences.getLong("userId", 0)

                                try {
                                    val callinApi = CallingApi
                                    val data = callinApi.MockTestApi.userEligibleForDayTest(
                                        userId,
                                        mockTestId.toLong()
                                    )
                                    data.enqueue(object : retrofit2.Callback<String> {
                                        override fun onResponse(
                                            call: Call<String>,
                                            response: Response<String>
                                        ) {
                                            if (response.isSuccessful) {
                                                if (response != null)
                                                {
                                                    if (response.body().equals("user limit for day is completed"))
                                                    {
                                                        val reachLimitForAttempt = AlertDialog.Builder(holder.itemView.context)
                                                            .setTitle("Attempt Limit Reached")
                                                            .setMessage("You can attempt this paper only three times in one day. Your limit for the day has expired.")
                                                            .setCancelable(false)
                                                        reachLimitForAttempt.setPositiveButton("Ok"){
                                                             dialog,_ ->
                                                             dialog.dismiss()
                                                         }

                                                        reachLimitForAttempt.create().show()
                                                    }
                                                    else if (response.body().equals("sorry,paper is not exit"))
                                                    {
                                                        val paperNotExit = AlertDialog.Builder(holder.itemView.context)
                                                            .setTitle("Exit")
                                                            .setMessage("Sorry,For Some Reason Paper is will deleted")
                                                            .setCancelable(false)
                                                        paperNotExit.setPositiveButton("Ok"){
                                                                dialog,_ ->
                                                            dialog.dismiss()
                                                        }

                                                        paperNotExit.create().show()
                                                    }
                                                    else if (response.body().equals("user will eligible for test"))
                                                    {
                                                        holder.itemView.context.startActivity(intent)

                                                        // Finish the hosting activity (fragment's activity)
                                                        if (holder.itemView.context is FragmentActivity) {
                                                            (holder.itemView.context as FragmentActivity).finish()
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    val paperNotExit = AlertDialog.Builder(holder.itemView.context)
                                                        .setTitle("Exit")
                                                        .setMessage("Sorry,Their is problem in server please try after sometime")
                                                        .setCancelable(false)
                                                    paperNotExit.setPositiveButton("Ok"){
                                                            dialog,_ ->
                                                        dialog.dismiss()
                                                    }

                                                    paperNotExit.create().show()
                                                }

                                            }
                                        }

                                        override fun onFailure(call: Call<String>, t: Throwable) {
                                             Toast.makeText(holder.itemView.context,"Problem in server please try after somtime",Toast.LENGTH_SHORT).show()
                                        }

                                    })
                                }
                                catch (e :Exception)
                                {
                                    Log.d("problem", "Api not correctly integrated ${e.toString()}")
                                }



                                dialog.dismiss()
                            }

                            dialog.show()

                        } else if (response.code() == 404) {
                            Log.d("mocktestid", "onResponse: " + response.body())
                            val dialog = AlertDialog.Builder(holder.itemView.context)
                                .setTitle("Not Exit")
                                .setMessage("Sorry the teachecher not set the paper")
                                .create()
                            dialog.show()

                        }

                    }

                    override fun onFailure(call: Call<TestDetailsResponse>, t: Throwable) {
                        Toast.makeText(
                            holder.itemView.context,
                            "Server Problem" + t,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("problem" + t, "onFailure: ")

                    }
                })
            }
            catch (e : Exception)
            {
                Log.d("responseOfMockDetailFailed", "onBindViewHolder: "+e)
            }



        }

    }

    fun setData(newData: List<MockTestItemItem>)
    {
        itemList = newData
        notifyDataSetChanged()
    }


}