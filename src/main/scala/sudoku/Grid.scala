package sudoku

import scalafx.application.JFXApp
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.paint.Color

class Grid(i: Int, stage: JFXApp.PrimaryStage, wSpacing: Int, hSpacing: Int, wOffset: Int, hOffset: Int)
{
  val grid: Array[Array[Cell]] = Grid.readBoard(i)

  def drawBoard(gc: GraphicsContext): Unit =
  {
    grid.foreach(arr => arr.foreach({ cell =>
      gc.clearRect(cell.x * wSpacing + wSpacing / 9, cell.y * hSpacing + hSpacing / 9, wSpacing - wSpacing / 8, hSpacing - hSpacing / 8)
      if (!cell.isGiven) gc.fill = Color.Blue
      else gc.fill = Color.Black
      gc.fillText(cell.value, cell.x * wSpacing + wOffset, cell.y * hSpacing + hOffset)
    }))
  }

  def cellClicked(x: Double, y: Double, gc: GraphicsContext): Unit =
  {
    for(arr <- grid)
    {
      for(cell <- arr)
      {
        if(x < cell.x * wSpacing + wSpacing && x > cell.x * wSpacing && y < cell.y * hSpacing + hSpacing && y > cell.y * hSpacing && !cell.isGiven)
        {
          val ButtonTypeBlank = new ButtonType("")
          val ButtonTypeOne = new ButtonType("1")
          val ButtonTypeTwo = new ButtonType("2")
          val ButtonTypeThree = new ButtonType("3")
          val ButtonTypeFour = new ButtonType("4")
          val ButtonTypeFive = new ButtonType("5")
          val ButtonTypeSix = new ButtonType("6")
          val ButtonTypeSeven = new ButtonType("7")
          val ButtonTypeEight = new ButtonType("8")
          val ButtonTypeNine = new ButtonType("9")

          val alert = new Alert(AlertType.Confirmation) {
            initOwner(stage)
            title = "Number Input"
            headerText = "Enter a number into this cell."
            contentText = "Choose your guess or erase a number."
            buttonTypes = Seq(
              ButtonTypeOne, ButtonTypeTwo, ButtonTypeThree, ButtonTypeFour, ButtonTypeFive, ButtonTypeSix, ButtonTypeSeven, ButtonTypeEight, ButtonTypeNine, ButtonTypeBlank,
              ButtonType.Cancel)
          }

          val result = alert.showAndWait()

          result match {
            case Some(button) =>
            {
              if (button != ButtonType.Cancel && button.text != cell.value)
              {
                cell.updateValue(button.text)
                drawBoard(gc)
              }
            }
            case _ => println("User chose CANCEL or closed the dialog")
          }
        }
      }
    }
  }

}

object Grid
{
  // Read sudoku board from txt file
  def readBoard(i: Int): Array[Array[Cell]] =
  {
    val source = scala.io.Source.fromFile("src/main/resources/boards/board" + i + ".txt")
    val lines = source.getLines().toArray.map(line => line.split(" ").map(_.toInt))
    source.close()
    (for(i <- lines.indices) yield
      {
        (for(j <- lines.indices) yield
          {
            if (lines(i)(j) == 0) new Cell(j, i, "", false)
            else new Cell(j, i, lines(i)(j).toString, true)
          }).toArray
      }).toArray
  }
}
