package com.markit.api.finance

import com.markit.api.model.DailyPrice

trait FinanceClient {
  def getTickerData(ticker:String):List[DailyPrice]
}
