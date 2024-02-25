package com.example.woolfabric.Farmer.Functions

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Adapter.RequestsView
import com.example.woolfabric.R
import com.example.woolfabric.Responses.RequestResponse
import com.example.woolfabric.Responses.RetroFit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ViewMyRequests : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_my_requests)
        val cycle13=findViewById<RecyclerView>(R.id.cycle13)
        cycle13.layoutManager=LinearLayoutManager(this)
        val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")
        CoroutineScope(Dispatchers.IO).launch {
            RetroFit.instance.getrequest("getfarmerrequest","$id").enqueue(object :retrofit2.Callback<RequestResponse>{
                override fun onResponse(
                    call: Call<RequestResponse>,
                    response: Response<RequestResponse>
                ) {
                    response.body()?.apply {
                        cycle13.setItemViewCacheSize(data.size)
                        cycle13.adapter= RequestsView(this@ViewMyRequests, data, "change")
                    }
                }
                override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
                    Toast.makeText(this@ViewMyRequests, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}