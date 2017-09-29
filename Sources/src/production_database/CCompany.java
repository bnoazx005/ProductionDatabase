/**
 * CCompany.java
 * 
 * @author Kasimov ildar
 */
package production_database;

import production_database.interfaces.*;


/**
 * class CCompany
 * 
 * The class implements ICompany interface and describes a company's (manufacturer) functionality
 * 
 * @author Kasimov Ildar
 *
 */
public final class CCompany implements ICompany {
	private String mName;
	
	private String mCode;
	
	private String mPhone;
	
	private String mAddress;
	
	/**
	 * The main constructor
	 * 
	 * @param name A company's name
	 * @param code A company's code
	 * @param address A company's address
	 * @param phone A company's phone
	 */
	
	public CCompany(String name, String code, String address, String phone) {
		mName = name;
		mCode = code;
		
		mPhone   = phone;
		mAddress = address;
	}
	
	/**
	 * Constructor without phone parameter
	 * 
	 * @param name A company's name
	 * @param code A company's code
	 * @param address A company's address
	 */
	
	public CCompany(String name, String code, String address) {
		mName = name;
		mCode = code;
		
		mPhone   = "";
		mAddress = address;
	}
	
	@SuppressWarnings("unused")
	private CCompany() {		
	}
	
	/**
	 * The method sets up a name of a company
	 * @param name A name of a company
	 */
	@Override
	public void SetName(String name) {
		mName = name;
	}
	
	/**
	 * The method sets up a code of a company
	 * @param code A string that contains code's value
	 */
	
	@Override
	public void SetCode(String code) {
		mCode = code;
	}
	
	/**
	 * The method sets up a string, which contains a phone of a company
	 * @param phone A string with a company's phone
	 */
	
	@Override
	public void SetPhone(String phone) {
		mPhone = phone;
	}
	
	/**
	 * The method sets up an address of a company
	 * @param address A string with a company's address
	 */
	
	@Override
	public void SetAddress(String address) {
		mAddress = address;
	}
	
	/**
	 * The method returns a name of a company
	 * @return A string with a name of a company
	 */
	
	@Override
	public String GetName() {
		return mName;
	}
	
	/**
	 * The method returns a code of a company
	 * @return A string with a company's code
	 */
	
	@Override
	public String GetCode() {
		return mCode;
	}
	
	/**
	 * The method returns a string with a company's phone
	 * @return A string, which contains a company's phone
	 */
	
	@Override
	public String GetPhone() {
		return mPhone;
	}
	
	/**
	 * The method returns a string with a company's address
	 * @return A string, which contains a company's address
	 */
	
	@Override
	public String GetAddress() {
		return mAddress;
	}
	
	@Override
	public String toString() {
		String resultString = "[" + mName;
		
		if (mCode != null) {
			resultString += ", Code: (" + mCode + ")";
		}
		
		if (mPhone != null && mPhone != "") {
			resultString += ", Phone: " + mPhone;
		}
		
		return resultString + "]";
	}
}
