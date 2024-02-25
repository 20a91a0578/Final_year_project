package com.example.woolfabric.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.woolfabric.Responses.Models.ForUser
import com.example.woolfabric.User.Functions.ViewDetails
import com.example.woolfabric.databinding.CardBinding
import com.example.woolfabric.databinding.UsercardBinding

class ViewOrders(val context: Context,val data:ArrayList<ForUser>):RecyclerView.Adapter<ViewOrders.Viewing>() {
    class Viewing(val item:UsercardBinding):RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= Viewing(UsercardBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: Viewing, position: Int) {
    val d=data[position]
        val tt=context.getSharedPreferences("user",AppCompatActivity.MODE_PRIVATE).getString("type","")
        with(holder.item){
            var text="<b>Order id : </b>${d.orderid}<br>" +
                    "<b>Farmer Name : </b> ${d.name}<br>" +
                    "<b>Order Date : </b> ${d.ordereddate}<br>" +
                    "<b>Farmer Mobile : </b> ${d.mobile}<br>" +
                    "<b>Farmer Mail : </b> ${d.mail}<br>" +
                    "<b>Payment status"
            if(tt=="user"){
                userimage.load(d.image)
            }else{

               text= "<b>Order id : </b>${d.orderid}<br>" +
                        "<b>User Name : </b> ${d.name}<br>" +
                        "<b>Order Date : </b> ${d.ordereddate}<br>" +
                        "<b>User Mobile : </b> ${d.mobile}<br>" +
                        "<b>User Mail :</b> ${d.mail}<br>" +
                        "<b>Payment status"
                userimage.isVisible=false
            }

            details.text=HtmlCompat.fromHtml(text,FROM_HTML_OPTION_USE_CSS_COLORS)
    card2.setOnClickListener {
        Intent(context,ViewDetails::class.java).apply {
                putExtra("id",d.orderid)
            putExtra("mobile",d.mobile)
            context.startActivity(this)
        }
    }
        }
    }
}