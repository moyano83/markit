import com.markit.api.MarkitExercise

import scala.util.{Failure, Try}

object MarkitApp extends App{

  /**
    * Main method, application entry point
    * @param args the args for the application
    */
  override def main(args:Array[String]):Unit = {
    if(args.size!=2 || Try(args(1).toInt).isFailure) showHelp()
    else Try(executeCommand(args(0), args(1).toInt)) match{
          case Failure(x:Exception) => println(x.getMessage)
          case _ =>
        }
  }

  /**
    * Default print operation
    * @param number the number to format
    */
  private def printFormattedDouble(number:Double):Unit = println(f"${number}%.5f")

  /**
    * Executes the utility class method
    * @param ticker the ticker to look at
    * @param option the option 1-3
    */
  private def executeCommand(ticker: String, option: Int) = {
    val markitExercise = MarkitExercise()
      option match {
      case 1 => markitExercise.dailyPrices(ticker).foreach(printFormattedDouble)
      case 2 => markitExercise.returns(ticker).foreach(printFormattedDouble)
      case 3 => Some(markitExercise.meanReturn(ticker)).foreach(printFormattedDouble)
      case _ => showHelp()
    }
  }

  /**
    * Usage explanation
    */
  def showHelp() = println(
    """
       | Usage of application:
       | scala -classpath Markit.jar MarkitApp <ticker> <operation>
       | where <operation> is a number from 1 to 3 representing:
       | 1 - one year of historic prices given for the given ticker
       | 2 - Daily returns for the given ticker
       | 3 - Mean of one year of historic prices given the ticker
       |
       | usage example:
       |  scala -classpath <project folder>/target/scala-2.11/Markit.jar MarkitApp AMZN 2
     """.stripMargin)
}
