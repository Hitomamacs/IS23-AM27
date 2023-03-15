package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

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
        Set<Tile> tilePicked= new HashSet<Tile>();
        Random random= new Random();
       for(int i=0;i<n;i++)
       {
           //generation of a random number
           int randomIndex= random.nextInt(bag.size());

           //conversion from set to array
           Tile[] array=bag.toArray(new Tile[bag.size()]);

           //getting the element from the array
           Tile randomTile= array[randomIndex];

           //remove the element from the bag and add the element to the set
            bag.remove(randomTile);
            tilePicked.add(randomTile);
       }

       return tilePicked;
    }
}
