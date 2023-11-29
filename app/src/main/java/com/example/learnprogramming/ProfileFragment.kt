package com.example.learnprogramming

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.learnprogramming.databinding.FragmentProfileBinding
import com.example.learnprogramming.model.User
import com.example.learnprogramming.model.login
import com.example.learnprogramming.retrofit.PersonalDetailsAPI
import com.example.learnprogramming.retrofit.RetrofitService
import com.example.learnprogramming.retrofit.UserApi
import com.example.learnprogramming.retrofit.loginApi


class ProfileFragment : Fragment() {
    private var binding : FragmentProfileBinding?=null
    private  val binding1 get() = binding !!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater,container,false)

        return binding1.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.linearLogut?.setOnClickListener {
            startActivity(Intent(context,LoginActivity::class.java))
            requireActivity().finish()
        }
        binding!!.txtPrivacy!!.setOnClickListener {
            startActivity(Intent(context, PrivacyActivity::class.java))
        }
        binding!!.txtPersonalDetails!!.setOnClickListener {
            startActivity(Intent(context, PersonalDetailsActivity::class.java))

        }

        binding!!.txtLogout.setOnClickListener {
           val sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putBoolean("user_login",false)
            editor.remove("user_name")
            editor.remove("user_email")
            editor.remove("user_mobile")
            editor.remove("user_university")
            editor.remove("userId")
            editor.remove("user_password")
            editor.apply()

            startActivity(Intent(context,LoginActivity::class.java))
            requireActivity().finish()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}