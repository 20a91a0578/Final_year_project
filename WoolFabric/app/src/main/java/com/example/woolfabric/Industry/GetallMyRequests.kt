package com.example.woolfabric.Industry

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.CustomeResponse2
import com.example.woolfabric.Responses.Models.Customdata
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityGetallMyRequestsBinding
import com.example.woolfabric.databinding.UsercardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetallMyRequests : AppCompatActivity() {
    val bind by lazy {
        ActivityGetallMyRequestsBinding.inflate(layoutInflater)
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 setContentView(bind.root)
    getdata()
    }

    private fun getdata() {
        bind.progress.isVisible=true
        val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")
        RetroFit.instance.getmycompletedlist(condition = "getcompleted", id = "$id").enqueue(object :Callback<CustomeResponse2>{
            override fun onResponse(
                call: Call<CustomeResponse2>,
                response: Response<CustomeResponse2>
            ) {
                response.body()?.apply {
                bind.cycle15.apply {
                    layoutManager=LinearLayoutManager(this@GetallMyRequests)
                adapter=MyView(this@GetallMyRequests,data)
                }



                }

                bind.progress.isVisible=false

            }

            override fun onFailure(call: Call<CustomeResponse2>, t: Throwable) {
            bind.progress.isVisible=false
            }
        })
    }
inner  class MyView(val context: Context,val data: ArrayList<Customdata>):RecyclerView.Adapter<MyView.Viewed>(){
    inner class Viewed (val item :UsercardBinding):RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        Viewed(
            UsercardBinding.inflate(LayoutInflater.from(
              context
            ),
                parent,
                false)
        )

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: Viewed, position: Int) {
        val k=data[position]
    with(holder.item){
        userimage.load(k.image)
        val text="<b>Name</b>: ${k.name}<br>" +
                "<b>Mobile</b>: ${k.mobile}<br>" +
                "<b>Price per kg</b>: ${k.bidprice}<br>" +
                "<b>Wool Weight</b>: ${k.woolweight}<br>" +
                "<b>Description</b>: ${k.content}<br>"
        details.text=HtmlCompat.fromHtml(text,HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
    }
        holder.itemView.setOnClickListener {
Intent(context,CheckoutMystock::class.java).apply {
    putExtra("data",k)
    startActivity(this)
}
        }
    }

}
}

