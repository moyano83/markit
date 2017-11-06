package com.markit.api.finance

import java.util.UUID

import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GoogleFinanceClientTest extends FlatSpec{

  val googleFinanceClient = new GoogleFinanceClient(None)

  "The GoogleFinanceClient" should "be able to retrieve a valid ticker" in {
    val response = googleFinanceClient.getTickerData("AMZN")
    assert(response.nonEmpty)
    response.foldLeft(new DateTime())((b, dailyPrice) => {
      assert(b.isAfter(dailyPrice.date))
      dailyPrice.date
    })
  }

  it should "retrieve no data if an invalid ticker is passed" in {
    assertThrows[Exception]{
      assert(googleFinanceClient.getTickerData(UUID.randomUUID().toString).isEmpty)
    }
  }
}
