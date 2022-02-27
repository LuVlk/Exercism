package evaluator

import error.ForthError.ForthError
import io.InputParser

trait ForthEvaluator  {

  def eval(text: String, state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    text.split(" ").foldLeft(state)((state, word) =>
      InputParser.parse(word, state).fold(Left(_), _.evaluate(state))
    )
  }
}