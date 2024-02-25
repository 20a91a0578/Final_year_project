package com.example.woolfabric.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Admin.Info.Price
import com.example.woolfabric.Admin.Info.Addinformation
import com.example.woolfabric.Responses.Models.Count
import com.example.woolfabric.databinding.CardBinding

class AdminHome(
    val context: Context,
    val data: ArrayList<String>,
    val drawable: ArrayList<Int>,
    val count: ArrayList<Count>
):RecyclerView.Adapter<AdminHome.Viewed>() {
    class Viewed(val card:CardBinding):RecyclerView.ViewHolder(card.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= Viewed(CardBinding.inflate(LayoutInflater.from(context),parent,false))

    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: Viewed, position: Int) {
      with(holder.card){
          text.text=data[position]
          image2.setImageDrawable(context.getDrawable(drawable[position]))
          val rgb=(0..999999).random()
          try{
              coun.text=count[position].count
              card.setCardBackgroundColor(Color.parseColor("#$rgb"))
          }catch (e:Exception){
              card.setCardBackgroundColor(Color.BLACK)
          }
      card.setOnClickListener {
var int=Intent()
          when(data[position]){
              "Wool Growth"->{
int=Intent(context,Addinformation::class.java)
                  int.putExtra("type",data[position])

              }
              "Price"->{
                  int=Intent(context,Price::class.java)
              }
              "Trade News"->{
                  int=Intent(context,Addinformation::class.java)
                  int.putExtra("type",data[position])
              }
                      "News"->{

                          int=Intent(context,Addinformation::class.java)
                          int.putExtra("type",data[position])
                      }
          }
          context.startActivity(int)
      }
      }

    }

    override fun getItemCount()=data.size
}