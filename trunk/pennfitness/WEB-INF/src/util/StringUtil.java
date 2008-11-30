package util;

public class StringUtil {
	/*
	 * If a given string is longer than maxLength, shrink it to maxLength with '...' at the end 
	 */
	public static String fitString(String str, int maxLength)
	{
		if( str.length() <= maxLength ) return str;
		else
		{
			return str.substring(0, maxLength-4) + "...";
		}
	}

}
