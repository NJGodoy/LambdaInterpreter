@main
def main(): Unit = {
  val expr = "(λx.λx.(y x) z)"
  val tokens = lector.leerExpresion(expr)
  println(tokens)
  val AST = interprete.interpretarExpresion(tokens)
}
