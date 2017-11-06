package com.markit.api.parser

import com.markit.api.Constants
import com.markit.api.model.DailyPrice
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object DailyPriceParser {

  val datePosition = 0
  val openPosition = 1
  val highPosition = 2
  val lowPosition = 3
  val closePosition = 4
  val volumePosition = 5

  def parse(line:String):DailyPrice = {
    val parsedItems = line.split(",")
    DailyPrice(DateTime.parse(parsedItems(datePosition), DateTimeFormat.forPattern(Constants.TimeFormatPattern)),
      parsedItems(openPosition).toDouble,
      parsedItems(highPosition).toDouble,
      parsedItems(lowPosition).toDouble,
      parsedItems(closePosition).toDouble,
      parsedItems(volumePosition).toInt)
  }
}
