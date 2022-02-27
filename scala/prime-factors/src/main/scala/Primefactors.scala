object PrimeFactors {

  def factors(num: Long, divisor: Long = 2): List[Long] = {
    if (num <= 0)
      throw new IllegalArgumentException(s"num must greater than 0, got $num.")

    if (num == 1) List()
    else if (num % divisor == 0) divisor +: factors(num / divisor, divisor)
    else factors(num, divisor + 1)
  }
}