object SpaceAge {

  def onEarth(age_in_second: Double): Double = age_in_second / 31557600
  def onMercury(age_in_second: Double): Double = onEarth(age_in_second) / 0.2408467
  def onVenus(age_in_second: Double): Double = onEarth(age_in_second) / 0.61519726
  def onMars(age_in_second: Double): Double = onEarth(age_in_second) / 1.8808158
  def onJupiter(age_in_second: Double): Double = onEarth(age_in_second) / 11.862615
  def onSaturn(age_in_second: Double): Double = onEarth(age_in_second) / 29.447498
  def onUranus(age_in_second: Double): Double = onEarth(age_in_second) / 84.016846
  def onNeptune(age_in_second: Double): Double = onEarth(age_in_second) / 164.79132
}