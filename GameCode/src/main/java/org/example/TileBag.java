package org.example;

/**
 * This class represents the bag that is filled by the tiles
 */

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class TileBag {


    private Set<Tile> bag;


    public TileBag() {
        bag= new HashSet<>();
        initializeBag();
    }

    /**
     * For each color create 22 tiles and assign each one a different id
     */
    public void initializeBag(){
        int j=0; //index of tile
        for (Color color:Color.values())
        {
            for(int i=0; i<22; i++)
            {
                Tile tile= new Tile(color,j);
                bag.add(tile);
                j++;
            }
        }
    }

    /**
     * Method that randomly takes n tiles from the bag.
     * To do this, you turn the set into an array, randomly choose a value between 0 and size of the array,
     * take the tile that is in that position, delete the tile from the set.
     * @param n rapresents the number of cards to draw
     * @return The set of drawn tiles
     */
    public Set<Tile> randomPick(int n)
    {
        Set<Tile> tilePicked= new HashSet<Tile>();
        Random random= new Random();
       for(int i=0;i<n;i++)
       {
           if(bag.size()>0){
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
       }

       return tilePicked;
    }

    public Set<Tile> getBag() {
        return bag;
    }
}
