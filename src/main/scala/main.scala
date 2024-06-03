@main
def main(): Unit = {
  val expr = "(λx.λy.y (λx.(x x) λx.(x x))) "
  val tokens = lector.leerExpresion(expr)
  val AST = interprete.interpretarExpresion(tokens)
  val ASTreducido = reductor.reducirCallByValue(AST)
  interprete.graficarArbol(ASTreducido)
}
