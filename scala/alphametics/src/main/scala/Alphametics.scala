import scala.util.matching.Regex
import scala.util.parsing.combinator.JavaTokenParsers

case class AlphameticsParserError(msg: String) extends Throwable(msg)
case class AlphameticsCompilerError(msg: String) extends Throwable(msg)

trait AlphameticsAST

object Variable extends Regex("[A-Z]+")
case class Variable(letters: String) extends AlphameticsAST {
  override def toString: String = letters
}
object Add extends Regex("\\+")
case class Add(ast1: AlphameticsAST, ast2: AlphameticsAST) extends AlphameticsAST {
  override def toString: String = s"(+, $ast1, $ast2)"
}
object Equals extends Regex("==")
case class Equals(ast1: AlphameticsAST, ast2: AlphameticsAST) extends AlphameticsAST {
  override def toString: String = s"(==, $ast1, $ast2)"
}

object AlphameticsParser extends JavaTokenParsers {
  def variable: Parser[AlphameticsAST] = Variable.asInstanceOf[Regex] ^^ (letter => Variable(letter))

  def expression: Parser[AlphameticsAST] = variable ~ rep(Add ~ variable) ^^ {
    case ast ~ asts => asts.foldLeft[AlphameticsAST](ast) {
      case (ast1, _ ~ ast2) => new Add(ast1, ast2)
    }
  }

  def equation: Parser[AlphameticsAST] = expression ~ Equals ~ expression ^^ {
    case ast1 ~ _ ~ ast2 => new Equals(ast1, ast2)
  }

  def alphametics: Parser[AlphameticsAST] = phrase(equation)

  def apply(text: String): Either[AlphameticsParserError, AlphameticsAST] = {
    parse(alphametics, text) match {
      case NoSuccess(msg, _) => Left(AlphameticsParserError(msg))
      case Success(result, _) => Right(result)
    }
  }
}

object AlphameticsCompiler {
  case class AlphameticsEvaluator[A](eval: Map[Char, Int] => A)
  type AlphameticsCompilationResult[A] = Either[AlphameticsCompilerError, AlphameticsEvaluator[A]]

  private def map2[A, B](acr1: AlphameticsCompilationResult[A], acr2: AlphameticsCompilationResult[A])
                (eval2: (A, A) => B): AlphameticsCompilationResult[B] = for {
    leftEval <- acr1
    rightEval <- acr2
  } yield AlphameticsEvaluator(input => eval2(leftEval.eval(input), rightEval.eval(input)))

  private def compileExpression(ast: AlphameticsAST): AlphameticsCompilationResult[Int] = ast match {
    case Variable(letters) => Right(AlphameticsEvaluator(f => letters.map(c => f(c).toString.charAt(0)).toInt))
    case Add(ast1, ast2) => map2(compileExpression(ast1), compileExpression(ast2))(_ + _)
    case _ => Left(AlphameticsCompilerError(s"Unexpected AST node $ast"))
  }

  def compile(ast: AlphameticsAST): AlphameticsCompilationResult[Boolean] = ast match {
    case Equals(ast1, ast2) => map2(compileExpression(ast1), compileExpression(ast2))(_ == _)
    case _ => Left(AlphameticsCompilerError(s"Unexpected AST node $ast"))
  }
}

object Alphametics {

  def solve(expr: String): Option[Map[Char, Int]] = ???

}