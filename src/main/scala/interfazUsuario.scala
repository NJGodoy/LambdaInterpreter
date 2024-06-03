import interprete.*
import lector.*
import reductor.*

import scala.io.StdIn.readLine

def ejecutar(): Unit = {
  println("Iniciando interfaz del interprete Lambda")
  var continuar = true
  var modo = "call-by-name"
  while (continuar) {
    println()
    val input = readLine(">").toLowerCase()

    input match {
      case "exit" =>
        continuar = false
      case "set call-by-name" =>
        modo = "call-by-name"
        println("Modo de reducción cambiado a call-by-name.")
      case "set call-by-value" =>
        modo = "call-by-value"
        println("Modo de reducción cambiado a call-by-value.")
      case "set free-variables" =>
        modo = "free-variables"
        println("Modo cambiado a cálculo de variables libres.")
      case expresion if modo == "free-variables" =>
        val tokens = leerExpresion(expresion)
        val expr = interpretarExpresion(tokens)
        val FV = variablesLibres(expr)
        println(s"Variables libres: $FV")
      case expresion =>
        val tokens = leerExpresion(expresion)
        val expr = interpretarExpresion(tokens)
        graficarArbol(expr)
        val resultado = modo match {
          case "call-by-name" => reducirCallByName(expr)
          case "call-by-value" => reducirCallByValue(expr)
        }
        print(s"Reducido por $modo: ")
        imprimirExpresion(resultado)
    }
  }
}