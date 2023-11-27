package com.example.learnprogramming

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.learnprogramming.databinding.ActivityPrivacyBinding
import com.example.learnprogramming.model.UserPrivacy
import com.example.learnprogramming.model.UserProfile
import com.example.learnprogramming.retrofit.CallingApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        displayUserDataFromSharedPreferences()
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

            saveUserDataToSharedPreferences()
        }

    }

    private fun displayUserDataFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val userEmail = sharedPreferences.getString("user_email", "")
        val userPassword = sharedPreferences.getString("user_password", "")

        binding.etEmail.setText(userEmail)
        binding.etPassword.setText(userPassword)

    }

    private fun saveUserDataToSharedPreferences()
    {

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val id = sharedPreferences.getLong("userId",0)


        try {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val user = UserPrivacy(email,password)

            val calling = CallingApi
            val data = calling.userPrivacyUpdate.updatePrivacy(id, user)
            data.enqueue(object : Callback<UserPrivacy> {
                override fun onResponse(call: Call<UserPrivacy>, response: Response<UserPrivacy>) {
                    if (response.isSuccessful) {
                        Log.d("response", "onResponse: " + response.body())
                    }
                }

                override fun onFailure(call: Call<UserPrivacy>, t: Throwable) {
                    Log.d("failed", "onFailure: $t")
                }

            })
        }
        catch (e:Exception)
        {
            Log.d("exception", "saveUserDataToSharedPreferences: $e")
        }
    }
}