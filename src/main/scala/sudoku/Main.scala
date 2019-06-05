package sudoku

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.MouseEvent
import scalafx.scene.text.Font

object Main extends JFXApp {
  val w = 800
  val h = 800

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

      grid.foreach(arr => arr.foreach({ cell =>
        gc.font_=(new Font("Times Roman", 32))
        gc.fillText(cell.value, cell.x * w / 9 + w / 24, cell.y * h / 9 + h / 15)
      }))

      onMousePressed = (me: MouseEvent) =>
      {
        // TODO:
        // Handle mouse clicks and edit cells
      }

    }
  }
}


