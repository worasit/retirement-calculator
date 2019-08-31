package com.nongped.rtc

import scala.annotation.tailrec

class RetCalc {}

object RetCalc {
  def nbOfMonthsSaving(
      interestRate: Double,
      nbOfMonthsInRetirement: Int,
      netIncome: Int,
      currentExpenses: Int,
      initialCapital: Int) = {
    @tailrec
    def loop(months: Int): Int = {
      val (_, capitalAfterDeath) = simulatePlan(
        interestRate = interestRate,
        nbOfMonthsSaving = months,
        nbOfMonthsInRetirement = nbOfMonthsInRetirement,
        netIncome = netIncome,
        currentExpenses = currentExpenses,
        initialCapital = initialCapital)

      if (capitalAfterDeath > 0.0)
        months
      else
        loop(months + 1)
    }
    if (netIncome > currentExpenses)
      loop(months = 0)
    else
      Int.MaxValue
  }

  def simulatePlan(
      interestRate: Double,
      nbOfMonthsSaving: Int,
      nbOfMonthsInRetirement: Int,
      netIncome: Int,
      currentExpenses: Int,
      initialCapital: Double): (Double, Double) = {
    val capitalAtRetirement =
      futureCapital(interestRate, nbOfMonthsSaving, netIncome, currentExpenses, initialCapital)
    val capitalAfterDeath = futureCapital(
      interestRate,
      nbOfMonthsInRetirement,
      netIncome = 0,
      currentExpenses,
      initialCapital = capitalAtRetirement)

    (capitalAtRetirement, capitalAfterDeath)
  }

  def futureCapital(
      interestRate: Double,
      nbOfMonths: Int,
      netIncome: Int,
      currentExpense: Int,
      initialCapital: Double): Double = {
    val monthlySavings = netIncome - currentExpense
    (0 until nbOfMonths).foldLeft(initialCapital)((accumulated, _) => accumulated * (1 + interestRate) + monthlySavings)
  }
}
