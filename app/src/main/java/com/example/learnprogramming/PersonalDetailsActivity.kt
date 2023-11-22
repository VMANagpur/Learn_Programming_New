package com.example.learnprogramming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.learnprogramming.databinding.ActivityPersonalDetailsBinding
import com.example.learnprogramming.model.Profile
import com.example.learnprogramming.model.User
import com.example.learnprogramming.retrofit.PersonalDetailsAPI
import com.example.learnprogramming.retrofit.RetrofitService
import com.example.learnprogramming.retrofit.UserApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import okhttp3.Callback
import okhttp3.Response
import retrofit2.create
import java.util.jar.Attributes.Name

@Suppress("DEPRECATION")
class PersonalDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPersonalDetailsBinding

    private val personalDetailsAPI = RetrofitService().retrofit.create(PersonalDetailsAPI::class.java)


    private lateinit var tILName: TextInputLayout
    private lateinit var tILEmail: TextInputLayout
    private lateinit var tILMobile: TextInputLayout
    private lateinit var tILUniversity: TextInputLayout
    private lateinit var fABEdit: FloatingActionButton
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityPersonalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActivity()

        Log.d("TAG", "onCreate: ")
    }
    private fun initActivity() {

        binding.btnedit.setOnClickListener {
         binding.etName.isEnabled =true
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
            profileDetails()

    }

        binding.toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
               // startActivity(Intent(this, ProfileFragment::class.java))
            }
    }
    private fun profileDetails(){

        val retrofitService:RetrofitService = RetrofitService()

        val profileApi: PersonalDetailsAPI = retrofitService.retrofit.create(PersonalDetailsAPI::class.java)

        val profile: Profile = Profile()

        profile.email = binding.etEmail.text.toString()
        profile.name = binding.etName.text.toString()
        profile.mobileNumber = binding.etMobile.text.toString().toLong()
        profile.university = binding.etUnivercityName.text.toString()

        val data = profileApi.updateProfile(User())
    }
}