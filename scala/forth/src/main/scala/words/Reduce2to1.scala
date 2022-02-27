package words
import error.ForthError
import error.ForthError.ForthError
import evaluator.ForthEvaluatorState

trait Reduce2to1 {

  def reduce2to1(state: ForthEvaluatorState, fn: (Int, Int) => Either[ForthError, Int]): Either[ForthError, ForthEvaluatorState] = {
    val v2 = state.pop().getOrElse(return Left(ForthError.StackUnderflow))._1
    val v1 = state.pop().getOrElse(return Left(ForthError.StackUnderflow))._1
    fn(v1, v2).fold(Left(_), result => Right(state.push(result)))
  }
}
