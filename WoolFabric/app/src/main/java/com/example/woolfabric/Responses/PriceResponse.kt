package com.example.woolfabric.Responses

import com.example.woolfabric.Responses.Models.Price

data class PriceResponse (var error:Boolean,var message:String,var data:ArrayList<Price>)