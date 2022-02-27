object BeerSong {

  private def verse(numBeers: Int): Option[String] = numBeers match {
    case 0 =>
      Some(s"""No more bottles of beer on the wall, no more bottles of beer.\nGo to the store and buy some more, 99 bottles of beer on the wall.\n""")
    case 1 =>
      Some(s"""1 bottle of beer on the wall, 1 bottle of beer.\nTake it down and pass it around, no more bottles of beer on the wall.\n""")
    case 2 =>
      Some(s"""2 bottles of beer on the wall, 2 bottles of beer.\nTake one down and pass it around, 1 bottle of beer on the wall.\n""")
    case numBeers if (99 >= numBeers) && (numBeers > 2) =>
      Some(s"""$numBeers bottles of beer on the wall, $numBeers bottles of beer.\nTake one down and pass it around, ${numBeers - 1} bottles of beer on the wall.\n""")
    case _ => None
  }

  def recite(numBeers: Int, numVerses: Int): String =
    ((numBeers - (numVerses - 1)) to numBeers).sorted(Ordering.Int.reverse).map(verse(_).getOrElse("")).mkString("\n")
}