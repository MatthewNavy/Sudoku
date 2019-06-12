package sudoku

class Cell(_x: Int, _y: Int, var value: String, _isGiven: Boolean)
{
  def x = _x
  def y = _y
  def isGiven = _isGiven

  def updateValue(v: String): Unit = value = v
}