package words

import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

trait ForthWord {
  def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState]
}