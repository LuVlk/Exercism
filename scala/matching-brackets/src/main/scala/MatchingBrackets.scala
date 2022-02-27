object MatchingBrackets {

  private val bracketPairs = Map(
    ')'->'(',
    ']'->'[',
    '}'->'{'
  )

  def isPaired(str: String): Boolean = {
    var openBrackets = List[Char]()
    for (c <- str) {
      if (bracketPairs.values.exists(_ == c)) {
        openBrackets = c +: openBrackets
      }
      else if (bracketPairs.keys.exists(_ == c)) {
        if (openBrackets.isEmpty) {
          return false
        }
        val lastOpenBracket +: remainingOpenBrackets = openBrackets
        if (lastOpenBracket != bracketPairs(c)) {
          return false
        }
        openBrackets = remainingOpenBrackets
      }
    }
    if (openBrackets.nonEmpty) {
      return false
    }
    true
  }
}