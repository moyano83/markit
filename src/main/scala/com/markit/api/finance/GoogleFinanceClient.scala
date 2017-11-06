package com.markit.api.finance

import com.markit.api.Constants.{GoogleFinanceBaseUrl, OutputFormat, defaultIndex}
import com.markit.api.model.DailyPrice
import com.markit.api.parser.DailyPriceParser
import skinny.http.HTTP

class GoogleFinanceClient(index:Option[String]) extends FinanceClient{
  override def getTickerData(ticker:String):List[DailyPrice] = {
    val response = HTTP.get(GoogleFinanceBaseUrl,
      ("q" -> index.getOrElse(defaultIndex).concat(":").concat(ticker)),
      ("output", OutputFormat))
    if(response.status == 200) {
      response.textBody.split("\n")
        .tail.map(DailyPriceParser.parse(_)).toList
    }else{
      throw new Exception(s"Couldn't retrieve Google finance index. Response code=[${response.status}]")
    }
  }
}
