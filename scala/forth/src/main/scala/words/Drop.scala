package words

import error.ForthError
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

object Drop extends ForthWord {

  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), dropLastValue)
  }

  def dropLastValue(state: ForthEvaluatorState): Either[ForthError, ForthEvaluatorState] = {
    state.pop().getOrElse(return Left(ForthError.StackUnderflow))
    Right(state)
  }
}
