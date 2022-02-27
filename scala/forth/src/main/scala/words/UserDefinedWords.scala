package words

import error.ForthError
import error.ForthError.ForthError

import scala.collection.mutable

trait UserDefinedWords {

  private val userDefinedWords: mutable.Map[String, String] = mutable.Map()

  def userDefinedWordExists(word: String): Boolean = {
    userDefinedWords.contains(word)
  }
//  def lastUpdatedUserDefinedWord: Option[String] = _lastUpdated
//
//  def overrideLastUpdatedUserDefinedWord(word: String): Unit = {
//    _lastUpdated = Some(word)
//  }

  private var lastUpdated: Option[String] = None

  def setUserDefinedWord(word: String = lastUpdated.getOrElse(""), definition: String = NoOp.toString): Either[ForthError, Unit] = {
    if (word == "") Left(ForthError.InvalidWord)
    else {
      lastUpdated = Some(word)
      Right(userDefinedWords.update(word, definition))
    }
  }

  def getUserDefinedWord(word: String): Either[ForthError, ForthWord] = {
    userDefinedWords.get(word) match {
      case None => Left(ForthError.UnknownWord)
      case Some(definition) => Right(UserDefinedWord(definition))
    }
  }

}
