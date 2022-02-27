import error.ForthError.ForthError
import evaluator.{ForthEvaluator, ForthEvaluatorState}

class Forth extends ForthEvaluator {
  def eval(text: String): Either[ForthError, ForthEvaluatorState] = {
    super.eval(text, Right(new ForthEvaluatorState))
  }
}