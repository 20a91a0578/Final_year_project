package com.example.woolfabric.Responses

import com.example.woolfabric.Responses.Models.Request23

data class ReportResponse (
    var error:Boolean,
    var message:String,
    var data:ArrayList<Request23>
)
