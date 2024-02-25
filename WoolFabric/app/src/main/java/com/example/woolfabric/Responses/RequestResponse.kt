package com.example.woolfabric.Responses

import com.example.woolfabric.Responses.Models.Requests

data class RequestResponse (
    var error:Boolean,
    var message:String,
    var data:ArrayList<Requests>
)