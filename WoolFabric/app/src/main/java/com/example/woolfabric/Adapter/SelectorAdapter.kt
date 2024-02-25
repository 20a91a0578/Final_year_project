package com.example.woolfabric.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewStructure.HtmlInfo
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.woolfabric.Responses.Models.Product
import com.example.woolfabric.Responses.Models.Selection
import com.example.woolfabric.databinding.SelectioncardBinding
import java.lang.StringBuilder

class SelectorAdapter(
    val data: ArrayList<Product>,
    val context: Context,
    val selection: ArrayList<Selection>,
    val reponsive: reponsive
) : RecyclerView.Adapter<SelectorAdapter.Selector>() {
    class Selector(val item: SelectioncardBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Selector(
        SelectioncardBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
    )

    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Selector, position: Int) {
        val d = data[position]
        selection.add(Selection(d.id.toInt(), 0, d.cost.toInt()))
        with(holder.item) {
            imageView6.load(d.image)
            val text = "<b>Product name : </b>${d.pname}<br>" +
                    "<b>Left quantity :</b>${d.leftqty}<br>" +
                    "<b>Cost :</b>₹ ${d.cost}/-<br>"
            details6.text = HtmlCompat.fromHtml(text, FROM_HTML_OPTION_USE_CSS_COLORS)
            var num = 0
            add.setOnClickListener {
                num = 1
                selection[position].qty = 1
                count.text = "$num"
                add.isVisible = false
                mylay.isVisible = true
                reponsive.position(selection.size)
            }
            val k=StringBuilder()
            d.leftqty.forEach {
                if(it.isDigit()){
                    k.append(it)
                }else{
                    k.append(0)
                }
            }
            if(k.isNotEmpty()){
                if(k.toString().toInt()==0){
                    selection[position].qty = 0
                    linearLayout4.isVisible=false
                }else{
                    stockover.isVisible = false
                }
            }
            plus.setOnClickListener {
                if (d.leftqty.toInt() != num) {
                    num++
                    selection[position].qty = num
                    count.text = "$num"
                    reponsive.position(selection.size)
                    stockover.isVisible = false
                }
                stockover.isVisible = d.leftqty.toInt() == num
            }
            minus.setOnClickListener {
                if (num > 1) {
                    num--
                    selection[position].qty = num
                    count.text = "$num"
                } else {
                    selection[position].qty = 0
                    add.isVisible = true
                    mylay.isVisible = false
                    stockover.isVisible = false
                }
                reponsive.position(selection.size)
            }


        }
    }
}