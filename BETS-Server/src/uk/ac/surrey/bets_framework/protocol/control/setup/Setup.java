/**
 * DICE NFC evaluation.
 *
 * (c) University of Surrey and Pervasive Intelligence Ltd 2017.
 */
package uk.ac.surrey.bets_framework.protocol.control.setup;

import java.util.Arrays;

import uk.ac.surrey.bets_framework.protocol.NFCReaderStateMachine;
import uk.ac.surrey.bets_framework.protocol.NFCSharedMemory;
import uk.ac.surrey.bets_framework.protocol.control.ControlStates;
import uk.ac.surrey.bets_framework.state.SharedMemory;

/**
 * Control protocol state machine used to setup the client.
 *
 * @author Matthew Casey
 */
public class Setup extends NFCReaderStateMachine {

  /**
   * The implementation of the state machine's shared memory.
   */
  public class SetupSharedMemory extends NFCSharedMemory {

    /** The setup data. */
    public ServerData serverData = null;

  }

  /** The shared memory. */
  private SetupSharedMemory sharedMemory = new SetupSharedMemory();

  /**
   * Constructor requiring the setup information for the client.
   *
   * @param serverData The setup data.
   */
  public Setup(ServerData serverData) {
    super(Arrays.asList(new ControlStates.ControlState0(), new ControlStates.ControlState1(), new SetupStates.SetupState2(),
        new SetupStates.SetupState3(), new SetupStates.SetupState4()));

    this.sharedMemory.serverData = serverData;
  }
  
  public Setup()
  {
	  super(Arrays.asList(new ControlStates.ControlState0(), new ControlStates.ControlState1()));
  }

  /**
   * @return The shared memory for the state machine.
   */
  @Override
  public SharedMemory getSharedMemory() {
    return this.sharedMemory;
  }

  /**
   * Sets the shared memory for the state machine.
   *
   * @param sharedMemory The shared memory to set.
   */
  @Override
  public void setSharedMemory(SharedMemory sharedMemory) {
    this.sharedMemory = (SetupSharedMemory) sharedMemory;
  }
}
