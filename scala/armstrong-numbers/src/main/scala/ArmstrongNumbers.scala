object ArmstrongNumbers {

  def isArmstrongNumber(number: Int): Boolean = {
    val digits = number.toString.map(_.asDigit)
    number == digits.map(Math.pow(_, digits.length)).sum
  }
}