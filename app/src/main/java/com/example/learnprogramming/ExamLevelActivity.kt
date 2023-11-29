package com.example.learnprogramming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.learnprogramming.databinding.ActivityExamLevelBinding
import com.example.learnprogramming.databinding.InstructionPageBinding

class ExamLevelActivity : AppCompatActivity() {
    lateinit var binding : ActivityExamLevelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

     //   opennewActivity()
    }

    private fun opennewActivity(){
        binding.buttonStart.setOnClickListener {
          val view:View = LayoutInflater.from(this).inflate(R.layout.instruction_page,null)
            var bindingInstruction: InstructionPageBinding = InstructionPageBinding.bind(view)
            val dialog:AlertDialog = AlertDialog.Builder(this@ExamLevelActivity)
                .setView(view)
                .create()

            bindingInstruction.btnContinue.setOnClickListener {
                startActivity(Intent(this,QuestionActivity::class.java))
                finish()
            }

             dialog.show()
        }
    }

    override fun onBackPressed() {
       onBackPressedDispatcher.onBackPressed()
    }

}