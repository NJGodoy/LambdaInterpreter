package interprete

import scala.collection.mutable.Stack
import modelo.*
def interpretarExpresion(ecuacion: List[Token | Char]): Expresion = {
  val (res,_) = _interpretarExpresionRec(ecuacion)
  res
}
def _interpretarExpresionRec(tokens: List[Token | Char]): (Expresion, List[Token | Char]) = {
  tokens match {
    case Nil => throw new IllegalArgumentException("Token Invalido")
    case Token.LPAR :: xs =>
      val (e1, siguiente) = _interpretarExpresionRec(xs)
      val (e2, resto) = _interpretarExpresionRec(siguiente)
      (Aplicacion(e1, e2), resto.tail)
    case Token.LAMBDA :: (param : Char) :: Token.DOT :: xs =>
      val (cuerpo, resto) = _interpretarExpresionRec(xs)
      (Abstraccion(param, cuerpo), resto)
    case Token.SPACE :: xs =>
      _interpretarExpresionRec(xs)
    case (variable : Char) :: resto =>
      (Variable(variable), resto)
    case _ => throw new IllegalArgumentException("Argumento Invalido.")
  }
}