package words
import error.ForthError
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

object Division extends ForthWord with Reduce2to1 {

  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), divideLastTwoValues)
  }

  def divideLastTwoValues(state: ForthEvaluatorState): Either[ForthError, ForthEvaluatorState] = {
    reduce2to1(state, (v1, v2) => {
      if (v2 == 0) Left(ForthError.DivisionByZero)
      else Right(v1 / v2)
    })
  }
}
