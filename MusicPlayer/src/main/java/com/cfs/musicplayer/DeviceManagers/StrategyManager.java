package com.cfs.musicplayer.DeviceManagers;

import com.cfs.musicplayer.Enums.PlayStrategyType;
import com.cfs.musicplayer.Strategies.CustomPlayStrategy;
import com.cfs.musicplayer.Strategies.PlayStrategy;
import com.cfs.musicplayer.Strategies.RandomPlayStrategy;
import com.cfs.musicplayer.Strategies.SequentialPlayStrategy;

public class StrategyManager {
    private static StrategyManager instance = null;
    private CustomPlayStrategy customPlayStrategy;
    private RandomPlayStrategy randomPlayStrategy;
    private SequentialPlayStrategy sequentialPlayStrategy;

    private StrategyManager() {
        customPlayStrategy = new CustomPlayStrategy();
        randomPlayStrategy = new RandomPlayStrategy();
        sequentialPlayStrategy = new SequentialPlayStrategy();
    }

    public StrategyManager getInstance() {
        if (instance == null) {
            instance = new StrategyManager();
        }
        return instance;
    }

    public PlayStrategy getCurrentPlayStrategy(PlayStrategyType type) {
        if (type == PlayStrategyType.RANDOM) {
            return randomPlayStrategy;
        }
        else if (type == PlayStrategyType.SEQUENTIAL) {
            return sequentialPlayStrategy;
        }
        else {
            return customPlayStrategy;
        }
    }
}
