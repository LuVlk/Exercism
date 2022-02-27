package evaluator
import error.ForthError
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState.{CreateNewWord, DefineNewWord, Evaluate}
import stack.ForthStack
import words.{BuiltInWords, CreateUserDefinedWord, ForthWord, NoOp, Number, UserDefinedWords}

class ForthEvaluatorState extends ForthStack with BuiltInWords with UserDefinedWords {

  private var evaluatorState = Evaluate

  private def isWordCreationInitializer(word: String) = word == ":"

  def getState: ForthEvaluatorState.ForthEvaluatorState = evaluatorState

  private def switchState(
    to: ForthEvaluatorState.ForthEvaluatorState,
    from: ForthEvaluatorState.ForthEvaluatorState): Either[ForthError, ForthEvaluatorState] = {

    if (evaluatorState != from) Left(ForthError.InvalidWord)
    else {
      evaluatorState = to
      Right(this)
    }
  }
  def initializeWordCreation(): Either[ForthError, ForthEvaluatorState] = switchState(CreateNewWord, Evaluate)
  def initializeWordDefinition(): Either[ForthError, ForthEvaluatorState] = switchState(DefineNewWord, CreateNewWord)
  def finalizeWordDefinition(): Either[ForthError, ForthEvaluatorState] = switchState(Evaluate, DefineNewWord)

  override def getUserDefinedWord(word: String): Either[ForthError, ForthWord] = evaluatorState match {
    case Evaluate => super.getUserDefinedWord(word)
  }

  override def getBuiltInWord(word: String): Either[ForthError, ForthWord] = evaluatorState match {
    case Evaluate if isWordCreationInitializer(word) => evaluatorState = CreateNewWord; getBuiltInWord(NoOp.toString)
    case Evaluate => getNumberOrWord(word)
    case CreateNewWord => Right(CreateUserDefinedWord(word))
    case DefineNewWord => Right(DefineUserDefinedWord(word))
  }

  private def isAllDigit(str: String): Boolean = {
    str.toCharArray.map(_.isDigit).reduce(_ && _)
  }

  private def getNumberOrWord(word: String): Either[ForthError, ForthWord] = word match {
    case w if isAllDigit(w) => Right(Number(w.toInt))
    case _ => super.getBuiltInWord(word)
  }
}

object ForthEvaluatorState extends Enumeration {
  type ForthEvaluatorState = Value
  val Evaluate, CreateNewWord, DefineNewWord = Value
}