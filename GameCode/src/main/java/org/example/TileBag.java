package org.example;

import java.util.HashSet;
import java.util.Set;

public class TileBag {

    private Set<Tile> bag;


    public TileBag() {
        bag= new HashSet<>();
    }

    public void initializeBag(){
        for (Color color:Color.values())
        {
            for(int i=0; i<22; i++)
            {
                Tile tile= new Tile(color,i);
                bag.add(tile);
            }
        }
    }

    public Set<Tile> randomPick(int n)
    {
        
    }
}
