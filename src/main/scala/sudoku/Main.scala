package sudoku

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.text.Font

object Main extends JFXApp
{
  val w = 800
  val h = 800

  val grid = Array.tabulate(9)(i => Array.tabulate(9)(j => new Cell(i, j, i + j + "", true)))

  // DEBUG
  grid.foreach(a => a.foreach(c => println(c.value)))


  stage = new JFXApp.PrimaryStage
  {
    title = "Sudoku"
    scene = new Scene(w, h) {
      val canvas = new Canvas(w, h)
      val gc = canvas.getGraphicsContext2D
      content = canvas

      grid.foreach(arr => arr.foreach({ cell =>
        gc.font_=(new Font("Times Roman", 32))
        gc.fillText(cell.value.toString, cell.x * w / 9 + w / 36, cell.y * h / 9 + h / 16)
      }))

    }
  }
}


