object PhoneNumber {
  def clean(number: String): Option[String] = {
    val digits = number.filter(_.isDigit).toList.map(_.toString.toInt)
    digits match {
      case 1 :: rest if digits.length == 11 & isValid(rest) => Some(rest.mkString(""))
      case digits if digits.length == 10 & isValid(digits) => Some(digits.mkString(""))
      case _ => None
    }
  }

  def isValid(number: Seq[Int]): Boolean = {
    (2 to 9).contains(number.head) &
      (2 to 9).contains(number(3)) &
      number.map((0 to 9).contains).reduce(_ & _)
  }
}