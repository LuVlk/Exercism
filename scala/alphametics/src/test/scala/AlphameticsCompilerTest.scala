import org.scalatest.{FunSuite, Matchers}

class AlphameticsCompilerTest extends FunSuite with Matchers {

  test("compile simple equation") {
    val testee = AlphameticsCompiler.compile(Equals(Variable("A"), Variable("B")))
    assert(testee.right.get.eval(Map('A' -> 1, 'B' -> 1)))
    assert(!testee.right.get.eval(Map('A' -> 1, 'B' -> 2)))
  }

  test("invalid AST") {
    assert(AlphameticsCompiler.compile(Variable("A")).isLeft)
    assert(AlphameticsCompiler.compile(Add(Variable("A"), Variable("A"))).isLeft)
    assert(AlphameticsCompiler.compile(Equals(Equals(Variable("A"), Variable("A")), Variable("A"))).isLeft)
  }
}
