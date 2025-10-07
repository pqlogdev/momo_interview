## Project Structure

```
src/
  ├── Main.java
  ├── core/
  │    ├── Account.java
  │    ├── Bill.java
  │    ├── Payment.java
  │    ├── BillService.java
  │    ├── PaymentService.java
  ├── commands/
  │    ├── Command.java
  │    ├── CommandFactory.java
  │    ├── CashInCommand.java
  │    ├── PayCommand.java
  │    ├── ListBillCommand.java
  │    ├── DueDateCommand.java
  │    ├── ScheduleCommand.java
  │    ├── ListPaymentCommand.java
  │    ├── SearchBillByProviderCommand.java
  ├── repository/
  │    ├── BillRepository.java
  │    ├── PaymentRepository.java
  ├── utils/
  │    ├── DateUtils.java
  │    ├── ConsoleUtils.java
  └── tests/
       ├── AccountTest.java
       ├── BillServiceTest.java
       └── PaymentServiceTest.java
```

## How to Run

```bash
# Run in interactive mode
java -cp bin Main

# Run with a specific command
java -cp bin Main COMMAND [ARGS]
```

## Available Commands

- `CASH_IN <amount>` - Add funds to your account
- `LIST_BILL` - List all bills
- `PAY <bill_id> [<bill_id> ...]` - Pay one or multiple bills
- `DUE_DATE` - View bills by due date
- `SCHEDULE <bill_id> <date>` - Schedule a payment for a future date
- `LIST_PAYMENT` - View payment history
- `SEARCH_BILL_BY_PROVIDER <name>` - Search bills by provider name
- `EXIT` - Exit the program

## Examples

```
$ java -cp bin Main CASH_IN 1000000
Your available balance: 1000000

$ java -cp bin Main LIST_BILL
Bill No. Type Amount Due Date State PROVIDER
1. ELECTRIC 200000 25/10/2020 NOT_PAID EVN HCMC
2. WATER 175000 30/10/2020 NOT_PAID SAVACO HCMC
3. INTERNET 800000 30/11/2020 NOT_PAID VNPT

$ java -cp bin Main PAY 1
Payment has been completed for Bill with id 1.
Your current balance is: 800000
```

## How to Run Tests

```bash
# Compile tests with JUnit 5
javac -cp "lib/junit-platform-console-standalone-1.9.2.jar:bin" -d bin src/tests/*.java

# Run tests
java -cp "lib/junit-platform-console-standalone-1.9.2.jar:bin" org.junit.platform.console.ConsoleLauncher --scan-classpath
```