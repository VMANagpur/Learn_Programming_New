package com.example.learnprogramming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnprogramming.databinding.FragmentTestBinding
import com.example.learnprogramming.model.MockTestItemItem
import com.example.learnprogramming.retrofit.CallingApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestFragment : Fragment() {

    private var binding:FragmentTestBinding?=null
    private  val binding1 get() = binding !!
    private lateinit var adapter:test_adapter

    private var mocktestId = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTestBinding.inflate(inflater,container,false)
        return binding1.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        adapter = test_adapter(emptyList())
        binding?.recyclerView?.adapter = adapter

        adapter.notifyDataSetChanged()

       val callingApi = CallingApi

        try {

            val data = callingApi.MockTestApi.getMockTestItem()

            data.enqueue(object : Callback<List<MockTestItemItem>> {
                override fun onResponse(
                    call: Call<List<MockTestItemItem>>,
                    response: Response<List<MockTestItemItem>>
                ) {
                    if (response.isSuccessful) {
                            val mockInformation = response.body()
                            if (mockInformation != null) {
                                adapter.setData(mockInformation)
                                Log.d("mock", "onResponse: "+mockInformation)
                                adapter.notifyDataSetChanged()
                            }

                    }
                }

                override fun onFailure(call: Call<List<MockTestItemItem>>, t: Throwable) {
                   Toast.makeText(requireContext(),"Sorry,login is failed, Please try after some time there is problem in server",Toast.LENGTH_SHORT).show()
                }

            })
        }
        catch (e : Exception){
            Log.e("FailedInMockTest", "onViewCreated: ",e )
        }




    }






}