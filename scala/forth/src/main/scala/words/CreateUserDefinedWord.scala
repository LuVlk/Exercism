package words
import error.ForthError
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState
import evaluator.ForthEvaluatorState.CreateNewWord

case class CreateUserDefinedWord(word: String) extends ForthWord {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), createUserDefinedWord)
  }

  def createUserDefinedWord(state: ForthEvaluatorState): Either[ForthError, ForthEvaluatorState] = {
    if (state.getState != CreateNewWord) Left(ForthError.InvalidWord)
    else {
      state.setUserDefinedWord(word = word)
      state.initializeWordDefinition()
    }
  }
}
