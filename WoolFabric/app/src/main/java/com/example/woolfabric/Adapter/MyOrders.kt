package com.example.woolfabric.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.woolfabric.Responses.Models.Orders
import com.example.woolfabric.databinding.UsercardBinding

class MyOrders(var context: Context, var data:ArrayList<Orders>):RecyclerView.Adapter<MyOrders.Viewees>(){
    class Viewees(val item :UsercardBinding):RecyclerView.ViewHolder(item
        .root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=Viewees(UsercardBinding.inflate(
        LayoutInflater.from(context),parent,false))

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: Viewees, position: Int) {
        val d=data[position]
 with(holder.item){
     userimage.load(d.image)
     val total=d.qty.toInt()*d.cost.toInt()
     val text="<b>Product name : </b>${d.pname}<br>" +
             "<b>Cost : </b>₹\n${d.cost}<br>" +
             "<b>Cost : </b>${d.cost}<br>" +
             "<b>Quantity : </b>${d.qty}<br>" +
             "<b>Total : </b>₹${total}/-<br>"
     details.text= HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
     details.setTextColor(Color.WHITE)
 }
    }
}