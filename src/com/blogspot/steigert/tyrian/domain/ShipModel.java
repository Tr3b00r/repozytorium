package com.blogspot.steigert.tyrian.domain;

import java.util.Locale;

import com.blogspot.steigert.tyrian.utils.TextUtils;

/**
 * The available ship's models.
 */
public enum ShipModel
    implements
        Item
{
    USP_TALON( "Magiczny Babel"),
    GENCORE_PHOENIX( "Szmaragdowy Billy"),
    GENCORE_II( "Szalona Kate"),
    MICROSOL_STALKER( "Diamentowa Baska"),
    SUPER_CARROT( "Sloneczny Roman");

    private final String name;
   // private final int price;
   // private final int firingCapacity;

    private ShipModel(
        String name)
        //int price,
       // int firingCapacity )
        
    {
        this.name = name;
        //this.price = price;
       // this.firingCapacity = firingCapacity;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String getSimpleName()
    {
        return "ship-model-" + name().replaceAll( "_", "-" ).toLowerCase();
    }

    //public int getPrice()
   // {
   //     return price;
   // }

    //@Override
  //  public String getPriceAsText()
  //  {
 //       return TextUtils.creditStyle( price );
 //   }

    /**
     * Retrieves the firing capacity for this ship model.
     * <p>
     * 1 means 1 shot each 1/4 sec.
     */
   // public int getFiringCapacity()
   // {
       // return firingCapacity;
   // }

    @Override
    public String toString()
    {
        return String.format( Locale.US, "%s", name);
    }

	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPriceAsText() {
		// TODO Auto-generated method stub
		return null;
	}
}
