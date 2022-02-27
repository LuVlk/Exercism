import ForthError.ForthError

import scala.util.parsing.combinator.RegexParsers

object ForthError extends Enumeration {
  type ForthError = Value
  val DivisionByZero, StackUnderflow, InvalidWord, UnknownWord = Value
}

trait ForthEvaluatorState

case class ForthState(stack: List[Int], mapping: Map[String, List[Definition]]) extends ForthEvaluatorState {
  override def toString = stack.reverse.mkString(" ")
}

object ForthState {
  val empty = ForthState(Nil, Map())
}

trait Definition {
  def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState]
  def evaluateTerm(term: String, state: Either[ForthError, ForthEvaluatorState])(normal: (ForthState => Either[ForthError, ForthEvaluatorState])): Either[ForthError, ForthEvaluatorState] =
    state.right.flatMap {
      case ForthState(stack, mapping) =>
        mapping.get(term) match {
          case Some(definitions) =>
            definitions.foldLeft(state) {
              case (acc, definition) => definition.evaluate(acc)
            }
          case None => normal(ForthState(stack, mapping))
        }
    }
}

sealed abstract class UnaryOperation(term: String) extends Definition {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] =
    evaluateTerm(term, state) {
      case ForthState(stack, mapping) => stack match {
        case x :: xs => operation(x).right.map(z => ForthState(z ++ xs, mapping))
        case _ => Left(ForthError.StackUnderflow)
      }
    }
  def operation(x: Int): Either[ForthError, List[Int]]
}

sealed abstract class BinaryOperation(term: String) extends Definition {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] =
    evaluateTerm(term, state) {
      case ForthState(stack, mapping) => stack match {
        case x :: y :: xs => operation(y, x).right.map(z => ForthState(z ++ xs, mapping))
        case _ => Left(ForthError.StackUnderflow)
      }
    }
  def operation(x: Int, y: Int): Either[ForthError, List[Int]]
}

case class Constant(n: Int) extends Definition {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] =
    state.right.flatMap {
      case ForthState(stack, mapping) => Right(ForthState(n :: stack, mapping))
    }
}

case class Word(term: String) extends Definition {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] =
    evaluateTerm(term, state) { _ => Left(ForthError.InvalidWord) }
}

case class CustomTerm(term: String, actions: List[Definition]) extends Definition {
  override def evaluate(state: Either[ForthError, ForthEvaluatorState]): Either[ForthError, ForthEvaluatorState] = {
    state.right.flatMap {
      case ForthState(stack, mapping) =>
        if (term.matches("""\d+""")) Left(ForthError.InvalidWord)
        else {
          resolveCustomTerms(mapping).fold(
            Left(_), resolvedActions => Right(ForthState(stack, mapping.updated(term, resolvedActions))))
        }
    }
  }

  private def resolveCustomTerms(mapping: Map[String, List[Definition]]): Either[ForthError, List[Definition]] =
    Right(actions.flatMap {
      case Word(word) => mapping.get(word) match {
        case None => return Left(ForthError.InvalidWord)
        case Some(actions) => Some(actions)
      }
      case action => Some(List(action))
    }.flatten)
}

object Dup extends UnaryOperation("dup") {
  override def operation(x: Int) = Right(List(x, x))
}

object Drop extends UnaryOperation("drop") {
  override def operation(x: Int) = Right(Nil)
}

object Swap extends BinaryOperation("swap") {
  override def operation(x: Int, y: Int) = Right(List(x, y))
}

object Over extends BinaryOperation("over") {
  override def operation(x: Int, y: Int) = Right(List(x, y, x))
}

object Plus extends BinaryOperation("+") {
  override def operation(x: Int, y: Int) = Right(List(x + y))
}

object Minus extends BinaryOperation("-") {
  override def operation(x: Int, y: Int) = Right(List(x - y))
}

object Multiply extends BinaryOperation("*") {
  override def operation(x: Int, y: Int) = Right(List(x * y))
}

object Division extends BinaryOperation("/") {
  override def operation(x: Int, y: Int): Either[ForthError, List[Int]] =
    if (y == 0) Left(ForthError.DivisionByZero)
    else Right(List(x / y))
}

trait ForthEvaluator {
  def eval(text: String): Either[ForthError, ForthEvaluatorState]
}

class Forth extends RegexParsers with ForthEvaluator {
  override val whiteSpace = """(?U)(\s|[\u0000-\u001f])+""".r

  def constant: Parser[Constant] = """\d+""".r ^^ (n => Constant(n.toInt))

  def termIdentifier: Parser[String] = """(?U)[[^\s]&&[^\u0000-\u001f]&&[^\d]&&[^;]]+""".r ^^ (x => x.toLowerCase())

  def term: Parser[Definition] = termIdentifier ^^ {
    case "+" => Plus
    case "-" => Minus
    case "*" => Multiply
    case "/" => Division
    case "dup" => Dup
    case "drop" => Drop
    case "swap" => Swap
    case "over" => Over
    case word => Word(word)
  }

  def customTermIdentifier: Parser[String] = """(?U)[[^\s]&&[^\u0000-\u001f]&&[^;]]+""".r ^^ (x => x.toLowerCase())

  def customTerm: Parser[Definition] = (":" ~> customTermIdentifier ~ rep1(constant | term) <~ ";") ^^ {
    case (term ~ definitions) => CustomTerm(term, definitions)
  }

  def expression: Parser[List[Definition]] = rep(customTerm | constant | term)

  def eval(definitions: List[Definition]): Either[ForthError, ForthEvaluatorState] = {
    definitions.foldLeft[Either[ForthError, ForthEvaluatorState]](Right(ForthState.empty)) {
      case (state, definition) => definition.evaluate(state)
    }
  }

  override def eval(text: String): Either[ForthError, ForthEvaluatorState] = {
    parse(expression, text.toLowerCase) match {
      case Success(definitions, _) => eval(definitions)
      case _ => Left(ForthError.InvalidWord)
    }
  }
}

object Forth {
  def apply(text: String) = new Forth
}