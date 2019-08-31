package com.nongped.rtc

import org.scalatest.{ Matchers, WordSpec }

class ReturnsTest extends WordSpec with Matchers {
  "keep only a window of the returns" in {
    val variableReturns = VariableReturns(Vector.tabulate(12) { i =>
      val d = (i + 1).toDouble
      VariableReturn(f"2017.$d%02.0f", d)
    })

    variableReturns.fromUntil(monthIdFrom = "2017.07", monthIdUntil = "2017.09").returns should ===(
      Vector(VariableReturn("2017.07", 7.0), VariableReturn("2017.08", 8.0)))

    variableReturns.fromUntil("2017.10", "2018.01").returns should ===(
      Vector(VariableReturn("2017.10", 10.0), VariableReturn("2017.11", 11.0), VariableReturn("2017.12", 12.0)))
  }
}
