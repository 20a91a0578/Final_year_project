package com.example.woolfabric.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Responses.Models.StartMycheck
import com.example.woolfabric.databinding.MyowncardBinding

class ChechkofItem(val context: Context, val data :ArrayList<StartMycheck>):RecyclerView.Adapter<ChechkofItem.ItemView>() {
    class ItemView (val item :MyowncardBinding):RecyclerView.ViewHolder(item
        .root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=ItemView(
        MyowncardBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
    )

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: ItemView, position: Int) {
    with(holder.item){
        val k=data[position]
        checkable.text=k.item
        checkable.isChecked=k.condition

checkable.setOnCheckedChangeListener { compoundButton, b ->
    k.condition=b
}
    }
    }
}