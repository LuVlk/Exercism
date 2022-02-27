package words

import error.ForthError
import error.ForthError.ForthError

trait BuiltInWords {

  private val builtInWords: Map[String, ForthWord] = Map(
    "+" -> Addition,
    "-" -> Subtraction,
    "*" -> Multiplication,
    "/" -> Division,
    "dup" -> Duplication,
    "drop" -> Drop,
    "swap" -> Swap,
    "over" -> Over,
    InitializeWordCreation.toString -> InitializeWordCreation,
    FinalizeWordDefinition.toString -> FinalizeWordDefinition,
    NoOp.toString -> NoOp
  )

  def isWordDefinitionFinalizer(word: String): Boolean = word == FinalizeWordDefinition.toString

  def getBuiltInWord(word: String): Either[ForthError, ForthWord] = {
    builtInWords.get(word) match {
      case None => Left(ForthError.UnknownWord)
      case Some(word) => Right(word)
    }
  }

}
