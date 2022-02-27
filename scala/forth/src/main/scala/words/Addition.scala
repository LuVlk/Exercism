package words
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

object Addition extends ForthWord with Reduce2to1 {

  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), addLastTwoValues)
  }

  def addLastTwoValues(state: ForthEvaluatorState): Either[ForthError, ForthEvaluatorState] = {
    reduce2to1(state, (v1, v2) => Right(v1 + v2))
  }
}
