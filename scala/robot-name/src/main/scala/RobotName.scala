import Robot.alreadySet

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Random

object Robot {
  private val alreadySet = mutable.HashSet.empty[String]
}

class Robot {

  private var _name = makeRandomName()
  def name: String = _name

  @tailrec
  private def makeRandomName(): String = {
    val letters = Random.alphanumeric.filter(c => {c.isLetter && c.isUpper}).take(2).mkString
    val digits = Random.alphanumeric.filter(_.isDigit).take(3).mkString
    val robotName = letters.concat(digits)
    if (alreadySet contains robotName) {
      makeRandomName()
    } else {
      alreadySet += robotName
      robotName
    }
  }

  def reset(): Unit = {
    alreadySet -= _name
    _name = makeRandomName()
  }
}