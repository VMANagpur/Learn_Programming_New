package com.example.learnprogramming

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.learnprogramming.databinding.ActivityRegistrationBinding
import com.example.learnprogramming.model.User
import com.example.learnprogramming.retrofit.RetrofitService
import com.example.learnprogramming.retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding

    private var isAtleat = false
    private var hasUpperCase = false
    private var hasSymbol = false
    private var hasNumber = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()

        NameChange()
        emailChange()
        MobileChang()
        university()
        password()
        Registration()


    }


    private fun initActivity() {
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Are you sure you want to exit from Learn Programming!")

        dialog.setPositiveButton("Yes") { dialog, _ ->
            finish()
        }

        dialog.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = dialog.create()
        alertDialog.show()


    }

    private fun passwordValidation(password : String) :Boolean {

        //for 8 character
       // val password = binding.etPassword.text.toString()
        if (password.length >= 8) {
            isAtleat = true
            binding.cardOne.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
        } else {
            isAtleat = false
            binding.cardOne.setCardBackgroundColor(resources.getColor(R.color.colorDefault))
        }

        //forUpperCase
        if (passwordValidationForUpperCase(password)) {
            hasUpperCase = true
            binding.cardTwo.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
        } else {
            hasUpperCase = false
            binding.cardTwo.setCardBackgroundColor(resources.getColor(R.color.colorDefault))
        }

        //for number
        if (passwordValidationForNumber(password)) {
            hasNumber = true
            binding.cardThree.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
        } else {
            hasNumber = false
            binding.cardThree.setCardBackgroundColor(resources.getColor(R.color.colorDefault))
        }

        //for symbol
        if (passwordValidateForSymbol(password)) {
            hasSymbol= true
            binding.cardFour.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
        } else {
            hasSymbol  = false
            binding.cardFour.setCardBackgroundColor(resources.getColor(R.color.colorDefault))
        }


        Log.d("TAG", "null "+hasUpperCase+" " + hasNumber +" "+ hasSymbol+" " )
        return   hasUpperCase && hasNumber && hasSymbol
    }

    private fun passwordValidationOverall(password: String) : Boolean{
      //  val password = binding.edtPassword.text.toString()
        // pattern for password starting with charachter , in middle @ # $ i allow ,end should in number
        val pattern = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#])\\S{8,}")
        return pattern.matches(password)

    }


    //atleast one uppercase character in password
    private fun passwordValidationForUpperCase(password:String): Boolean {

       // val password = binding.etPassword.text.toString()
        val uppercase: Pattern = Pattern.compile("[A-Z]+")
        val matcher: Matcher = uppercase.matcher(password)
        return matcher.find()// this use for at least one charachter find in password

    }

    //atleast one number in password
    private fun passwordValidationForNumber(password: String): Boolean {
      //  val password = binding.etPassword.text.toString()
        val number: Pattern = Pattern.compile("[0-9]{1,}")
        val matcher: Matcher = number.matcher(password)
        return matcher.find()
    }

    //for symbol
    private fun passwordValidateForSymbol(password: String): Boolean {
      //  val password = binding.etPassword.text.toString()
        val symbol: Pattern = Pattern.compile("[@#]")
        val matcher: Matcher = symbol.matcher(password)
        return matcher.find()
    }





    private fun forEmailValidation(): Boolean {

        val emailValidate = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")

        val email = binding.etEmail.text.toString()

        return emailValidate.matches(email)
    }

    private fun forNameValidation(Name: String): Boolean {
        val nameValidation = Regex("((^[A-Z a-z]*)\$)")

        return Name.matches(nameValidation)
    }

    private fun forMobileValidation(mobile: String): Boolean {
        val mobileValidation = Regex("(^[0-9]{1,10}\$)")

        return mobileValidation.matches(mobile)
    }

    private fun forMobileValidationOverAll(mobile: String):Boolean
    {
        val mobileValidation = Regex("(^[0-9]{10}\$)")

        return mobileValidation.matches(mobile)
    }



    private fun NameChange() {

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("SuspiciousIndentation")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding.nameError.visibility = View.GONE

                val name = s.toString()

                   if (!forNameValidation(name)) {
                        binding.etName.setError("Name is incoorect")


                    }
                    else {
                        binding.etName.setError(null)
                    }


            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }


    private fun emailChange()
    {
        binding.etEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                if (binding.etName.text.toString().equals(""))
                {
                    binding.nameError.setText("Please write your name here!")
                    binding.nameError.visibility = View.VISIBLE

                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding.emailError.visibility = View.GONE

            }

            override fun afterTextChanged(s: Editable?) {

            }


        })


    }
    private fun MobileChang(){

        binding.etMobile.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                if (binding.etEmail.text.toString().equals(""))
                {
                    binding.emailError.setText("Please write email here")
                    binding.emailError.visibility = View.VISIBLE

                }

                Log.d("TAG", "MobileChang: "+forEmailValidation())
                if (!binding.etEmail.text.toString().equals("")) {
                    if (forEmailValidation() == false) {
                        binding.emailError.setText("Please write correct email")
                        binding.emailError.visibility = View.VISIBLE

                    }
                    else {
                        binding.emailError.visibility = View.GONE
                    }
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding.mobileError.visibility = View.GONE

                //formobile
                val mobile = s.toString()

                if (!binding.etMobile.text.toString().equals("")) {
                    if (!forMobileValidation(mobile)) {
                        binding.etMobile.setError("Mobile is incorrect")

                    }
                    else
                    {
                        binding.etMobile.setError(null)
                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

       private fun university()
       {
           binding.etUnivercityName.addTextChangedListener(object : TextWatcher{
               override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                   if (binding.etMobile.text.toString().equals(""))
                   {
                       binding.mobileError.setText("Please write mobile number!")
                       binding.mobileError.visibility = View.VISIBLE

                   }

                   val mobile = binding.etMobile.text.toString()

                   if(mobile.length>0 && mobile.length<10)
                   {
                       binding.mobileError.setText("mobile number is incorrect!")
                       binding.mobileError.visibility = View.VISIBLE
                   }
               }

               override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

               }

               override fun afterTextChanged(s: Editable?) {

               }

           })
       }

    private fun password(){

        binding.etPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                if (binding.etMobile.text.toString().equals(""))
                {
                    binding.mobileError.setText("Please write mobile number!")
                    binding.mobileError.visibility = View.VISIBLE
                }


                val mobile = binding.etMobile.text.toString()

                if(mobile.length>0 && mobile.length<10)
                {
                    binding.mobileError.setText("mobile number is incorrect!")
                    binding.mobileError.visibility = View.VISIBLE
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val password = s.toString()
                 passwordValidation(password)


                if (password.length>=8) {
                    if (passwordValidationOverall(password) == false) {
                        binding.passwordError.setText("Please write correct password")
                        binding.passwordError.visibility = View.VISIBLE
                    } else {
                        binding.passwordError.visibility = View.GONE
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


    @SuppressLint("SuspiciousIndentation")
    private fun Registration(){

        binding.btnRegistration.setOnClickListener {

            binding.progressBar.visibility=View.VISIBLE
            binding.btnRegistration.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE

          val email = binding.etEmail.text.toString()
          val name = binding.etName.text.toString()
          val mobile = binding.etMobile.text.toString()
          val password = binding.etPassword.text.toString()
           // Toast.makeText(this,"onclick",Toast.LENGTH_SHORT).show()
            if (email.equals("") || name.equals("")  || mobile.equals("") || password.equals(""))
            {
                Toast.makeText(this,"Please fill the form",Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility=View.GONE
                binding.btnRegistration.visibility = View.VISIBLE
                binding.tvLogin.visibility = View.VISIBLE

            }

            if (passwordValidationOverall(password)==false)
            {
                binding.passwordError.setText("Please write correct password")
                binding.passwordError.visibility =  View.VISIBLE
                binding.progressBar.visibility=View.GONE
                binding.btnRegistration.visibility = View.VISIBLE
                binding.tvLogin.visibility = View.VISIBLE
            }
            else
            {
                binding.passwordError.visibility =  View.GONE

            }



            if (forNameValidation(name) && forEmailValidation() && forMobileValidationOverAll(mobile) && passwordValidationOverall(password))
            {
                binding.progressBar.visibility = View.VISIBLE

                val retrofitService:RetrofitService =  RetrofitService();


               val userApi: UserApi = retrofitService.retrofit.create(UserApi::class.java)
               // Toast.makeText(this,"onClick",Toast.LENGTH_SHORT).show()
                val user = User();
                user.name = binding.etName.text.toString();
                user.email = binding.etEmail.text.toString();
                val mobile = binding.etMobile.text.toString();
                user.mobileNumber = mobile.toLong();

                user.university = binding.etUnivercityName.text.toString();
                user.password = binding.etPassword.text.toString();


                try {

                    userApi.getRegister(user)
                        .enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                if (response.isSuccessful) {

                                    Log.d("TAG", "onResponse: succecceful " + response.toString())
                                    if (response != null) {
                                        val res = response.body();
                                        if (res?.contains("user already Exit") == true) {
                                            val dialog =
                                                AlertDialog.Builder(this@RegistrationActivity)
                                                    .setCancelable(true)
                                                    .setMessage("user Already Exit")
                                                    .create()
                                                    .show()

                                            binding.progressBar.visibility = View.GONE
                                            binding.btnRegistration.visibility = View.VISIBLE
                                            binding.tvLogin.visibility = View.VISIBLE
                                            Log.d("TAG", "onResponse: " + "user already exit")


                                        }
                                        if (res?.contains("Registration Successful") == true) {

                                            binding.progressBar.visibility = View.GONE
                                            binding.btnRegistration.visibility = View.GONE
                                            binding.tvLogin.visibility = View.GONE
                                            Log.d("TAG", "onResponse: " + "Registration Successful")


                                    val dialog = AlertDialog.Builder(this@RegistrationActivity)
                                        .setCancelable(true)
                                        .setMessage("Verify Email and Complete the registration processes")
                                        .create()

                                     dialog.setOnCancelListener {

                                         startActivity(Intent(this@RegistrationActivity,LoginActivity::class.java))

                                     }

                                    dialog.show()

                                        }
                                    }

                                }


                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                binding.progressBar.visibility = View.GONE
                                binding.btnRegistration.visibility = View.VISIBLE
                                binding.tvLogin.visibility = View.VISIBLE
                                Log.d("TAG", "onFailure: " + t)

                                val dialog = AlertDialog.Builder(this@RegistrationActivity)
                                    .setCancelable(true)
                                    .setMessage("Registration Failed")
                                    .create()
                                    .show()

                            }

                        })

                }catch (e:Exception){
                    Log.v("TAG", "$e")
                }

            }

            else
            {
                binding.btnRegistration.visibility = View.VISIBLE
                binding.tvLogin.visibility = View.VISIBLE
                binding.progressBar.visibility=View.GONE

                Toast.makeText(this,"Please write correct information",Toast.LENGTH_SHORT).show()
            }
        }
    }

}


