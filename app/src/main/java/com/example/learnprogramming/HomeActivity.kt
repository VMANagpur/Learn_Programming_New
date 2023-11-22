package com.example.learnprogramming

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.learnprogramming.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding
    private var selectedItemId = R.id.test // Initialize with the default item

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadFragment(TestFragment(),true)

        binding.bottomNavigationView.setOnItemSelectedListener {
          selectedItemId  = it.itemId
            when(it.itemId){
                R.id.test -> {
                    loadFragment(TestFragment(),false)
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment(),false)

                }
                else -> {

                }

            }

            true
        }



    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadFragment(fragment: Fragment, addToBackStack:Boolean = false){
    val ft = supportFragmentManager.beginTransaction()
            if (addToBackStack){
                ft.add(R.id.container,fragment)
                Log.d("TAG", "firstbox"+addToBackStack)
            }
                else{
                  ft.replace(R.id.container,fragment)
                  ft.addToBackStack(null)

                Log.d("TAG", "secondbox: "+addToBackStack)
            }

            ft.commit()

    }


    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)

        if (currentFragment is TestFragment)
        {
            finish()
        }
        else
        {
            super.onBackPressed()
        }

        when(currentFragment)
        {
            is ProfileFragment -> selectedItemId = R.id.test
        }

        binding.bottomNavigationView.selectedItemId = selectedItemId
    }

}



