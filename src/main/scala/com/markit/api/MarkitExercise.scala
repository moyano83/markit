package com.markit.api

import com.markit.api.finance.{FinanceClient, GoogleFinanceClient}

class MarkitExercise(financeClient: FinanceClient){

  def dailyPrices(ticker: String): List[Double] = financeClient.getTickerData(ticker).map(_.close)

  def returns(ticker: String):Seq[Double] = {
    def returnsAcc(priceToday:Double, prices:List[Double], returns:List[Double]):List[Double] ={
      if(prices.nonEmpty){
        val yesterdaysPrice = prices.head
        val newReturn = (priceToday - yesterdaysPrice) / yesterdaysPrice
        returnsAcc(yesterdaysPrice, prices.tail, newReturn :: returns)
      }else returns.reverse
    }
    val dailyPricesList = dailyPrices(ticker)
    returnsAcc(dailyPricesList.head, dailyPricesList.tail, List())
  }

  def meanReturn(ticker: String):Double = {
    val dailyPricesList = dailyPrices(ticker)
    if(dailyPricesList.nonEmpty) dailyPricesList.sum / dailyPricesList.size
    else 0
  }
}

object MarkitExercise{
  def apply(): MarkitExercise = new MarkitExercise(new GoogleFinanceClient(None))
}
