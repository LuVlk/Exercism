package words
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

case class Number(value: Int) extends ForthWord {

  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), s => Right(s.push(value)))
  }
}
