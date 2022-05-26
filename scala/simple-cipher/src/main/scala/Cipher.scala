import scala.collection.mutable

case class Cipher(key: String) {
  private def act(applyShift: (Char, Int) => Int)(text: String): String = {
    var i = 0
    text.map(c => {
      val shift = {
        val charShift = {
          // wrap on key overflow
          if (i >= key.length) key(i - key.length)
          else key(i)
        }
        i = i + 1
        charShift - 'a'.toInt
      }

      val newC = applyShift(c, shift)

      // wrap on alphabet overflow
      if (newC > ('a'.toInt + 25)) newC - 26
      else if (newC < 'a'.toInt) newC + 26
      else newC
    }).map(_.toChar).mkString("")
  }

  def encode(text: String): String = act(_ + _)(text)
  def decode(text: String): String = act(_ - _)(text)
}

object Cipher {
  private def randomKey: String = {
    val sb = new mutable.StringBuilder()
    val chars = 'a' to 'z'

    for (_ <- 1 to 100) {
      val randomNum = util.Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString()
  }

  def apply(key: Option[String]): Cipher = key match {
    case None => Cipher(randomKey)
    case Some(key) if key.toLowerCase != key => throw new IllegalArgumentException()
    case Some(key) if key.count(c => c.isDigit) != 0 => throw new IllegalArgumentException()
    case Some(key) if key.isEmpty => throw new IllegalArgumentException()
    case Some(key) => Cipher(key)
  }
}