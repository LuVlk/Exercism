package words
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

object FinalizeWordDefinition extends ForthWord {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), _.finalizeWordDefinition())
  }
  override def toString: String = ";"
}
