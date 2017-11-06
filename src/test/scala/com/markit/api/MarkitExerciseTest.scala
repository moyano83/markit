package com.markit.api

import com.markit.api.finance.FinanceClient
import com.markit.api.model.DailyPrice
import com.markit.api.parser.DailyPriceParser
import org.junit.runner.RunWith
import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FlatSpec}

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class MarkitExerciseTest extends FlatSpec with Matchers with MockFactory with BeforeAndAfter{
  def getTestClass = {
  val financeData = List(
    "2-Nov-17,257.41,257.75,256.19,257.59,56449535",
    "1-Nov-17,258.04,258.43,257.07,257.49,54202736",
    "31-Oct-17,257.18,257.44,256.80,257.15,60304781",
    "30-Oct-17,257.23,257.60,256.41,256.75,54285687",
    "27-Oct-17,256.47,257.89,255.63,257.71,85562544",
    "26-Oct-17,255.99,256.30,255.48,255.62,69797972",
    "25-Oct-17,256.18,256.31,254.00,255.29,103715291",
    "24-Oct-17,256.60,256.83,256.15,256.56,66935910",
    "23-Oct-17,257.48,257.51,256.02,256.11,63915306").map(DailyPriceParser.parse(_))
  val financeClientMock = mock[FinanceClient]

    (financeClientMock.getTickerData _).expects(*).onCall { param: String =>
      param match {
        case "test" => financeData
        case _ => List[DailyPrice]()
      }
    }

    new MarkitExercise(financeClientMock)

  }

  val dailyPricesList = List(257.59, 257.49, 257.15, 256.75, 257.71, 255.62, 255.29, 256.56, 256.11)

  "The MarkitExercise" should "return the list of dailyRates for a given ticker" in {
    assert(getTestClass.dailyPrices("test") == dailyPricesList)
  }

  it should "return an empty list of rates" in {
    getTestClass.dailyPrices("") == List()
  }

  it should "calculate the average" in {
    val mean = dailyPricesList.sum / 9
    assert(getTestClass.meanReturn("test") == mean)
  }

  it should "return a 0 result for the average if no results are passed" in {
    assert(getTestClass.meanReturn("") == 0)
  }

  it should "calculate the returns of a List of daily prices" in {
    val expectedReturnList:ListBuffer[Double] = ListBuffer()
    for(i<- 0 until 8){
      expectedReturnList += (dailyPricesList(i) - dailyPricesList(i+1)) / dailyPricesList(i+1)
    }
    assert(getTestClass.returns("test") == (expectedReturnList.toList))
  }

  it should "return an empty list of returns if no Daily rates are passed" in {
    assert(getTestClass.dailyPrices("").isEmpty)
  }
}
