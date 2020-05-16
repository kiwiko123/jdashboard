package com.kiwiko.webapp.games.scrabble.words.internal.unixlocal.data;

import javax.annotation.Nullable;

public class WordMigratorParameters {

    private int batchSize;
    private @Nullable Integer maxWordsToProcess;

    @Nullable
    public Integer getBatchSize() {
        return batchSize;
    }

    public WordMigratorParameters withBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    @Nullable
    public Integer getMaxWordsToProcess() {
        return maxWordsToProcess;
    }

    public WordMigratorParameters withMaxWordsToProcess(@Nullable Integer maxWordsToProcess) {
        this.maxWordsToProcess = maxWordsToProcess;
        return this;
    }
}
