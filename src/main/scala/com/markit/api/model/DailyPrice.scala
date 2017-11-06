package com.markit.api.model

import org.joda.time.DateTime

case class DailyPrice(date:DateTime, open:Double, high:Double, low:Double, close:Double, volume:Int)
