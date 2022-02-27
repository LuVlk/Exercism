package words
import error.ForthError
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState
import evaluator.ForthEvaluatorState.DefineNewWord

class DefineUserDefinedWord(word: String) extends ForthWord {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.fold(Left(_), defineUserDefinedWord)
  }

  def defineUserDefinedWord(state: ForthEvaluatorState): Either[ForthError, ForthEvaluatorState] = {
    if (state.getState != DefineNewWord) Left(ForthError.InvalidWord)
    else if (state.isWordDefinitionFinalizer(word)) state.finalizeWordDefinition()
    else {
      state.setUserDefinedWord(definition = word)
      Right(state)
    }
  }
}
