package com.gamesense.api.util.world.search;

import com.gamesense.api.util.world.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ChunkSearcher {
    private final Chunk chunk;
    private final ArrayList<BlockPos> blocks = new ArrayList<>();
    private final ArrayList<Block> blockTypes;
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    private Future<?> future;

    public ChunkSearcher(Chunk chunk, ArrayList<Block> blockTypes) {
        this.chunk = chunk;
        this.blockTypes = blockTypes;
    }

    public void startSearching(ChunkSearchingFinished listener) {
        this.future = service.submit(() -> {
            ChunkPos pos = this.chunk.getPos();

            for (int x = pos.getXStart(); x <= pos.getXEnd(); ++x) {
                for (int y = 0; y <= 256; ++y) {
                    for (int z = pos.getZStart(); z <= pos.getZEnd(); ++z) {
                        if (this.future == null || Thread.interrupted()) {
                            return;
                        }

                        BlockPos blockPos = new BlockPos(x, y, z);
                        if (!this.blockTypes.contains(BlockUtil.getBlock(blockPos))) {
                            continue;
                        }

                        this.blocks.add(blockPos);
                    }
                }
            }

            if (this.future != null) {
                listener.finished(this.blocks);
            }
        });
    }

    public void cancel() {
        if (this.future != null) {
            try {
                this.future.get();
                this.future = null;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        this.blocks.clear();
    }

    public void add(Block block) {
        if (this.blockTypes.contains(block)) {
            this.blockTypes.add(block);
        }
    }

    public void remove(Block block) {
        if (!this.blockTypes.contains(block)) {
            this.blockTypes.remove(block);
            this.blocks.removeIf((pos) -> BlockUtil.getBlock(pos).equals(block));
        }
    }

    public ArrayList<BlockPos> getBlocks() {
        return blocks;
    }

    @FunctionalInterface
    public interface ChunkSearchingFinished {
        void finished(ArrayList<BlockPos> blocks);
    }
}
