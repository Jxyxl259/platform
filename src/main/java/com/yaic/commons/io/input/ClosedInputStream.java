package com.yaic.commons.io.input;

import static com.yaic.servicelayer.util.IOUtil.EOF;

import java.io.InputStream;


/**
 * Closed input stream. This stream returns EOF to all attempts to read something from the stream.
 * <p>
 * Typically uses of this class include testing for corner cases in methods that accept input streams and acting as a
 * sentinel value instead of a {@code null} input stream.
 */
public class ClosedInputStream extends InputStream
{

	/**
	 * A singleton.
	 */
	public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

	/**
	 * Returns -1 to indicate that the stream is closed.
	 *
	 * @return always -1
	 */
	@Override
	public int read()
	{
		return EOF;
	}

}
