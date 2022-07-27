package Model

import java.math.BigDecimal
import java.time.LocalDate

data class Customer(
  val name: String,
  val gender: Gender,
  val dob: LocalDate,
  var balance: BigDecimal,
  val account: String,
  val address: Address
)
