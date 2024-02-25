package com.example.woolfabric.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.woolfabric.Farmer.Functions.SendRequest
import com.example.woolfabric.R
import com.example.woolfabric.Responses.Models.User
import com.example.woolfabric.User.Functions.ViewProducts
import com.example.woolfabric.databinding.UsercardBinding

class AdapterForUser(val context: Context, val data: ArrayList<User>, val type: String,val  contr: String) : RecyclerView.Adapter<AdapterForUser.Viewed2>() {
    class Viewed2(val item :UsercardBinding):RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= Viewed2(UsercardBinding.inflate(
        LayoutInflater.from(context),parent,false))

    override fun getItemCount()=data.size

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Viewed2, position: Int) {
val d=data[position]
        val rand=(111..999999).random()
        with(holder.item){
    val text=if(d.type=="Farmer"){
        "<b>Name :</b>${d.name}<br></br>" +
                "<b>Mail :</b>${d.mail}<br></br>" +
                "<b>Mobile number :</b>${d.mobile}<br></br>" +
                "<b>No of sheep :</b>${d.count}"
    }else{
        "<b>Name :</b>${d.name}<br></br>" +
                "<b>Mail :</b>${d.mail}<br></br>" +
                "<b>Mobile number :</b>${d.mobile}<br></br>" +
                "<b>Address :</b>${d.count}"
    }

            if(type!="user"){
                try {
                    card2.setCardBackgroundColor(Color.parseColor("#$rand"))
                } catch (e: Exception) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        card2.setCardBackgroundColor(context.getColor(R.color.grey))
                    } else {
                        card2.setCardBackgroundColor(R.color.grey)
                    }
                }
            }else{
                details.setTextColor(Color.WHITE)
            }
            userimage.load(d.image)
            details.text = HtmlCompat.fromHtml(text,HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)

        card2.setOnClickListener {
            if(contr!="control") {
                if (type == "industry") {
                    Intent(context, SendRequest::class.java).apply {
                        putExtra("data", d)
                        context.startActivity(this)
                    }
                } else {
                    Intent(context, ViewProducts::class.java).apply {
                        putExtra("data", d)
                        context.startActivity(this)
                    }
                }
            }}
        }
    }

}
