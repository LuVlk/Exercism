class AndomraysBob extends Responses {

  import AndomraysBob.{Predicate, Rule, RuleMaker}

  def response:(String => String) = (
    shoutingQuestion
    orElse shouting
    orElse question
    orElse silence
    orElse default)

  //RULES
  private val default : Rule  = {case _ => WHATEVER}
  private val shouting: Rule = (allUpperCase ?> CHILL_OUT)
  private val question: Rule = (isQuestion   ?> SURE)
  private val shoutingQuestion = (isShoutingQuestion ?> CALM_DOWN)
  private val silence : Rule = (isSilence    ?> FINE)

  //PREDICATES
  private def allUpperCase:Predicate = s => !s.exists(_.isLower) && s.exists(_.isUpper)
  private def isQuestion  :Predicate = _.trim.endsWith("?")
  private def isSilence   :Predicate = _.forall(_.isWhitespace)
  private def isShoutingQuestion:Predicate = s => isQuestion(s) && allUpperCase(s)
}

object AndomraysBob {

  type Predicate = (String => Boolean)
  type Rule = PartialFunction[String, String]

  implicit class RuleMaker(val pred:Predicate) extends AnyVal {
    def ?>(s:String):Rule = { case x if(pred(x)) => s }
  }
}

trait Responses {
  val CHILL_OUT = "Whoa, chill out!"
  val CALM_DOWN = "Calm down, I know what I'm doing!"
  val WHATEVER  = "Whatever."
  val SURE      = "Sure."
  val FINE      = "Fine. Be that way!"
}
