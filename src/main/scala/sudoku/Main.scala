package sudoku

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.text.Font

object Main extends JFXApp {
  val w = 800
  val h = 800
  val wSpacing = w / 9
  val hSpacing = h / 9
  val wOffset = w / 24
  val hOffset = h / 15

  // DEBUG
  //val grid = Array.tabulate(9)(i => Array.tabulate(9)(j => new Cell(i, j, i + j + "", false)))
  //grid.foreach(a => a.foreach(c => println(c.value)))

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

  def drawBoard(grid: Array[Array[Cell]], gc: GraphicsContext): Unit =
  {
    grid.foreach(arr => arr.foreach({ cell =>
      if (!cell.isGiven) gc.fill = Color.Blue
      else gc.fill = Color.Black
      gc.fillText(cell.value, cell.x * wSpacing + wOffset, cell.y * hSpacing + hOffset)
    }))
  }

  def cellClicked(x: Double, y: Double, grid: Array[Array[Cell]], gc: GraphicsContext): Unit =
  {
    for(arr <- grid)
    {
      for(cell <- arr)
      {
        if(x < cell.x * wSpacing + wSpacing && x > cell.x * wSpacing && y < cell.y * hSpacing + hSpacing && y > cell.y * hSpacing && !cell.isGiven)
        {
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
            contentText = "Choose your guess."
            buttonTypes = Seq(
              ButtonTypeOne, ButtonTypeTwo, ButtonTypeThree, ButtonTypeFour, ButtonTypeFive, ButtonTypeSix, ButtonTypeSeven, ButtonTypeEight, ButtonTypeNine, ButtonType.Cancel)
          }

          val result = alert.showAndWait()

          result match {
            case Some(button) =>
            {
              if (button != ButtonType.Cancel && button.text != cell.value)
              {
                gc.clearRect(cell.x * wSpacing + wSpacing / 9, cell.y * hSpacing + hSpacing / 9, wSpacing - wSpacing / 8, hSpacing - hSpacing / 8)
                cell.updateValue(button.text)
                drawBoard(grid, gc)
              }
            }
            case _ => println("User chose CANCEL or closed the dialog")
          }
        }
      }
    }
  }



  stage = new JFXApp.PrimaryStage
  {
    title = "Sudoku"
    scene = new Scene(w, h) {
      val canvas = new Canvas(w, h)
      val gc = canvas.getGraphicsContext2D
      content = canvas

      gc.lineWidth_=(3)

      // Outside boundaries
      gc.strokeLine(0,0,w,0)
      gc.strokeLine(0,0,0,h)
      gc.strokeLine(w,0,w,h)
      gc.strokeLine(0,h,w,h)

      // Box lines
      gc.strokeLine(w / 3,0,w / 3,h)
      gc.strokeLine(w / 3 * 2, 0, w / 3 * 2,h)
      gc.strokeLine(0, h / 3, w, h / 3)
      gc.strokeLine(0, h / 3 * 2, w, h / 3 * 2)

      // Row and column lines
      gc.lineWidth_=(1)
      gc.strokeLine(w / 9, 0, w / 9, h)
      gc.strokeLine(w / 9 * 2, 0, w / 9 * 2, h)
      gc.strokeLine(w / 9 * 4, 0, w / 9 * 4, h)
      gc.strokeLine(w / 9 * 5, 0, w / 9 * 5, h)
      gc.strokeLine(w / 9 * 7, 0, w / 9 * 7, h)
      gc.strokeLine(w / 9 * 8, 0, w / 9 * 8, h)
      gc.strokeLine(0, h / 9, w, h / 9)
      gc.strokeLine(0, h / 9 * 2, w, h / 9 * 2)
      gc.strokeLine(0, h / 9 * 4, w, h / 9 * 4)
      gc.strokeLine(0, h / 9 * 5, w, h / 9 * 5)
      gc.strokeLine(0, h / 9 * 7, w, h / 9 * 7)
      gc.strokeLine(0, h / 9 * 8, w, h / 9 * 8)

      val grid = readBoard(1)

      gc.font_=(new Font("Times Roman", 32))
      drawBoard(grid, gc)

      onMousePressed = (me: MouseEvent) =>
      {
        cellClicked(me.x, me.y, grid, gc)
      }

    }
  }
}


