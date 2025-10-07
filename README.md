# Bill Payment Service

A command-line application for managing bills and payments.

## Project Structure

```
src/
  ├── Main.java
  ├── core/
  │    ├── Account.java
  │    ├── Bill.java
  │    ├── Payment.java
  │    ├── BillService.java
  │    ├── BillState.java
  │    ├── BillType.java
  │    ├── PaymentService.java
  │    ├── PaymentState.java
  ├── commands/
  │    ├── Command.java
  │    ├── CommandFactory.java
  │    ├── BillCommand.java      # Unified bill command
  │    ├── PaymentCommand.java   # Unified payment command
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

## How to Compile

### Using Provided Scripts

#### On Unix/Linux/macOS:
```bash
# Make the script executable (if needed)
chmod +x compile.sh

# Compile the project
./compile.sh
```

#### On Windows:
```bash
# Using Command Prompt
compile.bat

# Using Git Bash or similar
./compile.sh
```

### Manual Compilation

```bash
# Create bin directory if it doesn't exist
mkdir -p bin

# Compile main source files
javac -d bin src/Main.java src/core/*.java src/commands/*.java src/repository/*.java src/utils/*.java

# Compile test files with JUnit
javac -cp "bin;lib/junit-platform-console-standalone-1.9.2.jar" -d bin -sourcepath src src/tests/*.java
```

> Note: On Unix/Linux/macOS systems, use colon (:) instead of semicolon (;) in the classpath:
> ```bash
> javac -cp "bin:lib/junit-platform-console-standalone-1.9.2.jar" -d bin -sourcepath src src/tests/*.java
> ```

## How to Run

```bash
# Run in interactive mode
java -cp bin Main

# Run with a specific command
java -cp bin Main COMMAND [ARGS]
```

## Available Commands

### New Unified Command Structure

#### Bill Commands
- `BILL LIST` - List all bills
- `BILL CREATE <type> <amount> <dueDate> <provider>` - Create a new bill
- `BILL UPDATE <id> <type> <amount> <dueDate> <provider>` - Update an existing bill
- `BILL DELETE <id>` - Delete a bill
- `BILL SEARCH_BY_PROVIDER <name>` - Search bills by provider name
- `BILL DUE_DATE` - View bills by due date

#### Payment Commands
- `PAYMENT PAY <bill_id> [<bill_id> ...]` - Pay one or multiple bills
- `PAYMENT CASH_IN <amount>` - Add funds to your account
- `PAYMENT LIST` - View payment history
- `PAYMENT SCHEDULE <bill_id> <date>` - Schedule a payment for a future date

## Examples

```
$ java -cp bin Main PAYMENT CASH_IN 1000000
Your available balance: 1000000

$ java -cp bin Main BILL LIST
Bill No. Type Amount Due Date State PROVIDER
1. ELECTRIC 200000 25/10/2020 NOT_PAID EVN HCMC
2. WATER 175000 30/10/2020 NOT_PAID SAWACO
3. INTERNET 800000 30/11/2020 NOT_PAID VNPT

$ java -cp bin Main PAYMENT PAY 1
Payment has been completed for Bill with id 1.
Your current balance is: 800000
```

### Using Legacy Command Structure

```
$ java -cp bin Main CASH_IN 1000000
Your available balance: 1000000

$ java -cp bin Main LIST_BILL
Bill No. Type Amount Due Date State PROVIDER
1. ELECTRIC 200000 25/10/2020 NOT_PAID EVN HCMC
2. WATER 175000 30/10/2020 NOT_PAID SAWACO
3. INTERNET 800000 30/11/2020 NOT_PAID VNPT

$ java -cp bin Main PAY 1
Payment has been completed for Bill with id 1.
Your current balance is: 800000
```

## How to Run Tests

```bash
# Using the provided scripts
./compile.sh
./runTests.sh

# Or manually
javac -cp "lib/junit-platform-console-standalone-1.9.2.jar:bin" -d bin src/tests/*.java
java -cp "lib/junit-platform-console-standalone-1.9.2.jar:bin" org.junit.platform.console.ConsoleLauncher --scan-classpath
```