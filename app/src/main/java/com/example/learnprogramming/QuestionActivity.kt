package com.example.learnprogramming

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.learnprogramming.databinding.ActivityQuestionBinding
import com.example.learnprogramming.model.Answer
import com.example.learnprogramming.model.NoOfQuestionItem
import com.example.learnprogramming.model.Option
import com.example.learnprogramming.model.UserSubmittedExam
import com.example.learnprogramming.retrofit.CallingApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuestionActivity : AppCompatActivity() {
    // ...
    lateinit var binding:ActivityQuestionBinding
    lateinit var countDownTimer: CountDownTimer
    private var currentQuestionIndex = 0
    private lateinit var question: List<NoOfQuestionItem>
    private lateinit var option:List<Option>
    private val userAnswer:MutableMap<Long,Long?> = mutableMapOf()
    private lateinit var listOfuserAnswer:List<Answer>
    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressbar.visibility = View.VISIBLE

        prevbtnVisibility(currentQuestionIndex)



        val totalMark = intent.getStringExtra("marks")
     //   val time = intent.getStringExtra("time")
        val noOfQues = intent.getStringExtra("Que")
        val posMark = intent.getStringExtra("posMark")
        val negMark = intent.getStringExtra("negMark")


        countdowntimer()

        Log.d("valueOfIntent", "Marks= "+totalMark+"Ques = "+noOfQues+"posMark = "+posMark+"negMark = "+negMark)

        binding.marks.setText("+"+posMark)
        binding.negativeMark.setText(negMark)
        binding.totalQues.setText(noOfQues)

        val mockTestId = intent.getStringExtra("MockTestId")
        Log.d("mockId", "onCreate: "+mockTestId)


        try {
            val id = mockTestId.toString().toLong()
            val callingApi = CallingApi
            val response = callingApi.QuestionApi.getAllMockTestQuestion(id)
          response.enqueue(object :Callback<List<NoOfQuestionItem>>{
              override fun onResponse(
                  call: Call<List<NoOfQuestionItem>>,
                  response: Response<List<NoOfQuestionItem>>
              ) {
                  if (response.isSuccessful)
                  {
                      binding.progressbar.visibility = View.GONE
                      if (response !=null) {
                          Log.d("responseQues", "onResponse: " + response.body())
                          question = response.body()!!
                          displayQuestionAndOption()
                      }

                  }

              }

              override fun onFailure(call: Call<List<NoOfQuestionItem>>, t: Throwable) {
                  Log.d("exception", "onFailure: "+t.toString())
              }

          })


        }
        catch (e:Exception)
        {
            Log.d("exception", "onCreate: "+e.toString())
              Toast.makeText(this,"exception"+e.toString(),Toast.LENGTH_SHORT).show()
        }



        binding.btnNext.setOnClickListener {
            binding.radioGroup.clearCheck()
            binding.radioGroup.clearCheck()
            if (currentQuestionIndex<question.size-1)
            {
                     currentQuestionIndex++
                Log.d("questionIndex", "index "+currentQuestionIndex)
                displayQuestionAndOption()
                prevbtnVisibility(currentQuestionIndex)

            }
            else
            {

//                // Create a list of Answers (option ID, question ID)
//                listOfuserAnswer = userAnswer.entries.map { entry ->
//                    Answer(optionId = entry.value ?: -1, questionId = entry.key)
//                }
//
//                Log.d("optionId", "allAnswer $listOfuserAnswer")

                showExitConfirmationDialog()

            }
        }
        try {
            binding.btnPrevious.setOnClickListener {
                binding.radioGroup.clearCheck()
                currentQuestionIndex--
                displayQuestionAndOption()
                prevbtnVisibility(currentQuestionIndex)
            }
        }
        catch (e:Exception)
        {
            Log.d("prev", "Exception: "+e.toString())
        }

        try {
            binding.btnReset.setOnClickListener {

                binding.radioGroup.clearCheck()
            }
        }
        catch (e:Exception)
        {
            Log.d("recetException", "resetexception: "+e.toString())
        }

    }

    private fun showExitConfirmationDialog() {
        try {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Submit Exam")
            builder.setMessage("Are you sure you want to submit the exam!")
            builder.setPositiveButton("Yes") { dialog, _ ->

                // Create a list of Answers (option ID, question ID)
                listOfuserAnswer = userAnswer.entries.map { entry ->
                    Answer(optionId = entry.value ?: -1, questionId = entry.key)
                }

                Log.d("optionId", "allAnswer $listOfuserAnswer")
                //  dialog.dismiss()
                countDownTimer.cancel()
                submitUserExam()
                submitExam()

            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
        catch (e:Exception)
        {
            Log.d("submitExam", "problem in submit the exam")
        }
    }

    private fun submitExam() {

        startActivity(Intent(this, ExamSubmittedActivity::class.java))
        finish()
    }

    private fun countdowntimer()
    {
        val time = intent.getStringExtra("time").toString()

        //convert value to millis second
        val totalMillis = convertTimerToMillis(time)
        Log.d("time", "countdowntimer: "+totalMillis)

        countDownTimer = object : CountDownTimer(totalMillis,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                updateCountdownText(millisUntilFinished)
            }

            override fun onFinish() {
                listOfuserAnswer = userAnswer.entries.map { entry ->
                    Answer(optionId = entry.value ?: -1, questionId = entry.key)
                }

                Log.d("optionId", "allAnswer $listOfuserAnswer")
                submitUserExam()

                startActivity(Intent(this@QuestionActivity,ExamSubmittedActivity::class.java))
                finish()

            }
        }
        countDownTimer.start()
    }

    //function for convert the time to millisecond
    private fun convertTimerToMillis(time: String) : Long{

        val timeCount = time.split(":")
        val hour = timeCount[0].toInt()
        val minute = timeCount[1].toInt()
        val second = timeCount[2].toInt()

        val l = (hour*60*60+minute*60+second)*1000L
        return  l
        Log.d("timeinMillis"+l, "convertTimerToMillis: ")
    }

    private fun updateCountdownText(miliUntillFinish:Long){

        val hour = miliUntillFinish/(1000*60*60)
        val minute = (miliUntillFinish % (1000*60*60))/(1000*60)
        val second = (miliUntillFinish%(1000*60))/1000

        binding.time.text = String.format("%02d:%02d:%02d",hour,minute,second)

    }

    private fun displayQuestionAndOption()
    {
        val currentQustion = question[currentQuestionIndex]
        binding.Question.text = "Q] "+currentQustion.question
        val questionNow = currentQuestionIndex+1
        binding.questionNow.text = questionNow.toString()

        val id = currentQustion.id

        option = currentQustion.options
        try {
             binding.option1.text = option[0].optionText
             binding.option2.text = option[1].optionText
             binding.option3.text=option[2].optionText
             binding.option4.text=option[3].optionText
        }
        catch (e:Exception)
        {
            Log.d("errorInOption", "OptionDisplay "+e.toString())
        }

          radioClickButton(id,option)

    }

    private fun prevbtnVisibility(QuestionIndex:Int)
    {
        if (QuestionIndex==0)
        {
            binding.btnPrevious.visibility = View.GONE
        }
        else
        {
            binding.btnPrevious.visibility = View.VISIBLE
        }
    }

    private fun radioClickButton(questionId:Long,option: List<Option>)  {

        userAnswer[questionId] = null

        try {
           binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->

                // Check if checkedId is not null
                if (checkedId != -1) {
                    val selectedOption: RadioButton = findViewById(checkedId)

                    // Check if selectedRadioButton is not null
                    selectedOption.let {
                        val selectedOptionText = selectedOption.text.toString()

                        for (element in option)
                        {
                            val optionText = element.optionText
                            if (optionText == selectedOptionText)
                            {
                                userAnswer[questionId]=element.id
                                Log.d("optionId", "option id ${element.id}",)

                            }

                        }

                        // Log.d("optionId", "option id $selectedOption")
                        Log.d("optionId", "option text $selectedOptionText ")

                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("errorInOptionget", "failed ${e.toString()}")
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }


    }

    private fun submitUserExam() {
        binding.progressbar.visibility = View.VISIBLE

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getLong("userId", 0)
        val mockTestId = intent.getStringExtra("MockTestId")
        val mockid = mockTestId.toString().toLong()


        val userSubmittedExam = UserSubmittedExam(listOfuserAnswer, mockid, userId)

        Log.d("userExamSubmit", "submitUserExam: "+userSubmittedExam)

        try {

            val callApi = CallingApi
            val submitExam = callApi.submitExamByUser.submitExam(userSubmittedExam)
            submitExam.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful)
                    {
                        Log.d("userExamSubmit", "onResponse: ")
                        binding.progressbar.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("userExamSubmit", "onFailure: ")
                }

            })

        }
        catch (e : Exception)
        {
            Log.d("submit", "catch error ")
        }
    }

    override fun onBackPressed() {
        showExitConfirmationDialog()
    }


}


