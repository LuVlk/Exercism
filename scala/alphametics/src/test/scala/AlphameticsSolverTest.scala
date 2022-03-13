import org.scalatest.{FunSuite, Matchers}

class AlphameticsSolverTest extends FunSuite with Matchers {

  import Alphametics._

  test("solve simple puzzle") {

    val startSolution = Map('I' -> 0, 'B' -> 0, 'L' -> 0)
    val evaluator = AlphameticsCompiler.compile(
      Equals(
        Add(
          Variable("I"),
          Variable("BB")
        ),
        Variable("ILL")
      )
    ).right.get

    assert(AlphameticsSolver.solve(startSolution, evaluator).right.get === Map('B' -> 9, 'I' -> 1, 'L' -> 0))
  }
}
