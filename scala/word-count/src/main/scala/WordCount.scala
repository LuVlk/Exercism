case class WordCount(text: String) {

  def countWords: Map[String, Int] = {
    val words = text.toLowerCase
      .replaceAll("[!@$%^&:.]","")
      .split("[ ,\n]")
      .filter(_.nonEmpty)
      .map(_.stripPrefix("'").stripSuffix("'"))

    words.foldLeft(Map[String, Int]())((map, word) => map.get(word) match {
      case None => map.updated(word, 1)
      case Some(count) => map.updated(word, count + 1)
    })
  }

}