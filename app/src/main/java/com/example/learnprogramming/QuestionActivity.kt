package com.example.learnprogramming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.learnprogramming.databinding.ActivityQuestionBinding
import kotlin.math.log

//class QuestionActivity : AppCompatActivity() {

//    lateinit var binding:ActivityQuestionBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityQuestionBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnNext.setOnClickListener {
//
//            val dialog :AlertDialog.Builder = AlertDialog.Builder(this)
//                .setTitle("Submit Exam")
//                .setMessage("Are you sure you want to submit the exam!")
//                .setCancelable(false)
//
//            dialog.setPositiveButton("Yes"){
//                dialog,which->
//               startActivity(Intent(this,ExamSubmittedActivity::class.java))
//                finish()
//            }
//
//            dialog.setNegativeButton("No"){
//                dialog,which ->
//                dialog.cancel()
//            }
//
//            val alerDialog:AlertDialog = dialog.create()
//            alerDialog.show()
//        }
//    }

//
//    override fun onBackPressed() {
//      //  super.onBackPressed()
//        showExitConfirmationDialog()
//    }
//    private fun showExitConfirmationDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Submit Exam")
//        builder.setMessage("Are you sure you want to submit the exam!")
//        builder.setPositiveButton("Yes") { dialog, _ ->
//          //  dialog.dismiss()
//            startActivity(Intent(this,ExamSubmittedActivity::class.java))
//            finish()
//
//        }
//        builder.setNegativeButton("No") { dialog, _ ->
//            dialog.dismiss()
//        }
//        val dialog = builder.create()
//        dialog.show()
//    }
//}
//----------------------------------------------------------------------------

class QuestionActivity : AppCompatActivity() {
    // ...
    lateinit var binding:ActivityQuestionBinding
    private lateinit var countDownTimer: CountDownTimer
    private val initialTimeInMillis: Long = 3600000 // 60 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize your CountDownTimer
        countDownTimer = object : CountDownTimer(initialTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update your timer display (e.g., TextView)
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeText = String.format("%02d:%02d", minutes, seconds)
                binding.time.text = "Time Left: $timeText"
            }

            override fun onFinish() {
            
                binding.time.text = "Time's up!"
                submitExam()
            }
        }

        countDownTimer.start()

        binding.btnNext.setOnClickListener {
            // Show the exam submission confirmation dialog
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Submit Exam")
        builder.setMessage("Are you sure you want to submit the exam!")
        builder.setPositiveButton("Yes") { dialog, _ ->
            //  dialog.dismiss()
            startActivity(Intent(this,ExamSubmittedActivity::class.java))
            finish()

        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun submitExam() {

        startActivity(Intent(this, ExamSubmittedActivity::class.java))
        finish()
    }
}


