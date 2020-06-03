package gui.com;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class TextLimit extends PlainDocument {
	
	   private int limit;
	   private int maxLimit;
	   
	   public TextLimit(int limit) {
	      super();
	      this.limit = limit;
	   }
	   
	   public TextLimit(int minLimit, int maxLimit) {
		      super();
		      this.maxLimit = maxLimit;
		   }
	   
	   public TextLimit(int limit, boolean upper) {
	      super();
	      this.limit = limit;
	   }
	   
	   public TextLimit(boolean numeric) {
		   super();
	   }
	   
	   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		   
		  if(limit != 0) {
		      if (str == null) {
		    	  return;
		      }
		      
		      if ((getLength() + str.length()) <= limit) {
		         super.insertString(offset, str.replaceAll("[^0-9.]", ""), attr);
		      }
		  }
		  
		  if(maxLimit != 0) {
		      if (str == null) {
		    	  return;
		      }
		      
		      if ((getLength() + str.length()) <= maxLimit) {
		         super.insertString(offset, str.replaceAll("", ""), attr);
		      }
		  }
	      
	   }
	   
}
