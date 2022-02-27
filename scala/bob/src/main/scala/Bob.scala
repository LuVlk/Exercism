object Bob {
  def response(statement: String): String = {

    if (statement.isBlank)
    {
      "Fine. Be that way!"
    }
    else
    {
      val trimmed = statement.trim
      (isYelling(trimmed), isQuestion(trimmed)) match
      {
        case (true, true) => "Calm down, I know what I'm doing!"
        case (true, false) => "Whoa, chill out!"
        case (false, true) => "Sure."
        case _ => "Whatever."
      }
    }
  }

  private val lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz"
  private val upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

  private def isQuestion(statement: String): Boolean = statement.endsWith("?")

  private def isYelling(statement: String): Boolean =
    !statement.exists(lowerCaseLetters contains _) &&
    statement.exists(upperCaseLetters contains _)
}
