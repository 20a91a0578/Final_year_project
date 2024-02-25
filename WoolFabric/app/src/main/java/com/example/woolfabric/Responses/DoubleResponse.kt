package com.example.woolfabric.Responses

import android.content.Context
import com.example.woolfabric.Responses.Models.Information
import com.example.woolfabric.Responses.Models.Price
import java.lang.Error

data class DoubleResponse(val error: Boolean,val message:String,val price:ArrayList<Price>,val info:ArrayList<Information>)