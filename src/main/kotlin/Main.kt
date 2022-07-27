import Model.Address
import Model.Customer
import Model.Gender
import Model.Transfer
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val customerList = mutableListOf<Customer>()
private val transferList = mutableListOf<Transfer>()

fun main() {
  var option : String? = ""
  println("-------------------------------")
  println("|   Welcome To Monster Bank   |")
  println("-------------------------------")
  println("| 1. Customer Account         |")
  println("| 2. Transfer Fund            |")
  println("| 3. Transfer History         |")
  println("| 4. Top Up                   |")
  println("-------------------------------")

  print("=> Input Menu Number : ")
  when (readln()?.toInt()) {
    1 -> customerAccountMenu()
    2 -> {
          do {
            transferFund()
            println("-------------------------------")
            print("Do you want transfer fund again (y or n): ")
            option = readln()
          } while (option == "y" || option == "Y")

          if (option.toString() == "n" || option == "N") {
            main()
          }
        }
    3 -> {
          do {
            transferHistory()
            println("-------------------------------")
            print("Do you want to search transfer history again (y or n): ")
            option = readln()
          } while (option == "y" || option == "Y")

          if (option.toString() == "n" || option == "N") {
            main()
          }
        }
    4 -> {
            do {
              topUp()
              println("-------------------------------")
              print("Do you want to top up again (y or n): ")
              option = readLine()
            } while (option == "y" || option == "Y")

            if (option.toString() == "n" || option == "N") {
              main()
            }
          }
  }
}

fun customerAccountMenu () {
  var option : String? = ""

  println("-------------------------------")
  println("|       Customer Account      |")
  println("-------------------------------")
  println("| 1. Register Account         |")
  println("| 2. List Account             |")
  println("| 3. Back                     |")
  println("-------------------------------")
  print("=> Input Menu Number: ")
  when (readLine()?.toInt()) {
    1 -> {
        do {
          registerAccount()
          println("-------------------------------")
          print("Do you want create account again (y or n): ")
          option = readLine()
        } while (option == "y" || option == "Y")

        if (option.toString() == "n" || option == "N") {
          main()
        }
      }
    2 -> {
      do {
        listAccount()
        println("-------------------------------")
        print("Do you want to search account again (y or n): ")
        option = readLine()
      } while (option == "y" || option == "Y")

      if (option.toString() == "n" || option == "N") {
        main()
      }
    }
    3 -> main()
  }
}

fun registerAccount () {
  println("-------------------------------")
  println("|       Register Account      |")
  println("-------------------------------")
  print("Input Name: ")
  val name: String = readln()
  print("Input Gender ( Male or Female ): ")
  val gender: String = readln()
  print("Input Date Of Birth: ")
  val dob: String = readln()
  print("Input Balance: ")
  val balance = readln().toBigDecimal()
  print("Input Account Number: ")
  val account: String = readln()
  print("Input Street Number: ")
  val street: String = readln()
  print("Input Building Number: ")
  val buildingNumber: String = readln()
  print("Input Commune: ")
  val commune: String = readln()
  print("Input District: ")
  val district: String = readln()
  print("Input City/Province: ")
  val province: String = readln()
  val date = LocalDate.parse(dob, DateTimeFormatter.ISO_DATE)
  val address = Address(street, buildingNumber, district, commune, province)
  val customer = Customer(name, Gender.valueOf(gender), date, balance, account, address)
  customerList.add(customer)

  if (customerList.add(customer)) {
    println("-------------------------------")
    println("Create customer successfully")
  } else {
    println("-------------------------------")
    println("Create customer not successfully")
  }
}

fun listAccount () {
  println("-------------------------------")
  println("|         List Account        |")
  println("-------------------------------")
  print("Input account number: ")
  val accountNumber: String? = readLine()

  if (accountNumber == "") {
    println("-------------------------------")
    println("Account number can not empty...")
  } else {
    val customerData = customerList.find { it.account == accountNumber }

    if (customerData != null) {
      println("-------------------------------")
      println("Name: ${customerData.name}")
      println("Gender: ${customerData.gender}")
      println("Date Of Birth: ${customerData.dob}")
      println("Balance: ${customerData.balance}")
      println("Account Number: ${customerData.account}")
      println("===============Address=================")
      println("Street Number: ${customerData.address.streetNumber}")
      println("Building Number: ${customerData.address.buildingNumber}")
      println("Commune: ${customerData.address.commune}")
      println("District: ${customerData.address.district}")
      println("Province/City: ${customerData.address.province}")
    } else {
      println("-------------------------------")
      println("Account number [$accountNumber] not found...")
    }
  }
}

fun transferFund () {
  println("-------------------------------")
  println("|       Transfer Fund         |")
  println("-------------------------------")
  print("From account number: ")
  val fromAccount: String = readln()
  print("To account number: ")
  val toAccount: String = readln()
  print("Balance: ")
  val balance: String = readln()

  if (fromAccount == "" || toAccount == "" || balance == "") {
    println("-------------------------------")
    println("AccountNumber or Balance can not empty...")
    println("Error can not transfer fund")
  } else {
    if (balance < 0.toString()){
      println("-------------------------------")
      println("Balance can not under 0...")
      println("Error can not transfer fund")
    }else {
      val fromAccountNum = customerList.firstOrNull { it.account == fromAccount }
      val toAccountNum = customerList.firstOrNull { it.account == toAccount }
      if (fromAccountNum != null && toAccountNum != null){
        if (fromAccountNum.balance > balance.toBigDecimal()){
          fromAccountNum.balance -= balance.toBigDecimal()
          toAccountNum.balance += balance.toBigDecimal()
          val date = LocalDate.now()
          val transfer = Transfer(fromAccountNum.account, toAccountNum.account, balance.toBigDecimal(), date)
          transferList.add(transfer)
          println("-------------------------------")
          println("Transfer successfully")
        } else {
          println("-------------------------------")
          println("Money not enough to transfer")
        }
      } else if (fromAccountNum == null && toAccountNum == null) {
        println("-------------------------------")
        println("AccountNumber [$fromAccount & $toAccount] is not found in the database ")
        println("Error can not transfer fund")
      } else if (toAccountNum == null) {
        println("-------------------------------")
        println("AccountNumber [$toAccount] is not found in the database ")
        println("Error can not transfer fund")
      } else{
        println("-------------------------------")
        println("AccountNumber [$fromAccount] is not found in the database ")
        println("Error can not transfer fund")
      }
    }
  }
}

fun transferHistory () {
  println("-------------------------------")
  println("|       Transfer History      |")
  println("-------------------------------")
  print("Input account number: ")
  val accountNumber: String? = readLine()

  if (accountNumber == "") {
    println("-------------------------------")
    println("Account number can not empty...")
  } else {
    val transferFromData = transferList.find{ it.fromAccount == accountNumber }
    val transferToData = transferList.find{ it.fromAccount == accountNumber }
    if (transferFromData != null || transferToData != null) {
      transferList.filter { it.fromAccount == accountNumber || it.toAccount == accountNumber }.forEach {
        if (it.fromAccount == accountNumber) {
          println("-----------Transfer Out--------------")
        } else {
          println("-----------Transfer In--------------")
        }
        println("Date: ${it.date}")
        println("From Account: ${it.fromAccount}")
        println("To Account: ${it.toAccount}")
        println("Balance: ${it.balance}")
      }
    } else {
      println("-------------------------------")
      println("Account number [$accountNumber] not found...")
    }
  }
}

fun topUp () {
  println("-------------------------------")
  println("|           Top Up            |")
  println("-------------------------------")
  print("Input account number: ")
  val accountNumber: String? = readLine()

  if (accountNumber == "") {
    println("-------------------------------")
    println("Account number can not empty...")
    println("-------------------------------")
  } else {
    val accountNum = customerList.firstOrNull(){ it.account == accountNumber }
    if (accountNum != null) {
      print("Input balance: ")
      val balance: String = readln()
      if (balance == "") {
        println("-------------------------------")
        println("Balance can not empty...")
        println("Error can not top up")
      } else {
        if (balance < 0.toString()) {
          println("-------------------------------")
          println("Balance can not under 0...")
          println("Error can not top up")
        } else {
          accountNum.balance += balance.toBigDecimal()
          println("-------------------------------")
          println("Top up balance successfully")
        }
      }
    } else {
      println("-------------------------------")
      println("Account number [$accountNumber] not found...")
      println("Error can not top up")
    }
  }
}