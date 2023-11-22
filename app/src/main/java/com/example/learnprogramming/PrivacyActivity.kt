package com.example.learnprogramming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.learnprogramming.databinding.ActivityPrivacyBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class PrivacyActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrivacyBinding

    private lateinit var tILEmail: TextInputLayout
    private lateinit var tILPassword: TextInputLayout
    private lateinit var fABEdit: FloatingActionButton
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }
    private fun initActivity() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
           // startActivity(Intent(this, HomeActivity::class.java))
        }

        tILEmail = findViewById(R.id.til_email)
        tILPassword = findViewById(R.id.til_password)
        fABEdit = findViewById(R.id.btnedit)
        btnSave = findViewById(R.id.btn_save)
        val editable = tILEmail.isEnabled
        binding.btnedit.setOnClickListener {

            tILEmail.isEnabled = !editable
            tILPassword.isEnabled = !editable

            if (!editable){
                btnSave.visibility = View.VISIBLE
                fABEdit.visibility = View.INVISIBLE
            }
            else{
                fABEdit.visibility = View.INVISIBLE
            }
        }
        binding.btnSave.setOnClickListener {
//            btnSave.visibility = View.INVISIBLE
            tILEmail.isEnabled = editable
            tILPassword.isEnabled = editable

            if (!editable){
                btnSave.visibility = View.INVISIBLE
                fABEdit.visibility = View.VISIBLE
            }
        }

    }
}