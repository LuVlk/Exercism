object SecretHandshake {

  private val handshake =
    List(
      (1 << 0, (cmnds: List[String]) => cmnds :+ "wink"),
      (1 << 1, (cmnds: List[String]) => cmnds :+ "double blink"),
      (1 << 2, (cmnds: List[String]) => cmnds :+ "close your eyes"),
      (1 << 3, (cmnds: List[String]) => cmnds :+ "jump"),
      (1 << 4, (cmnds: List[String]) => cmnds.reverse))

  def commands(number: Int): List[String] = handshake.foldLeft[List[String]](Nil) {
    case (cmnds, (mask, applyHandshake)) =>
      if ((number & mask) != 0) applyHandshake(cmnds)
      else cmnds
  }
}
