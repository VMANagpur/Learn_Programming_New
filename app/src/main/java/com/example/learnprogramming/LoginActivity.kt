package com.example.learnprogramming

import android.R.attr.password
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.learnprogramming.databinding.ActivityLoginBinding
import com.example.learnprogramming.model.LoginResponse
import com.example.learnprogramming.model.User
import com.example.learnprogramming.model.login
import com.example.learnprogramming.retrofit.RetrofitService
import com.example.learnprogramming.retrofit.UserApi
import com.example.learnprogramming.retrofit.loginApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActivity()


        binding.btnLogin.setOnClickListener {
            binding.edtEmail.setError(null)
            binding.edtPassword.setError(null)

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.equals("")) {
                binding.emailError.visibility = View.VISIBLE
                binding.emailError.setText("Please write your email here")
            }
            if (password.equals("")) {
                binding.passwordError.visibility = View.VISIBLE
                binding.passwordError.setText("Please write your password here")
            }
            if (!email.equals("") && !password.equals(""))
            {
               loginTheUser()
            }
        }


        binding.edtEmail.addTextChangedListener {
            binding.emailError.visibility = View.GONE
        }
        binding.edtPassword.addTextChangedListener {
            binding.passwordError.visibility = View.GONE
        }

    }

    private fun initActivity() {

//       binding.btnLogin.setOnClickListener {
//           startActivity(Intent(this,HomeActivity::class.java))
//           finish()
//       }
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
        binding.txtForgot.setOnClickListener {
            startActivity(Intent(this,ForgetPasswordActivity::class.java))
        }
    }


    override fun onBackPressed() {
        val dialog:AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Are you sure you want to exit from Learn Programming!")

        dialog.setPositiveButton("Yes"){
                dialog,_ ->
            finish()
        }

        dialog.setNegativeButton("No"){
                dialog,_-> dialog.dismiss()
        }

        val alertDialog:AlertDialog = dialog.create()
        alertDialog.show()
    }


    public fun loginTheUser()
    {
        binding.pgBar.visibility = View.VISIBLE
        val retrofitService:RetrofitService = RetrofitService()

        val userApi: loginApi = retrofitService.retrofit.create(loginApi::class.java)

        val login: login = login()

        login.email = binding.edtEmail.text.toString()
        login.password = binding.edtPassword.text.toString()

        val data = userApi.getLogin(login)

        try {

           data.enqueue(object : Callback<LoginResponse>{
               @SuppressLint("SuspiciousIndentation")
               override fun onResponse(
                   call: Call<LoginResponse>,
                   response: Response<LoginResponse>
               ) {

                   if(response.isSuccessful)
                   {
                       if (response != null)
                       {
                             val loginResponse : LoginResponse? = response.body()

                           Log.d("loginResponse", "onResponse: "+loginResponse)

                           if (loginResponse != null)
                           {
                               val message = loginResponse.message
                               val status = loginResponse.status
                               val user = loginResponse.user

                               Log.d("messageAndstatus", "onResponse: "+message+" "+status)

                             if (status == false) {
                                 if (message.equals("Please verify your email address for login")) {
                                     binding.pgBar.visibility=View.GONE
                                     Toast.makeText(
                                         this@LoginActivity,
                                         "Please verify your email address for login",
                                         Toast.LENGTH_SHORT
                                     ).show()
                                 } else if (message.equals("Password is invalid")) {
                                     binding.pgBar.visibility = View.GONE
                                     binding.passwordError.visibility = View.VISIBLE
                                     binding.passwordError.setText("Paswword is incorrect")


                                 } else {
                                     binding.pgBar.visibility = View.GONE
                                     binding.emailError.visibility = View.VISIBLE
                                         binding.emailError.setText("Email is incorrect")
                                 }
                             }
                               else
                             {
                                   val password = binding.edtPassword.text.toString()
                                   saveUserDataToSharedPreferences(user,password )
                                   if (message.equals("login Suceesful")) {
                                       binding.pgBar.visibility = View.GONE
                                       startActivity(
                                           Intent(
                                               this@LoginActivity,
                                               HomeActivity::class.java
                                           )
                                       )
                                       finish()

                                       Toast.makeText(this@LoginActivity,"Login Successful",Toast.LENGTH_SHORT).show()
                                   }
                             }
                             }

                       }
                   }

               }

               override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                   Toast.makeText(
                       this@LoginActivity,
                       "Sorry,login is failed, Please try after some time there is problem in server",
                       Toast.LENGTH_SHORT
                   ).show()
                   binding.pgBar.visibility = View.GONE
               }

           })

        }
        catch (e:Exception)
        {
            Log.v("TAG", "$e")
        }

        }
    private fun saveUserDataToSharedPreferences(user: User, value: String ) {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

       editor.putString("user_name", user.name)
        editor.putString("user_email", user.email)
        editor.putString("user_mobile", user.mobileNumber.toString())
       editor.putString("user_university", user.university)
         editor.putLong("userId", user.id)
        editor.putString("user_password", value.toString())


        editor.apply()

    }
}


