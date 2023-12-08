data class Account(val number: Int, var name: String, var balance: Double)

class Bank {
    private var accountCounter = 1
    private val accounts = mutableListOf<Account>()

    fun showMenu() {
        while (true) {
            println("Выберите действие:")
            println("1. Просмотр списка счетов")
            println("2. Открытие счета")
            println("3. Пополнение счета")
            println("4. Перевод денег между счетами")
            println("5. Выход")

            when (readLine()?.toIntOrNull()) {
                1 -> showAccounts()
                2 -> openAccount()
                3 -> deposit()
                4 -> transfer()
                5 -> {
                    println("Выход из программы.")
                    return
                }
                else -> println("Некорректный выбор. Попробуйте еще раз.")
            }
        }
    }

    private fun showAccounts() {
        if (accounts.isEmpty()) {
            println("На текущий момент нет открытых счетов.")
        } else {
            println("Список открытых счетов:")
            for (account in accounts) {
                println("${account.number}. ${account.name} - Баланс: ${account.balance}")
            }
        }
    }

    private fun openAccount() {
        print("Введите название счета: ")
        val accountName = readLine() ?: return
        val newAccount = Account(accountCounter++, accountName, 0.0)
        accounts.add(newAccount)
        println("Счет открыт успешно. Новый счет: ${newAccount.number}")
    }

    private fun deposit() {
        if (accounts.isEmpty()) {
            println("На текущий момент нет открытых счетов.")
            return
        }

        print("Введите номер счета для пополнения: ")
        val accountNumber = readLine()?.toIntOrNull() ?: return

        val account = accounts.find { it.number == accountNumber }
        if (account == null) {
            println("Счет с номером $accountNumber не найден.")
            return
        }

        print("Введите сумму пополнения: ")
        val depositAmount = readLine()?.toDoubleOrNull() ?: return

        if (depositAmount <= 0) {
            println("Некорректная сумма пополнения.")
            return
        }

        account.balance += depositAmount
        println("Счет ${account.number} пополнен на сумму $depositAmount. Новый баланс: ${account.balance}")
    }

    private fun transfer() {
        if (accounts.size < 2) {
            println("Для перевода денег необходимо иметь хотя бы два открытых счета.")
            return
        }

        print("Введите номер счета, с которого будет осуществлен перевод: ")
        val fromAccountNumber = readLine()?.toIntOrNull() ?: return

        val fromAccount = accounts.find { it.number == fromAccountNumber }
        if (fromAccount == null) {
            println("Счет с номером $fromAccountNumber не найден.")
            return
        }

        print("Введите номер счета, на который будет осуществлен перевод: ")
        val toAccountNumber = readLine()?.toIntOrNull() ?: return

        val toAccount = accounts.find { it.number == toAccountNumber }
        if (toAccount == null) {
            println("Счет с номером $toAccountNumber не найден.")
            return
        }

        print("Введите сумму перевода: ")
        val transferAmount = readLine()?.toDoubleOrNull() ?: return

        if (transferAmount <= 0 || transferAmount > fromAccount.balance) {
            println("Некорректная сумма перевода.")
            return
        }

        fromAccount.balance -= transferAmount
        toAccount.balance += transferAmount
        println("Перевод успешно выполнен. Новый баланс счета ${fromAccount.number}: ${fromAccount.balance}")
        println("Новый баланс счета ${toAccount.number}: ${toAccount.balance}")
    }
}

fun main() {
    val bank = Bank()
    bank.showMenu()
}
