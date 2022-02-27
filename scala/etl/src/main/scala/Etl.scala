object Etl {

  private def makeScrabbleMap(score: Int, letters: Seq[String]): Map[String, Int] = {
    Map(letters.map(letter => letter.toLowerCase -> score): _*)
  }

  def transform(scores: Map[Int, Seq[String]]): Map[String, Int] = {
    scores.map(pair => makeScrabbleMap(pair._1, pair._2)).reduce(_ ++ _)
  }
}