package com.nongped.rtc

import org.scalactic.{Equality, TolerantNumerics, TypeCheckedTripleEquals}
import org.scalatest.{Matchers, WordSpec}

class ReturnsTest extends WordSpec with Matchers with TypeCheckedTripleEquals {

  implicit val doubleEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.0001)

  "keep only a window of the returns" in {
    val variableReturns = VariableReturns(Vector.tabulate(12) { i =>
      val d = (i + 1).toDouble
      VariableReturn(f"2017.$d%02.0f", d)
    })

    variableReturns.fromUntil(monthIdFrom = "2017.07", monthIdUntil = "2017.09").returns should ===(
      Vector(VariableReturn("2017.07", 7.0), VariableReturn("2017.08", 8.0))
    )

    variableReturns.fromUntil("2017.10", "2018.01").returns should ===(
      Vector(VariableReturn("2017.10", 10.0), VariableReturn("2017.11", 11.0), VariableReturn("2017.12", 12.0))
    )
  }
  "Returns.monthlyRate" should {

    val variableReturns = VariableReturns(Vector(VariableReturn("2000.01", 0.1), VariableReturn("2000.02", 0.2)))

    "return a fixed rate for a FixedReturn" in {
      Returns.monthlyRate(FixedReturns(0.04), 0) should ===(0.04 / 12)
      Returns.monthlyRate(FixedReturns(0.04), 10) should ===(0.04 / 12)
    }
  }

}
