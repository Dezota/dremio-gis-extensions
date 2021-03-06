package com.dezota.gis;

import java.util.Arrays;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * This program is a very simple implementation to emulate xpath APIs
 * for JSON Data in Java.
 * 
 * @author satyajitpaul
 * @version 1.2
 * @since   2017-03-29
 * @modified 2017-03-29
 * 
 * v1.2: In this release all static method are changed to non-static. This has
 * primarily been done to accommodate the new variable SUPPRESS_JSON_EXCEPTION.
 * This variable must be an instance level variable and cannot be made static 
 * as it will influence all the runtime instances. 
 */

/**
 * 
 *  MIT license
 *  ==========================================================================
 *  Copyright 2017 Satyajit Paul
 *  Permission is hereby granted, free of charge, to any person obtaining a 
 *  copy of this software and associated documentation files (the "Software"), 
 *  to deal in the Software without restriction, including without limitation 
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 *  and/or sell copies of the Software, and to permit persons to whom the 
 *  Software is furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in 
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 *  IN THE SOFTWARE.
 *	==========================================================================
 */

/* Based on from https://github.com/satyapaul/jpath with extraneous functions for
 * reading JSON from a URL.  This also eliminated the need for the jersey libraries */

public class JSONDataReader {

	/*
	 * The variable is introduced to deal with two different 
	 * situations - 
	 * 
	 * design time : when a user is exploring the jpath
	 * for a particular JSON Content. During that time the 
	 * jpath may be wrong and may throw legitimate Exception
	 * when data is missing due to wrong path.
	 * 
	 * run time: Usually a user will use a jpath that has been 
	 * validated. In that case, an exception may be thrown as 
	 * data may be missing even when path is correct. In such 
	 * situations, one may like to supress the exceptions. You
	 * may set the value of SUPPRESS_JSON_EXCEPTION to true to 
	 * achieve this.
	 *      
	 */
	public boolean SUPPRESS_JSON_EXCEPTION = false;
	
	private static final String LONG_TYPE = "0";
	private static final String INTEGER_TYPE = "1";
	private static final String STRING_TYPE = "2";
	private static final String BOOLEAN_TYPE = "3";
	private static final String JSON_ARRAY_TYPE = "4";
	private static final String JSON_OBJECT_TYPE = "6";
	private static final String DOUBLE_TYPE = "9";
	
	/**
	 * NOT a singleton class, this is just a helper method.
	 * Users are free to instantiate the class directly and
	 * use it. 
	 * @return
	 */
	public static JSONDataReader getInstance() {
		return new JSONDataReader();
	}

	/**
	 * This method is for reading the leaf level value of DataType 'String'
	 * @param path The parameter provides the input 'jpath'
	 * @param jsonData Actual JSONObject. The jpath above will traverse this data to get the requested data
	 * @return
	 * @throws JSONException
	 */
	public String getStringValue(String path, JSONObject jsonData) throws JSONException {
		path = path.startsWith("/") ? path.substring(1, path.length()) : path;
		String parts[] = path.split("/");
		Object value = getJSONValue( parts, jsonData);
		return (String)value;
	}
	
	/**
	 * This method is for reading the leaf level value of DataType 'Double'
	 * @param path The parameter provides the input 'jpath'
	 * @param jsonData Actual JSONObject. The jpath above will traverse this data to get the requested data
	 * @return
	 * @throws JSONException
	 */
	public Double getDoubleValue(String path, JSONObject jsonData) throws JSONException {
		path = path.startsWith("/") ? path.substring(1, path.length()) : path;
		String parts[] = path.split("/");
		Object value = getJSONValue( parts, jsonData);
		return (Double)value;
	}
	
	/**
	 * This method is for reading the leaf level value of DataType 'Integer'
	 * @param path The parameter provides the input 'jpath'
	 * @param jsonData Actual JSONObject. The jpath above will traverse this data to get the requested data
	 * @return
	 * @throws JSONException
	 */	
	public Integer getIntegerValue(String path, JSONObject jsonData) throws JSONException {
		path = path.startsWith("/") ? path.substring(1, path.length()) : path;
		String parts[] = path.split("/");
		Object value = getJSONValue( parts, jsonData);
		return (Integer)value;
	}
	
	/**
	 * This method is for reading the leaf level value of DataType 'Long'
	 * @param path The parameter provides the input 'jpath'
	 * @param jsonData Actual JSONObject. The jpath above will traverse this data to get the requested data
	 * @return
	 * @throws JSONException
	 */	
	public Long getLongValue(String path, JSONObject jsonData) throws JSONException {
		path = path.startsWith("/") ? path.substring(1, path.length()) : path;
		String parts[] = path.split("/");
		Object value = null;
		if( SUPPRESS_JSON_EXCEPTION ) { 
			try {
				value = getJSONValue( parts, jsonData);
			} catch(JSONException jsone) {
				System.err.println("User has used a wrong jpath or a non existing jpath.");
			}
		} else {
			value = getJSONValue( parts, jsonData);
		}
		return value != null ? (Long)value : null;
	}
	
	
	/**
	 * This method is for reading the non-leaf level a child JSONObject data 
	 * @param path The parameter provides the input 'jpath'
	 * @param jsonData Actual JSONObject. The jpath above will traverse this data to get the requested data
	 * @return
	 * @throws JSONException
	 */	
	public JSONObject getJSONObjectValue(String path, JSONObject jsonData) throws JSONException {
		path = path.startsWith("/") ? path.substring(1, path.length()) : path;		
		String parts[] = path.split("/");
		Object value = getJSONValue( parts, jsonData);
		return (JSONObject)value;
	}
	
	
	/**
	 * This method is for reading the non-leaf level a child JSONArray data 
	 * @param path The parameter provides the input 'jpath'
	 * @param jsonData Actual JSONObject. The jpath above will traverse this data to get the requested data
	 * @return
	 * @throws JSONException
	 */		
	public JSONArray getJSONArrayValue(String path, JSONObject jsonData) throws JSONException {
		path = path.startsWith("/") ? path.substring(1, path.length()) : path;		
		String parts[] = path.split("/");
		Object value = getJSONValue( parts, jsonData);
		return (JSONArray)value;
	}
	
	
	/**
	 * This recursive method is core method that traverses the JSON Data for return the requested jpath data. 
	 * This is a private method.
	 * @param parts
	 * @param jsonData
	 * @return
	 * @throws JSONException
	 */
	private Object getJSONValue(String parts[], Object jsonData) throws JSONException {
		String pathValue = getPathValue(parts[0]);
		String dataType = getDataType( parts[0] );
		if( "Object".equalsIgnoreCase(dataType) || JSON_OBJECT_TYPE.equals(dataType)) {
			JSONObject jsonDataObj = (JSONObject)jsonData;
			jsonData = jsonDataObj.getJSONObject(pathValue);
		} else if( "Array".equalsIgnoreCase(dataType) || JSON_ARRAY_TYPE.equals(dataType)) {
			JSONObject jsonDataObj = (JSONObject)jsonData;
			int dataLocInArr = getArrayLocation( parts[0], pathValue, jsonDataObj );
			if ( dataLocInArr > -1) {
				jsonData = jsonDataObj.getJSONArray(pathValue).get(dataLocInArr);
			}
			else {
				jsonData = jsonDataObj.getJSONArray(pathValue);
			}
		} else if( "String".equalsIgnoreCase(dataType) || STRING_TYPE.equals(dataType)) {
			JSONObject jsonDataObj = (JSONObject)jsonData;
			return jsonDataObj.getString(pathValue);
		} else if( "Integer".equalsIgnoreCase(dataType) || INTEGER_TYPE.equals(dataType)) {
			JSONObject jsonDataObj = (JSONObject)jsonData;
			return jsonDataObj.getInt(pathValue);
		} else if( "Double".equalsIgnoreCase(dataType) || DOUBLE_TYPE.equals(dataType)) {
			JSONObject jsonDataObj = (JSONObject)jsonData;
			return jsonDataObj.getDouble(pathValue);
		} else if( "Boolean".equalsIgnoreCase(dataType) || BOOLEAN_TYPE.equals(dataType)) {
			JSONObject jsonDataObj = (JSONObject)jsonData;
			return jsonDataObj.getBoolean(pathValue);
		} else if( "Long".equalsIgnoreCase(dataType) || LONG_TYPE.equals(dataType)) {
			JSONObject jsonDataObj = (JSONObject)jsonData;
			return jsonDataObj.getLong(pathValue);
		}
		
		if( parts.length > 1) {
			return getJSONValue( Arrays.copyOfRange(parts, 1, parts.length ), jsonData);
		} else {
			return jsonData;
		}
	}
	
	private String getPathValue(String pathlet) {
		String pathValue = pathlet.indexOf("[") != -1 ? pathlet.substring(0, pathlet.indexOf("[")) : pathlet;
		return pathValue;
	}
	
	private String getDataType(String pathlet) {
		String dataType = pathlet.indexOf("[") != -1 ?  pathlet.substring(pathlet.indexOf("[") + 1 , pathlet.indexOf("]")) : "String" ;
		return dataType;
	}
	
	private int getArrayLocation(String pathlet, String pathValue, JSONObject jsonDataObj) throws JSONException {
		int location = -1 ;
		String loc = pathlet.substring(pathlet.lastIndexOf("[") + 1 , pathlet.lastIndexOf("]"));
		if( loc.indexOf("=") != -1 ) {
			String name = loc.split("=")[0];
			String value = loc.split("=")[1];
			JSONArray arr = jsonDataObj.getJSONArray(pathValue);
			for(int i = 0 ; i < arr.length(); i++) {
				String val = arr.getJSONObject(i).getString(name);
				if ( value.equals( val ) ) {
					return i;
				}
			}
			return location;
		}
		
		try {
			location = Integer.parseInt(loc);
		} catch(java.lang.NumberFormatException nfe) {
			//eat it
		}
		return location;
	}
}
