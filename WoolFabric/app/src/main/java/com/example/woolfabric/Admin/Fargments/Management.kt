package com.example.woolfabric.Admin.Fargments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woolfabric.Adapter.AdapterForUser
import com.example.woolfabric.Admin.AddMembers
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.LoginResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityManagementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Management: Fragment() {
    private lateinit var bind:ActivityManagementBinding
    private lateinit var allFunction:AllFunction
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind=ActivityManagementBinding.inflate(layoutInflater)

        allFunction=AllFunction(requireContext())

                    viewdatae()
                   return bind.root
        }

    private fun viewdatae() {
        CoroutineScope(Dispatchers.IO).launch {
            RetroFit.instance.getperosons(condition = "getotherusers").enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                    response.body()?.let {
                        allFunction.apply {
                            p.dismiss()
                            bind.cycle1.layoutManager=LinearLayoutManager(requireContext())
                            bind.cycle1.adapter=AdapterForUser(requireContext(), it.data, "admin","control")
                        }
                    }
                    if(response.body()==null){
                        allFunction.apply {
                            p.dismiss()
                            toast("$this"+"error i response ")
                        }
                    }


                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    allFunction.apply {
                        toast(t.message!!)
                        p.dismiss()
                    }
                }
            })
        }
    }

    override fun onResume() {
        viewdatae()
        super.onResume()
    }
}