package words
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

object InitializeWordCreation extends ForthWord {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), _.initializeWordCreation())
  }
  override def toString: String = ":"
}
