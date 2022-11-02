package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class Blockchain {

    private List<Block> blocks = new LinkedList<>();
    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    public void add(Block block) {
        blocks.add(block);
    }

    public int size() {
        return blocks.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        if(blocks.size() == 0) return true;
        else if(blocks.size() == 1){
            if(blocks.get(0).getHash().contains(" ")) return false;
            if(!isMined(blocks.get(0))) return false;
        }
        else {
            Block currentBlock;
            Block previousBlock;
            for (int i = 1; i < blocks.size(); i++) {
                if (!isMined(blocks.get(i)) || (blocks.get(i).getHash().contains(" "))) return false;
                currentBlock = blocks.get(i);
                previousBlock = blocks.get(i - 1);
                if (!currentBlock.getHash().equals(currentBlock.calculatedHash())) return false;
                if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) return false;

            }
        }
        return true;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}