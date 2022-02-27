case class Triangle(s1: Double, s2: Double, s3: Double) {

  def isTriangle: Boolean = {
    s1 > 0 &
    s2 > 0 &
    s3 > 0 &
    ( s1 + s2 >= s3 &
      s2 + s3 >= s1 &
      s1 + s3 >= s2)
  }

  def degenerate: Boolean = {
    isTriangle &
    (s1 + s2 == s3 |
     s2 + s3 == s1 |
     s1 + s3 == s2)
  }

  def equilateral: Boolean = {
    isTriangle & s1 == s2 & s1 == s3
  }

  def isosceles: Boolean = {
    isTriangle & (s1 == s2 | s2 == s3 | s3 == s1)
  }

  def scalene: Boolean = {
    isTriangle & !isosceles
  }
}