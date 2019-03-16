package project0.pojo;

public class Account {

	private int userId;
	private long accountNo;
	private String accountType;
	private double accountBalance;
	
	public Account(){
		super();
	}
	
	
	
	public Account(int userId, String accountType, double accountBalance) {
		super();
		this.userId = userId;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}

		
	public Account(int userId, long accountNo, String accountType) {
		super();
		this.userId = userId;
		this.accountType = accountType;
		this.accountNo = accountNo;
	}

	public Account(int userId, long accountNo, String accountType, double accountBalance) {
		super();
		this.userId = userId;
		this.accountNo = accountNo;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}



	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(accountBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (accountNo ^ (accountNo >>> 32));
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Double.doubleToLongBits(accountBalance) != Double.doubleToLongBits(other.accountBalance))
			return false;
		if (accountNo != other.accountNo)
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [userId=" + userId + ", accountNo=" + accountNo + ", accountType=" + accountType
				+ ", accountBalance=" + accountBalance + "]";
	}
	
	
	
}
