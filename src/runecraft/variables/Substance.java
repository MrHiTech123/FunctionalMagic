package runecraft.variables;

import runecraft.datastructure.DataHelpers;
import runecraft.datastructure.pair.Pair;

import java.util.Map;

public enum Substance {
    AIR(),
    EARTH(),
    FIRE(),
    WATER(),
    MIND(),
    FLESH(),
    ACID(FIRE, WATER),
    LIGHT(FIRE, AIR),
    SMOKE(FIRE, EARTH),
    FIRE_MEPHIT(FIRE, MIND),
    OIL(FIRE, FLESH),
    STEAM(WATER, FIRE),
    ICE(WATER, WATER),
    SPARKLING_WATER(WATER, AIR),
    MINERAL_WATER(WATER, EARTH),
    WATER_MEPHIT(WATER, MIND),
    BLOOD(WATER, FLESH),
    HOT_AIR(AIR, FIRE),
    COLD_AIR(AIR, WATER),
    ASH(AIR, EARTH),
    AIR_MEPHIT(AIR, MIND),
    MUCUS(AIR, FLESH),
    LAVA(EARTH, FIRE),
    MUD(EARTH, WATER),
    SAND(EARTH, AIR),
    STONE(EARTH, EARTH),
    EARTH_MEPHIT(EARTH, MIND),
    FECES(EARTH, FLESH),
    RAGE(MIND, FIRE),
    CALM(MIND, WATER),
    FEAR(MIND, AIR),
    COURAGE(MIND, EARTH),
    SATISFACTION(MIND, MIND),
    HUNGER(MIND, FLESH),
    COOKED_FLESH(FLESH, FIRE),
    SKIN(FLESH, WATER),
    ORGAN_TISSUE(FLESH, AIR),
    PLANT_MATTER(FLESH, EARTH),
    NERVES(FLESH, MIND),
    MUSCLE(FLESH, FLESH),
    MADNESS(MIND, BLOOD),
    NARCOTICS(PLANT_MATTER, MADNESS);
    
    
    private static final Map<Pair<Substance, Substance>, Substance> combinationRecipes = DataHelpers.mapOfValues(
            Substance.class, 
            substance -> (
                    substance.getRecipe().getFirst() != null && 
                            substance.getRecipe().getSecond() != null
            ),
            Substance::getRecipe
    );
    
    private final Pair<Substance, Substance> recipe;
    
    Substance() {
        this(null, null);
    }
    
    Substance(Substance baseIngredient, Substance modifierIngredient) {
        this.recipe = new Pair<>(baseIngredient, modifierIngredient);
    }
    
    public Pair<Substance, Substance> getRecipe() {
        return this.recipe;
    }
    
    public static Map<Pair<Substance, Substance>, Substance> getCombinationRecipes() {
        return combinationRecipes;
    }
    
    public static Substance combine(Substance baseIngredient, Substance modifierIngredient) {
        Pair<Substance, Substance> recipe = new Pair<>(baseIngredient, modifierIngredient);
        return combinationRecipes.getOrDefault(recipe, null);
    }
    
    
}
