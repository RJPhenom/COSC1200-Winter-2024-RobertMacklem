//****************************************************************
//Name:     Accounts.java  
//Auth:     Robert Macklem
//Date:     March 16 2024
//Desc:     File that handles multiple bank account types using
//          inheritance and polymorphism.
//****************************************************************

//Main class with main method
public class Accounts {
    //Main method
    public static void main(String args[]) {
        //Instantiate our accounts
        BaseAccount accBase = new BaseAccount(10000);
        ChequingAccount accChq = new ChequingAccount(10001);
        SavingsAccount accSvn = new SavingsAccount(10002, 0.02f, 5.0f);

        //Add some cash to each
        Transact(accBase, true, 256f);
        Transact(accChq, true, 512f);
        Transact(accSvn, true, 1024f); //--> this will immediately test interest rate

        //Make some basic withdrawals
        Transact(accBase, false, 56f);
        Transact(accChq, false, 12f);
        Transact(accSvn, false, 24f); //--> this will immediately test withdrawl fees

        //Test chequing overdraft
        Transact(accChq, false, 512f); //--> this will test overdraft protection, result should be balance = 0.0f

        //Refill some cash into our chequing
        Transact(accChq, true, 512f);

        //Test our intrabank functions
        Transact(accChq, accSvn, false, 1024); //--> Should test chequing overdraft + savings interest
        Transact(accBase, accSvn, true, 512); //--> Should test savings fee as the target
    }

    //Basic transaction, depositing or withdrawing cash
    private static void Transact(BaseAccount account, Boolean depositing, float funds) {
        //Perform transaction
        if (depositing) {
            account.AddFunds(funds);
        }

        else {
            account.WithdrawFunds(funds);
        }

        //Print out details
        account.DisplayDetails(true);
    }

    //Intrabank transaction, depositing or withdrawing fund between accounts
    private static void Transact(BaseAccount account, BaseAccount targetAccount, Boolean depositing, float funds) {
        //Perform transaction
        if (depositing) {
            targetAccount.WithdrawFunds(funds);
            account.AddFunds(funds);
        }

        else {
            account.WithdrawFunds(funds);
            targetAccount.AddFunds(funds);
        }

        account.DisplayDetails(true);
    }
}

//Base account class for all actual accounts to inherit from
class BaseAccount {
    //Properties
    float balance;
    int accountID;

    BaseAccount(int newID) {
        this.accountID = newID;
        this.balance = 0f;
    }

    //Getters
    public float GetBalance() {
        return this.balance;
    }

    public int GetAccountID() {
        return this.accountID;
    }

    //Shared Methods
    public void AddFunds(float funds) {
        this.balance += funds;
    }

    public void WithdrawFunds(float funds) {
        this.balance -= funds;
    }

    public void DisplayDetails(Boolean includeBalance) {
        System.out.println("Account ID: " + Integer.toString(this.accountID));
        if (includeBalance) {
            System.out.println("Balance: " + Float.toString(this.balance));
        }
    }
}

class ChequingAccount extends BaseAccount {
    //Properties

    ChequingAccount(int newID) {
        super(newID);
        this.balance = 0f;
    }

    @Override
    public void WithdrawFunds(float funds) {
        this.balance -= (this.balance - funds < 0f) ? funds + (this.balance - funds) : funds;
    }
}

class SavingsAccount extends BaseAccount {
    //Properties
    public float ir;
    public float fee;

    SavingsAccount(int newID, float interestRate, float withdrawalFee) {
        super(newID);
        this.ir = interestRate;
        this.fee = withdrawalFee;
        this.balance = 0f;
    }

    @Override
    public void AddFunds(float funds) {
        this.balance += funds + funds * this.ir;
    }

    @Override
    public void WithdrawFunds(float funds) {
        this.balance -= funds + this.fee;
    }
}