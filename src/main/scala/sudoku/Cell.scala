package sudoku

class Cell(_x: Int, _y: Int, var value: String, isGiven: Boolean)
{
  def x = _x
  def y = _y

  def updateValue(v: String): Unit = if(!isGiven) value = v
}