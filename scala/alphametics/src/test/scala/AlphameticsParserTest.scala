import org.scalatest.{FunSuite, Matchers}

class AlphameticsParserTest extends FunSuite with Matchers {

  import Alphametics._
  import Alphametics.AlphameticsParser._

  def testParser(parser: Parser[AlphameticsAST])
           (expression: String): Either[ParserError, AlphameticsAST] = {
    parse(parser, expression) match {
      case NoSuccess(msg, _) => Left(ParserError(msg))
      case Success(result, _) => Right(result)
    }
  }

  test("variable") {
    val p = testParser(AlphameticsParser.variable) _

    assert(p("A").right.get === Variable("A"))
    assert(p("BA").right.get === Variable("BA"))
    assert(p("BBAC").right.get === Variable("BBAC"))

    assert(p("a").isLeft)
    assert(p("0").isLeft)
  }

  test("expression") {
    val p = testParser(AlphameticsParser.expression) _

    assert(p("A + B").right.get === Add(Variable("A"), Variable("B")))
    assert(p("A + B + C").right.get === Add(Add(Variable("A"), Variable("B")), Variable("C")))

    assert(p("A +").right.get === Variable("A"))
    assert(p("A == B").right.get === Variable("A"))
  }

  test("equation") {
    val p = testParser(AlphameticsParser.equation) _

    assert(p("A == B").right.get === Equals(Variable("A"), Variable("B")))
    assert(p("A == B == C").right.get === Equals(Variable("A"), Variable("B"))) // only first one equation is parsed
    assert(p("A + B == B + C == C").right.get === Equals(
      Add(Variable("A"), Variable("B")),
      Add(Variable("B"), Variable("C"))
    ))

    assert(p("A").isLeft)
  }

  test("alphametics") {
    val p = testParser(AlphameticsParser.alphametics) _

    assert(p("A == B").right.get === Equals(Variable("A"), Variable("B")))
    assert(p("A + B == B + C").right.get === Equals(
      Add(Variable("A"), Variable("B")),
      Add(Variable("B"), Variable("C"))
    ))

    assert(p("A == B == C").isLeft)
    assert(p("A").isLeft)
  }

  test("parses alphametics and creates trivial solution") {
    assert(AlphameticsParser.parse("A == B") === (
      Right(Equals(Variable("A"), Variable("B"))),
      Some(Map('A' -> 0, 'B' -> 0))
    ))
  }

  test("creates solution with unique letters") {
    assert(AlphameticsParser.parse("ABB == CBA") === (
      Right(Equals(Variable("ABB"), Variable("CBA"))),
      Some(Map('A' -> 0, 'B' -> 0, 'C' -> 0))
    ))
  }

  test("order within solution does not matter") {
    assert(AlphameticsParser.parse("CBA == CBA") === (
      Right(Equals(Variable("CBA"), Variable("CBA"))),
      Some(Map('A' -> 0, 'B' -> 0, 'C' -> 0))
    ))

    assert(AlphameticsParser.parse("ABC == ABC") === (
      Right(Equals(Variable("ABC"), Variable("ABC"))),
      Some(Map('A' -> 0, 'B' -> 0, 'C' -> 0))
    ))
  }

  test("produces same results every time") {
    assert(AlphameticsParser.parse("A == B") === (
      Right(Equals(Variable("A"), Variable("B"))),
      Some(Map('A' -> 0, 'B' -> 0))
    ))
    assert(AlphameticsParser.parse("A == B") === (
      Right(Equals(Variable("A"), Variable("B"))),
      Some(Map('A' -> 0, 'B' -> 0))
    ))
  }

  test("does not return a solution if parsing fails") {
    assert(AlphameticsParser.parse("A") === (
      Left(ParserError("string matching regex `==' expected but end of source found")),
      None
    ))
  }
}
