/**
 * DICE Protocol evaluation.
 *
 * (c) University of Surrey and Pervasive Intelligence Ltd 2017.
 */
package uk.ac.surrey.bets_framework.protocol;

/**
 * Shared memory for the NFC state machine.
 *
 * @author Matthew Casey
 */
public class NFCSharedMemory extends AbstractSharedMemory {

  /** Arbitrary proprietary AID (starts with "F") for Android app. Hopefully doesn't clash with anything else. */
  public static final byte[] AID             = new byte[] { (byte) 0xF0, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66 };

  /** The maximum size of a response before it needs to be chunked. */
  public static final int    APDU_CHUNK_SIZE = 50;
  
  

}
