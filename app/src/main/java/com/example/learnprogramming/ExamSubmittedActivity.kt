package com.example.learnprogramming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.learnprogramming.databinding.ActivityExamSubmittedBinding
import com.example.learnprogramming.databinding.FeedbackBinding


class ExamSubmittedActivity : AppCompatActivity() {
    lateinit var binding:ActivityExamSubmittedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamSubmittedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnFeedback.setOnClickListener {

            val view : View = LayoutInflater.from(this).inflate(R.layout.feedback,null)
            var binding:FeedbackBinding = FeedbackBinding.bind(view)
            val dialog:AlertDialog = AlertDialog.Builder(this)
                .setView(view)
                .create()

            binding.btnSubmit.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            dialog.show()

        }

        binding.btnHome.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }





}