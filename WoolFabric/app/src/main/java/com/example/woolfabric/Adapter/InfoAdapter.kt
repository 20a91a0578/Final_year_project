package com.example.woolfabric.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.woolfabric.Farmer.Functions.ViewInfo
import com.example.woolfabric.R
import com.example.woolfabric.Responses.Models.Information
import com.example.woolfabric.databinding.InfocardBinding

class InfoAdapter(val context: Context,val data:ArrayList<Information>):RecyclerView.Adapter<InfoAdapter.Viewed>() {
class Viewed(val item :InfocardBinding):RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= Viewed(InfocardBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: Viewed, position: Int) {
val g=data[position]
        with(holder.item){
            detailimage.load(g.path)
detailimage.setOnClickListener {
}
            details2.text= g.title
detailimage.setOnClickListener {
    Intent(context, ViewInfo::class.java).apply {
        putExtra("data",g)
        context.startActivity(this)
    }
}
}
    }
}