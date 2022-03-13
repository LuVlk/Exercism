import org.scalatest.{FunSuite, Matchers}

class AlphameticsCompilerTest extends FunSuite with Matchers {

  import Alphametics._

  test("compile simple equation") {
    val testee = AlphameticsCompiler.compile(Equals(Variable("A"), Variable("B")))
    assert(testee.right.get.eval(Map('A' -> 1, 'B' -> 1)).right.get)
    assert(!testee.right.get.eval(Map('A' -> 1, 'B' -> 2)).right.get)
  }

  test("produces runtime error if char was not found in alphametics solution") {
    val testee = AlphameticsCompiler.compile(Equals(Variable("A"), Variable("B")))
    assert(testee.right.get.eval(Map('B' -> 1, 'C' -> 2)) === Left(RuntimeError("letter 'A' was not found")))
  }

  test("produces runtime error if first char in multi letters variable is equal to zero") {
    val testee = AlphameticsCompiler.compile(Equals(Variable("ABC"), Variable("B")))
    assert(testee.right.get.eval(Map('A' -> 0, 'B' -> 1, 'C' -> 2)) === Left(RuntimeError("solution with leading 0 in multi letter variable is not allowed (variable: 'ABC')")))
  }

  test("invalid AST") {
    assert(AlphameticsCompiler.compile(Variable("A")).isLeft)
    assert(AlphameticsCompiler.compile(Add(Variable("A"), Variable("A"))).isLeft)
    assert(AlphameticsCompiler.compile(Equals(Equals(Variable("A"), Variable("A")), Variable("A"))).isLeft)
  }
}
