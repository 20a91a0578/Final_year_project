package com.example.woolfabric.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Industry.ViewDetails
import com.example.woolfabric.Responses.Models.Requests
import com.example.woolfabric.databinding.UsercardBinding

class RequestsView(val context: Context, val data: ArrayList<Requests>,val typo: String):RecyclerView.Adapter<RequestsView.Vieweds>() {
    class Vieweds(val item :UsercardBinding):RecyclerView.ViewHolder(item
        .root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=Vieweds(UsercardBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount()=data.size

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: Vieweds, position: Int) {
    val t=data[position]

        with(holder.item){
            userimage.isVisible=false
        val text="<b>Request id : </b>${t.id}<br>" +
                "<b>Wool Weight : </b>${t.woolweight}<br>" +
                "<b>Cost per Wool : </b>${t.bidprice}<br>" +
                "<b>Status : </b>${t.status}"
            details.text=HtmlCompat.fromHtml(text,FROM_HTML_OPTION_USE_CSS_COLORS)
card2.setOnClickListener { Intent(context,ViewDetails::class.java).apply {
        putExtra("data",t)
        putExtra("type",typo)
        context.startActivity(this)
}
}
        }
    }
}