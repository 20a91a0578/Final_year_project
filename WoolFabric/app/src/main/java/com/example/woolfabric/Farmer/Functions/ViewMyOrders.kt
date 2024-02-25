package com.example.woolfabric.Farmer.Functions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Adapter.ViewOrders
import com.example.woolfabric.R
import com.example.woolfabric.Responses.CustomReponse
import com.example.woolfabric.Responses.CustomResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.Responses.UsersOrders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMyOrders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_orders2)
        getmydata()
    }

    private fun getmydata() {
        val cycle=findViewById<RecyclerView>(R.id.cycle8)
        cycle.layoutManager=LinearLayoutManager(this)
        val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")!!
        CoroutineScope(IO).launch {

            RetroFit.instance.viewData(condition = "getfarmerside",id=id).enqueue(object :Callback<UsersOrders>{
                override fun onResponse(call: Call<UsersOrders>, response: Response<UsersOrders>) {
                    response.body()?.apply {
                        cycle.setItemViewCacheSize(data.size)
                       cycle.adapter= ViewOrders(this@ViewMyOrders,data)
                    }
                }

                override fun onFailure(call: Call<UsersOrders>, t: Throwable) {
                    Toast.makeText(this@ViewMyOrders, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}