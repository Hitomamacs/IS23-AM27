package org.example;

<<<<<<< Updated upstream
import java.util.HashSet;
import java.util.Set;

public class TileBag {
=======
public class TileBag {
<<<<<<< Updated upstream
>>>>>>> Stashed changes

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
=======
    //private Set<Tile> bag;
>>>>>>> Stashed changes
}
