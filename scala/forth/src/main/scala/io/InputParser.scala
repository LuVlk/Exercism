package io
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState
import words.ForthWord

object InputParser {

  def parse(word: String, state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthWord] = {
    state.fold(Left(_), s => parseWord(word, s))
  }

  private def parseWord(word: String, state: ForthEvaluatorState): Either[ForthError, ForthWord] = {
    if (state.userDefinedWordExists(word)) state.getUserDefinedWord(word)
    else state.getBuiltInWord(word)
  }
}