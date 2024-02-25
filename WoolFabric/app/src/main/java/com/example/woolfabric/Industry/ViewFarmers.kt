package com.example.woolfabric.Industry

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Adapter.AdapterForUser
import com.example.woolfabric.AllFunction
import com.example.woolfabric.R
import com.example.woolfabric.Responses.LoginResponse
import com.example.woolfabric.Responses.RetroFit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewFarmers : AppCompatActivity() {
    private lateinit var allFunction: AllFunction
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_farmers)
        val cycle=findViewById<RecyclerView>(R.id.cycle12)
        allFunction=AllFunction(this)
        allFunction.p.show()
        CoroutineScope(Dispatchers.IO).launch {
            RetroFit.instance.getFarmerProducts("getfarmer").enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            allFunction.p.dismiss()
                    response.body()?.let {

                        cycle.apply {
                            layoutManager= LinearLayoutManager(this@ViewFarmers)
                            adapter= AdapterForUser(
                                this@ViewFarmers,
                                it.data,
                                "industry",
                                "no control"
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
               allFunction.apply {
                   p.dismiss()
                   toast(t.message!!)
               }
                }
            })
        }
    }

}