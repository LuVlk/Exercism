package words

import error.ForthError
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

object Over extends ForthWord {

  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), swapLastTwoValues)
  }

  def swapLastTwoValues(state: ForthEvaluatorState): Either[ForthError, ForthEvaluatorState] = {
    val v2 = state.pop().getOrElse(return Left(ForthError.StackUnderflow))._1
    val v1 = state.pop().getOrElse(return Left(ForthError.StackUnderflow))._1
    state.push(v1)
    state.push(v2)
    state.push(v1)
    Right(state)
  }
}
