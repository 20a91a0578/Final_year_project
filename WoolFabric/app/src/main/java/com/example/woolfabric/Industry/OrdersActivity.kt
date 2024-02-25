package com.example.woolfabric.Industry

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.woolfabric.Farmer.Functions.ViewMyOrders
import com.example.woolfabric.R
import com.example.woolfabric.databinding.ModifycardBinding

class OrdersActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        findViewById<RecyclerView>(R.id.cycle14).apply {
            layoutManager=LinearLayoutManager(this@OrdersActivity)
            adapter=Adaptercreater(this@OrdersActivity, array = arrayOf("Products","View orders","Wool Processing")
            , images =  arrayOf(R.drawable.addproduct,R.drawable.listviews,R.drawable.processing)
            )
        }
    }




    inner class Adaptercreater(val context: Context,val array: Array<String>,val images: Array<Int>) : RecyclerView.Adapter<Adaptercreater.Creator>() {
        inner class Creator(val item:ModifycardBinding):RecyclerView.ViewHolder(item.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=Creator(
            ModifycardBinding.inflate(LayoutInflater.from(context)
            ,parent,false)
        )

        override fun getItemCount()=array.size

        override fun onBindViewHolder(holder: Creator, position: Int) {
            val k=array[position]
            with(holder.item){

                image12.load(images[position])
                details9.text=k
            }

   holder.itemView.setOnClickListener {
       when(k){
           "Products"->{
               startActivity(Intent(context,ViewAll::class.java))
           }
           "View orders"->{
               startActivity(Intent(context,ViewMyOrders::class.java))
           }
           "Wool Processing"->{
               startActivity(Intent(context,GetallMyRequests::class.java))
           }
       }

   }
        }

    }
}