package Model

import java.math.BigDecimal
import java.time.LocalDate

data class Transfer(
  val fromAccount: String?,
  val toAccount: String?,
  val balance: BigDecimal,
  val date: LocalDate
)
