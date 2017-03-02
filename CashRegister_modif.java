public class CashRegister{

	public void recordPurchase(double amount){
		purchase = purchase + amount;
	}

	public void receivePaiement(int dollars, int quarters, int dimes, int nickels, int pennies){
		payment = dollars + quarters*QUARTER_VALUE + dimes*DIME_VALUE + nickels*NICKEL_VALUE + pennies*PENNY_VALUE;
	}
}

public class ChangeGiver extends CashRegister{

	public double giveChange(){
	
		double change = payment - purchase;
		purchase = 0;
		payment = 0;
		return change;
	}
}