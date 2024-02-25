package com.example.woolfabric.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.woolfabric.Farmer.Functions.UpdateProduct
import com.example.woolfabric.Responses.Models.Product
import com.example.woolfabric.User.Functions.ViewProducts
import com.example.woolfabric.databinding.UsercardBinding

class ProductsView(val context: Context, val data: ArrayList<Product>) :
    RecyclerView.Adapter<ProductsView.MyView>() {
    class MyView(val view: UsercardBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyView(
        UsercardBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val d = data[position]
        with(holder.view) {
            userimage.load(d.image)
            val text = "<b>Product name : </b>${d.pname}<br>" +
                    "<b>Cost : </b>₹\n${d.cost}<br>" +
                    "<b>Last updated : </b>${d.updatedate}<br>" +
                    "<b>Left Quantity : </b>${d.leftqty}<br>"
            details.text = HtmlCompat.fromHtml(text, FROM_HTML_OPTION_USE_CSS_COLORS)
            details.setTextColor(Color.WHITE)

            card2.setOnClickListener {
                Intent(context, UpdateProduct::class.java).apply {
                    putExtra("data", d)
                    context.startActivity(this)
                }
            }
        }

    }
}
