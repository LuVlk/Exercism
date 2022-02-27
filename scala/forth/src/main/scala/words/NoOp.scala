package words
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

object NoOp extends ForthWord {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = state
  override def toString: String = "noop"
}
