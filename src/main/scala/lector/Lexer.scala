package lector
import modelo.Token
def leerExpresion(lambda: String): List[Token | Char] = {
  _tokenizar(lambda.toList)
}

def _tokenizar(tokens: List[Char]): List[Token | Char] = {
  tokens match {
    case Nil => List()
    case x::xs =>
      val token: Token | Char = x match {
        case 'λ' => Token.LAMBDA
        case ' ' => Token.SPACE
        case '.' => Token.DOT
        case '(' => Token.LPAR
        case ')' => Token.RPAR
        case char if char.isLetter => char
        case char => throw new IllegalArgumentException(s"Argumento invalido: $char")
      }
      token::_tokenizar(xs)
  }
}