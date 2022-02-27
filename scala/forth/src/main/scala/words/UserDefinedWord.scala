package words
import error.ForthError.ForthError
import evaluator.{ForthEvaluator, ForthEvaluatorState}

case class UserDefinedWord(definition: String) extends ForthWord with ForthEvaluator {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    eval(definition, state)
  }
}
