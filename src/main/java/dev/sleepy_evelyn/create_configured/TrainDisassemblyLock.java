package dev.sleepy_evelyn.create_configured;

import net.minecraft.util.ByIdMap;

import java.util.function.IntFunction;

public enum TrainDisassemblyLock {
    LOCKED(0),
    PARTY_MEMBERS_ONLY(1),
    NOT_LOCKED(2);

    private final int id;

    public static final IntFunction<TrainDisassemblyLock> BY_ID =
            ByIdMap.continuous(
                    TrainDisassemblyLock::getId,
                    TrainDisassemblyLock.values(),
                    ByIdMap.OutOfBoundsStrategy.ZERO
            );

    TrainDisassemblyLock(int id) {
        this.id = id;
    }

    public int getId() { return id; }
}
