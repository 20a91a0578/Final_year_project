package com.example.woolfabric

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast

class AllFunction(val context: Context) {
    val p=Dialog(context).apply {
setContentView(R.layout.progress)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun toast(message: Any){
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }
}