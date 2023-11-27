package com.example.learnprogramming

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.learnprogramming.databinding.ActivityPersonalDetailsBinding
import com.example.learnprogramming.model.UserProfile
import com.example.learnprogramming.retrofit.CallingApi
import com.example.learnprogramming.retrofit.PersonalDetailsAPI
import com.example.learnprogramming.retrofit.RetrofitService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class PersonalDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPersonalDetailsBinding

    private val personalDetailsAPI =
        RetrofitService().retrofit.create(PersonalDetailsAPI::class.java)


    private lateinit var tILName: TextInputLayout
    private lateinit var tILEmail: TextInputLayout
    private lateinit var tILMobile: TextInputLayout
    private lateinit var tILUniversity: TextInputLayout
    private lateinit var fABEdit: FloatingActionButton
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActivity()

        Log.d("TAG", "onCreate: ")
        displayUserDataFromSharedPreferences()
    }

    private fun initActivity() {

        binding.btnedit.setOnClickListener {
            binding.etName.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etMobile.isEnabled = true
            binding.etUnivercityName.isEnabled = true

            binding.btnedit.visibility = View.GONE
            binding.btnSave.visibility = View.VISIBLE

        }
        binding.btnSave.setOnClickListener {

            binding.etName.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etMobile.isEnabled = false
            binding.etUnivercityName.isEnabled = false
            binding.btnedit.visibility = View.VISIBLE
            binding.btnSave.visibility = View.GONE

            saveUserDataToSharedPreferences()
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            // startActivity(Intent(this, ProfileFragment::class.java))
        }
    }


    private fun displayUserDataFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        // Retrieve user data from SharedPreferences
        val userName = sharedPreferences.getString("user_name", "")
        val userEmail = sharedPreferences.getString("user_email", "")
        val userMobile = sharedPreferences.getString("user_mobile", "")
        val userUniversity = sharedPreferences.getString("user_university", "")

        // Display user data in EditText fields
        binding.etName.setText(userName)
        binding.etEmail.setText(userEmail)
        binding.etMobile.setText(userMobile)
        binding.etUnivercityName.setText(userUniversity)
    }

    private fun saveUserDataToSharedPreferences()
    {

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val id = sharedPreferences.getLong("userId",0)


        try {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val mobile = binding.etMobile.text.toString().toLong()
            val university = binding.etUnivercityName.text.toString()

            val user = UserProfile(name,email,mobile,university)

            val calling = CallingApi
            val data = calling.userUpdate.updateProfile(id,user)
            data.enqueue(object : Callback<UserProfile> {
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@PersonalDetailsActivity,"User data updated successfully", Toast.LENGTH_SHORT).show()
                        Log.d("response", "onResponse: " + response.body())
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Log.d("failed", "onFailure: " + t.toString())
                }

            })
        }
        catch (e:Exception)
        {
            Log.d("exception", "saveUserDataToSharedPreferences: "+e.toString())
        }
    }

}