package za.ac.ukzn.dbinterface;

public class CartDetail
{
	private int quantity;
	private double price;
	private int code;
	
	public CartDetail(int quantity, double price, int code)
	{
		this.setQuantity(quantity);
		this.setPrice(price);
		this.setCode(code);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
