import scala.collection.mutable
import scala.util.{Success, Try}

object House {

  private val phrases = Map(
    1  -> "the house that Jack built",
    2  -> "the malt that lay in",
    3  -> "the rat that ate",
    4  -> "the cat that killed",
    5  -> "the dog that worried",
    6  -> "the cow with the crumpled horn that tossed",
    7  -> "the maiden all forlorn that milked",
    8  -> "the man all tattered and torn that kissed",
    9  -> "the priest all shaven and shorn that married",
    10 -> "the rooster that crowed in the morn that woke",
    11 -> "the farmer sowing his corn that kept",
    12 -> "the horse and the hound and the horn that belonged to"
  )

  private val verseCache = mutable.Map[Int, String]()

  def verse(number: Int): Try[String] = number match {
    case n if verseCache.contains(number) => Success(verseCache(n))
    case 1 => Success(phrases(1))

    case n if n > 1 => {
      val v = s"${phrases(number)} ${verse(number - 1).get}"
      verseCache.update(n, v)
      Success(v)
    }

    case _ => throw new IllegalArgumentException
  }

  def recite(begin: Int, end: Int): String = {
    (begin to end).map(num => s"This is ${verse(num).get}.\n").mkString("") concat "\n"
  }
}