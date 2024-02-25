package com.example.woolfabric.User.Functions

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Adapter.MyOrders
import com.example.woolfabric.Adapter.ProductsView
import com.example.woolfabric.AllFunction
import com.example.woolfabric.R
import com.example.woolfabric.Responses.CustomReponse
import com.example.woolfabric.Responses.CustomResponse
import com.example.woolfabric.Responses.RetroFit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.typeOf

class ViewDetails : AppCompatActivity() {
    lateinit var allFunction: AllFunction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)
        allFunction= AllFunction(this)
        val text=findViewById<TextView>(R.id.total)
        val cycle=findViewById<RecyclerView>(R.id.cycle7)
        cycle.layoutManager=LinearLayoutManager(this)
        text.isVisible=false
        val update=findViewById<Button>(R.id.call)
        intent.getStringExtra("id")?.let {

update.setOnClickListener {
    startActivity(Intent(Intent.ACTION_DIAL,Uri.parse("tel:${intent.getStringExtra("mobile")}")))
}


            allFunction.p.show()

            CoroutineScope(IO).launch {
                RetroFit.instance.getuserproducts(condition="getorders",id=it).enqueue(object :Callback<CustomReponse>{
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<CustomReponse>,
                        response: Response<CustomReponse>
                    ) {
                        allFunction.apply {
                            p.dismiss()
                            response.body()?.apply {
                                cycle.setItemViewCacheSize(data.size)
                                cycle.adapter=MyOrders(this@ViewDetails,data)
                                var num=0
                                data.forEach {
                num+=it.cost.toInt()*it.qty.toInt()
                                }
                                text.text="Total cost:₹$num/-"
                                text.isVisible=true

                            }

                        }
                    }

                    override fun onFailure(call: Call<CustomReponse>, t: Throwable) {
                        allFunction.apply {
                            p.dismiss()
                        toast(t.message!!)}
                    }
                })
            }
        }
    }
}