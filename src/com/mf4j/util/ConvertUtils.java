/**
*
* Copyright (C) 2010  Jarungwit J. All Rights Reserved.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>
*
*/
package com.mf4j.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mf4j.ConvertException;


public class ConvertUtils {

	static Logger logger		= Logger.getLogger(ConvertUtils.class.getName());

	protected static final int BYTE_MASK		= 0xff;
	protected static final int MANTISSA_MASK	= 0x7fffff;
	protected static final int EXPONENT_MASK	= 0x80ffff;
	protected static final int SIGN_MASK		= 0x80;

	/**
	 * convert byte array b to integer
	 * @param b byte array to convert
	 * @param offset start from 0
	 * @param length between 1 and 4
	 * @return result of conversion
	 * @throws Exception
	 */
	public static int byte2Int(byte[] b, int offset, int length) throws ConvertException {
		int result = 0;

		// check boundary
		if (offset < 0) {
			throw new ConvertException("offset must greater than or equal zero;[offset="+offset+"]");
		}
		if (length<1 || length>4) {
			throw new ConvertException("length must between 1 and 4;[length="+length+"]");
		}
		if (offset+length>b.length) {
			throw new ConvertException("offset+length exceed size of b[];[offset="+offset+", length="+length+", b.length="+b.length+"]");
		}

		for (int i=0; i<length; i++) {
			int shift = 8*i;
			result += (long)(b[offset+i] & 0xff) << shift;
		}
		return result;
	}

	/**
	 * convert old Microsoft Basic Floating point (store in array b) to float
	 *
	 * <pre>
	 * MBF __________|__Byte 4 __|__Byte 3 __|__Byte 2 __|__Byte 1 __|
	 * Position bit__| 32 ... 24 | 23 ... 16 | 15 .. . 8 | 7 . . . 0 |
	 * Value in bit__| EEEE EEEE | XMMM MMMM | MMMM MMMM | MMMM MMMM |
	 *
	 * IEEE 754 _____|__Byte 4 __|__Byte 3 __|__Byte 2 __|__Byte 1 __|
	 * Position bit__| 32 ... 24 | 23 ... 16 | 15 .. . 8 | 7 . . . 0 |
	 * Value in bit__| XEEE EEEE | EMMM MMMM | MMMM MMMM | MMMM MMMM |
	 *
	 * Components:
	 * X = sign bit
	 * E = 8 bit exponent
	 * M = 23 bit mantissa
	 * </pre>
	 *
	 * @param b byte array (Microsoft Basic Floating point) to convert
	 * @param offset start from 0
	 * @return result of conversion
	 * @throws Exception
	 */
	public static float msbByte2Float(byte[] b, int offset) throws ConvertException {
		// check boundary
		if (offset<0) {
			throw new ConvertException("offset must greater than or equal zero;[offset="+offset+"]");
		}
		if (offset+4>b.length) {
			throw new ConvertException("offset exceed size of b[];[offset="+offset+", b.length="+b.length+"]");
		}

		long intOne = (int) (b[offset] & BYTE_MASK);
		long intTwo = (int) (b[offset+1] & BYTE_MASK);
		long intThree = (int) (b[offset+2] & BYTE_MASK);
		long intFour = (int) (b[offset+3] & BYTE_MASK);

		if (intOne == 0 && intTwo == 0 && intThree == 0 && intFour == 0) {
			return 0.0f;
		}

		long msf = intFour << 24 | intThree << 16 | intTwo << 8 | intOne;

		int mantissa = (int)(msf & MANTISSA_MASK);
		int exponent = (int)((msf >> 24) & EXPONENT_MASK) - 2;
		int sign = (int)(msf >> 16) & SIGN_MASK;

        mantissa |= exponent << 23 | sign << 24;

        return Float.intBitsToFloat(mantissa);
	}

	/**
	 * convert IEEE floating point to bytes array (Microsoft Basic Floating point)
	 * @param value floating point
	 * @return bytes array
	 */
	public static final byte[] float2MsbByte(float value) {
        byte[] result = new byte[4];

        int bits = Float.floatToIntBits(value);

        // IEEE754
        String bitStr = Integer.toBinaryString(bits);
        bitStr = StringUtils.leftPad(bitStr, bitStr.length() + (32 - bitStr.length()), '0');

        // Microsoft Basic Floating Point
        String mbf = bitStr.substring(1, 9) + bitStr.substring(0, 1) + bitStr.substring(9);
        long bitLong = Long.parseLong(mbf, 2);
        bitStr = Long.toBinaryString(bitLong);
        bitStr = StringUtils.leftPad(bitStr, bitStr.length() + (32 - bitStr.length()), '0');

        result[0] = (byte) Long.parseLong(bitStr.substring(24, 32), 2);
        result[1] = (byte) Long.parseLong(bitStr.substring(16, 24), 2);
        result[2] = (byte) Long.parseLong(bitStr.substring(8, 16), 2);
        result[3] = (byte) Long.parseLong(bitStr.substring(0, 8), 2);
        result[3] += 2;

        return result;
    }

	/**
	 * convert 4 bytes array to floating point
	 * @param b
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public static final float byte2Float(byte[] b, int offset) throws ConvertException {
		// check boundary
		if (offset<0) {
			throw new ConvertException("offset must greater than or equal zero;[offset="+offset+"]");
		}
		if (offset+4>b.length) {
			throw new ConvertException("offset exceed size of b[];[offset="+offset+", b.length="+b.length+"]");
		}

		byte[] tmp = new byte[4];
		for (int i = 0; i < 4; i++) {
			tmp[i] = b[offset+i];
		}
		int accum = 0;
		int i = 0;
		for (int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
			accum |= ( (long)( tmp[i] & 0xff ) ) << shiftBy;
			i++;
		}
		return Float.intBitsToFloat(accum);
	}

	/**
	 * convert byte array (Microsoft Basic Floating point) to date
	 *
	 * @param b byte array to convert
	 * @param offset start from 0
	 * @return result of conversion
	 * @throws Exception
	 */
	public static Date msbByte2Date(byte[] b, int offset) throws ConvertException {
		// check boundary
		if (offset<0) {
			throw new ConvertException("offset must greater than or equal zero;[offset="+offset+"]");
		}
		if (offset+4>b.length) {
			throw new ConvertException("offset exceed size of b[];[offset="+offset+", b.length="+b.length+"]");
		}

		return float2Date(msbByte2Float(b, offset));
	}

	/**
	 * convert floating point to date (for master and emaster)
	 * @param f floating point yyyymmdd (yyyy+1900 before convert)
	 * @return result of conversion
	 *
	 * <pre>
	 * Ex: Converter.float2Date(1000504) return 4-May-2000
	 * </pre>
	 */
	public static Date float2Date(float f) {
		int date, month, year;
		int d = (int)f;
		if (f>0) {
			date = d % 100;
			d = d / 100;
			month = (d % 100) - 1;	// month index start from 0
			d = d / 100;
			year = 1900+d;
		} else {
			d += 1000000;
			date = d % 100;
			d = d / 100;
			month = (d % 100) - 1;
			d = (int)f;
			d = d / 100;
			d = d /100;
			year = 1900+d;
		}

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * convert integer to date (for xmaster)
	 * @param input yyyymmdd
	 * @return result of conversion
	 *
	 * <pre>
	 * Ex: Converter.int2Date(20100504) return 4-May-2010
	 * </pre>

	 */
	public static Date int2DateX(int input) {
		if (input == 0) {
			return null;
		}

		int date, month, year;
		int d = input;
		date = d % 100;
		d = d / 100;
		month = (d % 100) - 1;	// month index start from 0
		d = d / 100;
		year = d;

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, 0, 0);
		return cal.getTime();
	}

	/**
	 * convert bytes array to date (for file master and emaster)
	 * @param b byte array to convert
	 * @param offset start from 0
	 * @return result of conversion
	 * @throws ConvertException
	 */
	public static Date byte2Date(byte[] b, int offset) throws ConvertException {
		// check boundary
		if (offset<0) {
			throw new ConvertException("offset must greater than or equal zero;[offset="+offset+"]");
		}
		if (offset+4>b.length) {
			throw new ConvertException("offset exceed size of b[];[offset="+offset+", b.length="+b.length+"]");
		}

		return float2Date(byte2Float(b, offset));
	}

	/**
	 * convert bytes array to date (for file xmaster)
	 * @param b byte array to convert
	 * @param offset start from 0
	 * @return result of conversion
	 * @throws ConvertException
	 */
	public static Date byte2DateX(byte[] b, int offset) throws ConvertException {
		// check boundary
		if (offset<0) {
			throw new ConvertException("offset must greater than or equal zero;[offset="+offset+"]");
		}
		if (offset+4>b.length) {
			throw new ConvertException("offset exceed size of b[];[offset="+offset+", b.length="+b.length+"]");
		}

		return int2DateX(byte2Int(b, offset, 4));
	}

	/**
	 * convert bytes array to string (for file xmaster)
	 * @param b bytes array string with null terminator
	 * @param offset
	 * @param length
	 * @return
	 * @throws ConvertException
	 */
	public static String byte2StringX(byte[] b, int offset, int length) throws ConvertException {
		// check boundary
		if (offset < 0) {
			throw new ConvertException("offset must greater than or equal zero;[offset="+offset+"]");
		}
		if (offset+length>b.length) {
			throw new ConvertException("offset exceed size of b[];[offset="+offset+", b.length="+b.length+"]");
		}

		StringBuffer result = new StringBuffer();
		int c = 0;

		while (c<length) {
			if (b[offset+c] != 0) {
				result.append((char)b[offset+c]);
			}
			c++;
		}

		return result.toString().trim();
	}
}
