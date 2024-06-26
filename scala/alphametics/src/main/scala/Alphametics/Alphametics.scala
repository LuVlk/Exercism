import scala.util.matching.Regex
import scala.util.parsing.combinator.JavaTokenParsers

package object Alphametics {

  type AlphameticsSolution = Map[Char, Int]
  def solve(expr: String): Option[AlphameticsSolution] = {
    val parsingResultAndStartSolution = AlphameticsParser.parse(expr)
    val parsingResult = parsingResultAndStartSolution._1
    val startSolution = parsingResultAndStartSolution._2
    ( for {
      ast <- parsingResult
      evaluator <- AlphameticsCompiler.compile(ast)
      solution <- AlphameticsSolver.solve(startSolution.get, evaluator)
    } yield solution ).fold(
      _ => None,
      sln => Some(sln)
    )
  }

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

    def variable: Parser[AlphameticsAST] = Variable.asInstanceOf[Regex] ^^ {
      letters => letters.foreach(c => solution = solution.updated(c, 0)); Variable(letters)
    }

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
        case Success(result, _) =>
          if (solution.size > 10) (Left(ParserError("exceeded maximum number of unique letters (10)")), None)
          else (Right(result), Some(solution))
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

      /** Convert letters to string of numbers and parse to int.
       *  Runtime error cases:
       *  - if letter was not found in evaluation input.
       *  - if leading letter in multi letter variable is equal to 0.
       */
      case Variable(letters) => Right(
        AlphameticsEvaluator(solution => letters.foldLeft[EvaluationResult[String]](Right(""))(
          (evalR, char) => evalR.fold(Left(_), acc => solution.get(char) match {
            case None => Left(RuntimeError(s"letter '$char' was not found"))
            case Some(value) if letters.length > 1 && letters.charAt(0) == char && value == 0 =>
              Left(RuntimeError(s"solution with leading 0 in multi letter variable is not allowed (variable: '$letters')"))
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

  object AlphameticsSolver {

    /** solves alphametics puzzle using following algorithm:
     *
     * 0 test startSolution and add to solutions on success
     *
     * 1
     * if value less than 9 increase value of first key to next possible value --
     *   test and add to solutions on success --
     *   repeat 1 --
     * reset value of first key
     *
     * 2
     * if value is less that 9 increase value of second key to next possible value --
     *   test and add to solutions on success --
     *   repeat 1 --
     *   repeat 2 --
     * reset value of second key
     *
     * ... continue to last key
     */
    def solve(startSolution: AlphameticsSolution, solutionEvaluator: AlphameticsEvaluator[Boolean]): Either[RuntimeError, AlphameticsSolution] = {

      var solutions: Set[AlphameticsSolution] = Set()
      var testee = startSolution
      if (solutionEvaluator.eval(testee).getOrElse(false)) solutions += testee

      var previousLetters: Set[Char] = Set()

      def nextValueFor(letter: Char): Int = {
        val reserved = testee.dropWhile(item => item._1 == letter).values.toSet
        var next = testee(letter) + 1
        while (reserved.contains(next)) next += 1
        next
      }

      def generateAndEvaluateSolutionsFor(letters: Iterable[Char]): Unit = {
        letters.foreach(letter => {
          val initial = testee(letter)
          while (testee(letter) < 9) {
            testee = testee.updated(letter, nextValueFor(letter))
            if (solutionEvaluator.eval(testee).getOrElse(false)) solutions += testee
            generateAndEvaluateSolutionsFor(previousLetters)
          }
          testee = testee.updated(letter, initial)
          previousLetters += letter // using Set so recursive calls do not add additional previous letters
        })
      }

      generateAndEvaluateSolutionsFor(testee.keys)

      // only keep solutions with unique values
      solutions = solutions.takeWhile(solution => solution.values.toSet.size == solution.size)
      if (solutions.isEmpty) Left(RuntimeError("no solution found"))
      else if (solutions.size > 1) Left(RuntimeError("multiple solutions are not allowed"))
      else Right(solutions.head)
    }
  }
}