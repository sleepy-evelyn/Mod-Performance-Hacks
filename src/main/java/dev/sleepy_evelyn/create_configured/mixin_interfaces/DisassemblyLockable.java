package dev.sleepy_evelyn.create_configured.mixin_interfaces;

import dev.sleepy_evelyn.create_configured.TrainDisassemblyLock;

import java.util.UUID;

public interface DisassemblyLockable {
    TrainDisassemblyLock cc$getLock();
    void cc$setLock(TrainDisassemblyLock lock);

    UUID cc$getLastDisassembler();
    void cc$setDisassembler(UUID disassembler);
}
