package words
import error.ForthError
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

object Duplication extends ForthWord {

  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), duplicateLastValue)
  }

  def duplicateLastValue(state: ForthEvaluatorState): Either[ForthError, ForthEvaluatorState] = {
    val v = state.pop().getOrElse(return Left(ForthError.StackUnderflow))._1
    state.push(v)
    state.push(v)
    Right(state)
  }
}
