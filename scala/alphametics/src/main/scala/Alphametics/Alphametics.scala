import scala.util.matching.Regex
import scala.util.parsing.combinator.JavaTokenParsers

package object Alphametics {

  type AlphameticsSolution = Map[Char, Int]
  def solve(expr: String): Option[AlphameticsSolution] = ???

  case class ParserError(msg: String) extends Throwable(msg)
  case class CompilerError(msg: String) extends Throwable(msg)
  case class RuntimeError(msg: String) extends Throwable(msg)

  trait AlphameticsAST

  object Variable extends Regex("[A-Z]+")
  case class Variable(letters: String) extends AlphameticsAST

  object Add extends Regex("\\+")
  case class Add(ast1: AlphameticsAST, ast2: AlphameticsAST) extends AlphameticsAST

  object Equals extends Regex("==")
  case class Equals(ast1: AlphameticsAST, ast2: AlphameticsAST) extends AlphameticsAST

  type ParsingResult = Either[ParserError, AlphameticsAST]

  object AlphameticsParser extends JavaTokenParsers {
    var solution: AlphameticsSolution = Map()

    def variable: Parser[AlphameticsAST] = Variable.asInstanceOf[Regex] ^^
      { letters => letters.foreach(c => solution = solution.updated(c, 0)); Variable(letters) }

    def expression: Parser[AlphameticsAST] = variable ~ rep(Add ~ variable) ^^ {
      case ast ~ asts => asts.foldLeft[AlphameticsAST](ast) {
        case (ast1, _ ~ ast2) => new Add(ast1, ast2)
      }
    }

    def equation: Parser[AlphameticsAST] = expression ~ Equals ~ expression ^^ {
      case ast1 ~ _ ~ ast2 => new Equals(ast1, ast2)
    }

    def alphametics: Parser[AlphameticsAST] = phrase(equation)

    def parse(text: String): (ParsingResult, Option[AlphameticsSolution]) = {
      solution = Map() // clear map at start due to stateful parsing
      parse(alphametics, text) match {
        case NoSuccess(msg, _) => (Left(ParserError(msg)), None)
        case Success(result, _) => (Right(result), Some(solution))
      }
    }
  }

  type EvaluationResult[A] = Either[RuntimeError, A]
  case class AlphameticsEvaluator[A](eval: AlphameticsSolution => EvaluationResult[A])

  object AlphameticsCompiler {
    type CompilationResult[A] = Either[CompilerError, AlphameticsEvaluator[A]]

    private def map2[A, B](acr1: CompilationResult[A], acr2: CompilationResult[A])
                          (eval2: (EvaluationResult[A], EvaluationResult[A]) => EvaluationResult[B]): CompilationResult[B] =
      for {
        leftEval <- acr1
        rightEval <- acr2
      } yield AlphameticsEvaluator(input => eval2(leftEval.eval(input), rightEval.eval(input)))

    private def compileExpression(ast: AlphameticsAST): CompilationResult[Int] = ast match {

      // Convert letters to string of numbers and parse to int.
      // Return runtime error if letter was not found in evaluation input.
      case Variable(letters) => Right(
        AlphameticsEvaluator(solution => letters.foldLeft[EvaluationResult[String]](Right(""))(
          (evalR, c) => evalR.fold(Left(_), acc => solution.get(c) match {
            case None => Left(RuntimeError(s"key '$c' was not found"))
            case Some(value) => Right(acc + value.toString)
          })
        ).fold(Left(_), s => Right(s.toInt)))
      )

      case Add(ast1, ast2) => map2(compileExpression(ast1), compileExpression(ast2))(
        (evalR1, evalR2) => for { result1 <- evalR1; result2 <- evalR2 } yield result1 + result2 )

      case _ => Left(CompilerError(s"Unexpected AST node $ast"))
    }

    def compile(ast: AlphameticsAST): CompilationResult[Boolean] = ast match {
      case Equals(ast1, ast2) => map2(compileExpression(ast1), compileExpression(ast2))(
        (evalR1, evalR2) => for { result1 <- evalR1; result2 <- evalR2 } yield result1 == result2 )
      case _ => Left(CompilerError(s"Unexpected AST node $ast"))
    }
  }
}