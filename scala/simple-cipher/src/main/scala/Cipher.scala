import scala.collection.mutable

case class Cipher(key: String) {
  lazy private val alpha = 'a' to 'z'

  private def act(applyShift: (Char, Int) => Int)(text: String): String = {
    val sb = new mutable.StringBuilder()
    for (i <- 0 until text.length) {
      val shift = {
        val alphabeticShift = {
          // wrap on key overflow
          if (i >= key.length) key(i - key.length)
          else key(i)
        }

        alphabeticShift - 97
      }

      val encodedChar = {
        val encodedCharIndex = applyShift(text(i), shift) - 97
        // wrap on alphabet overflow
        if (encodedCharIndex > 25) alpha(encodedCharIndex - 26)
        else if (encodedCharIndex < 0) alpha(26 - encodedCharIndex)
        else alpha(encodedCharIndex)
      }

      sb.append(encodedChar)
    }
    sb.toString()
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