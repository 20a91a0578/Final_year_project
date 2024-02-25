package com.example.woolfabric.Farmer.Functions

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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class AllRequests : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_requests)

    getdata()
    }
fun getdata(){
    val recycle=findViewById<RecyclerView>(R.id.cycle9)
    recycle.layoutManager=LinearLayoutManager(this)
    val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")

    CoroutineScope(IO).launch {
        RetroFit.instance.getrequest("getmyrequests","$id").enqueue(object :retrofit2.Callback<RequestResponse>{
            override fun onResponse(
                call: Call<RequestResponse>,
                response: Response<RequestResponse>
            ) {
            response.body()?.apply {
                recycle.setItemViewCacheSize(data.size)
                recycle.adapter= RequestsView(this@AllRequests, data, "not change")
            }
            }

            override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
                Toast.makeText(this@AllRequests, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
}